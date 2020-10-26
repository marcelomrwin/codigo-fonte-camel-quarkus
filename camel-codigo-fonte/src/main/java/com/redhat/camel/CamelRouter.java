package com.redhat.camel;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.camel.AggregationStrategy;
import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.language.xpath.XPathBuilder;
import org.apache.camel.model.rest.RestBindingMode;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.redhat.camel.enrich.ProdutoNovoItemAgregationStrategy;
import com.redhat.camel.model.Cliente;
import com.redhat.camel.model.PedidoNovo;

public class CamelRouter extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		AggregationStrategy aggregationStrategy = new ProdutoNovoItemAgregationStrategy();
		ExecutorService threadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        restConfiguration().bindingMode(RestBindingMode.auto)
        	.component("platform-http")
			.dataFormatProperty("prettyPrint", "true")
			.contextPath("/").port(8080)
			.apiContextPath("/openapi")
			.apiProperty("api.title", "Camel Quarkus Demo API")
			.apiProperty("api.version", "1.0.0-SNAPSHOT")
            .apiProperty("cors", "true");

		rest().tag("API de serviços Demo utilizando Camel e Quarkus").produces("application/json")

			.get("/clientes")
				.description("Listar todos os clientes")
				.route().routeId("restclienteall") .to("direct:clienteall").choice()
				.when(body().isNull()).setHeader(Exchange.HTTP_RESPONSE_CODE, constant(404)).endChoice().otherwise()
				.setHeader(Exchange.HTTP_RESPONSE_CODE, constant(200))
				.endRest()

			.get("/clientes/{cpf}")
				.description("Consulta cliente por CPF")
				.route().routeId("restquerybycpf").to("direct:querybycpf").choice()
				.when(body().isNull()).setHeader(Exchange.HTTP_RESPONSE_CODE, constant(404)).endChoice().otherwise()
				.setHeader(Exchange.HTTP_RESPONSE_CODE, constant(200))
				.endRest()

			.post("/clientes/{cpf}/{nome}/{email}/{endereco}")
				.description("Salvar novo cliente")
				.route().routeId("restclienteinsert") .to("direct:clienteinsert")
				.setHeader(Exchange.HTTP_RESPONSE_CODE, constant(201))
				.endRest()


			.put("/clientes/{cpf}/{nome}/{email}/{endereco}")
				.description("Alterar cliente")
				.responseMessage().code(200).message("cliente alterado com sucesso").endResponseMessage()
				.responseMessage().code(500).message("Falha ao alterar cliente").endResponseMessage()
				.route().routeId("restclienteupdate").to("direct:clienteupdate")
				.setHeader(Exchange.HTTP_RESPONSE_CODE, constant(200))
				.endRest()

			.delete("/clientes/{cpf}")
				.description("Exclui um cliente")
				.route().routeId("restclientedelete").to("direct:clientedelete")
				.setHeader(Exchange.HTTP_RESPONSE_CODE, constant(204))
				.endRest()

			.get("/produtos/{codigo}")
				.description("consultar produto por código")
				.route().routeId("restproduto")
				.to("direct:produto")
				.setHeader(Exchange.HTTP_RESPONSE_CODE,constant(200))
				.endRest()
		;

		from("direct:clienteall").routeId("clienteall").to("sql:classpath:sql/queryall.sql");
		from("direct:querybycpf").routeId("querybycpf").to("sql:classpath:sql/querybycpf.sql?outputType=SelectOne&outputClass=com.redhat.camel.model.Cliente");
		from("direct:clienteinsert").routeId("clienteinsert").to("sql:classpath:sql/insert.sql");
		from("direct:clienteupdate").routeId("clienteupdate").to("sql:classpath:sql/update.sql");
		from("direct:clientedelete").routeId("clientedelete").to("sql:classpath:sql/delete.sql");

		from("direct:produto").routeId("produto")
			.to("qute:soap-envelope.xml")
			.setHeader(Exchange.HTTP_METHOD, constant("POST"))
			.toD("http://{{produtos.url}}/produtos?bridgeEndpoint=true")
			.convertBodyTo(String.class)
			.process((exc)->{
				ObjectNode json = JsonNodeFactory.instance.objectNode();
				json.put("descricao", XPathBuilder.xpath("//descricao/text()").evaluate(exc,String.class));
				json.put("valor", Double.valueOf(XPathBuilder.xpath("//valor/text()").evaluate(exc,String.class)));
				json.put("codigo", exc.getIn().getHeader("codigo",String.class));
				exc.getMessage().setBody(json.toString());
			});

		from("file:{{pedidos.in.folder}}?antInclude=**/*.xml&move={{pedidos.out.folder}}").routeId("pedidos")
			.choice()
				.when(xpath("/pedido/departamento='TI'")).to(ExchangePattern.InOnly,"seda:processarPedido")
				.otherwise().log(LoggingLevel.WARN, "Pedido ignorado ${header.CamelFileName}");

		from("seda:processarPedido").errorHandler(noErrorHandler())
		.process((exc)->{
			PedidoNovo pedido = new PedidoNovo();
			pedido.setNumPedido(Integer.valueOf(XPathBuilder.xpath("//numero/text()").evaluate(exc,String.class)));
			exc.getIn().setHeader(ProdutoNovoItemAgregationStrategy.PEDIDO_NOVO, pedido);
		})
		.split(xpath("//produto"))
		.parallelProcessing().streaming().executorService(threadPool)
		.setHeader("codigo", xpath("//codigo/text()").convertToString())
		.setHeader("quantidade", xpath("//quantidade/text()").convertToString())
		.enrich("direct:produto",aggregationStrategy).end()

		.setHeader("cpf", xpath("//cliente/text()").convertToString())
		.to("direct:querybycpf").process((exc)->{
			PedidoNovo pedido = exc.getMessage().getHeader(ProdutoNovoItemAgregationStrategy.PEDIDO_NOVO,PedidoNovo.class);
			Cliente cliente = exc.getMessage().getBody(Cliente.class);
			pedido.setCodigoCliente(cliente.getCpf());
			pedido.setNomeCliente(cliente.getNome());
			pedido.setEmailCliente(cliente.getEmail());
			pedido.setEnderecoCliente(cliente.getEndereco());
			exc.getMessage().setBody(pedido);
			exc.getMessage().removeHeader(ProdutoNovoItemAgregationStrategy.PEDIDO_NOVO);
		})
		.log("Após Final >> ${body}")
		;

	}

}
