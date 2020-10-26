package com.redhat.camel.enrich;

import org.apache.camel.AggregationStrategy;
import org.apache.camel.Exchange;
import org.apache.camel.RuntimeCamelException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.redhat.camel.model.PedidoNovo;
import com.redhat.camel.model.ProdutoPedido;

public class ProdutoNovoItemAgregationStrategy implements AggregationStrategy {

	public static final String PEDIDO_NOVO = "pedidoNovo";

	@Override
	public Exchange aggregate(Exchange originalExchange, Exchange newExchange) {
		PedidoNovo pedido = originalExchange.getIn().getHeader(PEDIDO_NOVO, PedidoNovo.class);				
		try {
			String newBody = newExchange.getIn().getBody(String.class);
			JsonNode produto = new ObjectMapper().readTree(newBody);

			pedido.getProdutos()
					.add(new ProdutoPedido(originalExchange.getIn().getHeader("codigo", String.class),
							produto.get("descricao").asText(), produto.get("valor").asDouble(),
							originalExchange.getIn().getHeader("quantidade", Integer.class)));

		} catch (Exception e) {
			throw new RuntimeCamelException(e);
		}

		return originalExchange;
	}

}
