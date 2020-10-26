package com.redhat.camel;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import io.quarkus.test.junit.DisabledOnNativeImage;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
@DisabledOnNativeImage
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Teste da API rota de integração produtos")
public class ProdutosCamelTest extends AbstractCamelTest {

	@Test
	@Order(1)
	public void buscarProdutoTest() {
		Endpoint endpoint = context.getEndpoint("direct:produto");
		Exchange exc = endpoint.createExchange();
		Message msg = exc.getMessage();
		msg.setHeader("codigo", "hw001");
		exc.setProperty("codigo", "hw001");
		
		Exchange exchange = producerTemplate.send(endpoint, exc);
		String body = exchange.getMessage().getBody(String.class);
		assertThat(body, containsString("Impressora 3D"));
	}
	
}
