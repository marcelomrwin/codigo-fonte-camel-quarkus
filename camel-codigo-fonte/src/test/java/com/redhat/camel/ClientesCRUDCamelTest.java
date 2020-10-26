package com.redhat.camel;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.camel.CamelContext;
import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.ProducerTemplate;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.quarkus.test.junit.DisabledOnNativeImage;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
@DisabledOnNativeImage
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Teste da API rota de integração clientes")
public class ClientesCRUDCamelTest extends AbstractCamelTest {

	@Test
	@Order(1)
	public void insertTest() {
		Endpoint endpoint = context.getEndpoint("direct:clienteinsert");
		Exchange exc = endpoint.createExchange();
		Message msg = exc.getMessage();
		msg.setHeader("cpf", "111.222.333-44");
		msg.setHeader("nome", "Marcelo");
		msg.setHeader("email", "masales@redhat.com");
		msg.setHeader("endereco", "rua abcd 101");
		Exchange exchange = producerTemplate.send(endpoint, exc);
		Integer insertCount = exchange.getMessage().getHeader("CamelSqlUpdateCount", Integer.class);
		assertNotNull(insertCount);
		assertThat(insertCount, is(greaterThan(0)));
	}

	@Test
	@Order(2)
	public void selectAll() {
		Endpoint endpoint = context.getEndpoint("direct:clienteall");
		Exchange exc = endpoint.createExchange();
		Exchange exchange = producerTemplate.send(endpoint, exc);
		List<Map<String, ?>> body = (List) exchange.getMessage().getBody();
		assertThat(body, is(not(empty())));
		assertThat(body, hasSize(5));
	}

	@Test
	@Order(3)
	public void selectOne() {
		Endpoint endpoint = context.getEndpoint("direct:querybycpf");
		Exchange exc = endpoint.createExchange();
		Message msg = exc.getMessage();
		msg.setHeader("cpf", "111.222.333-44");
		Exchange exchange = producerTemplate.send(endpoint, exc);
		Map<String, Object> body = exchange.getMessage().getBody(Map.class);
		assertNotNull(body);
		assertThat(body.get("nome"), is("Marcelo"));
	}

	@Test
	@Order(4)
	public void update() {
		Endpoint endpoint = context.getEndpoint("direct:clienteupdate");

		Map<String, Object> body = new HashMap<>();
		body.put("nome", "Marcelo Sales");
		body.put("email", "masales@redhat.com");
		body.put("endereco", "RUA ABCD 101");

		Map<String, Object> newBody = producerTemplate.requestBodyAndHeader(endpoint, body, "cpf", "111.222.333-44",
				Map.class);
		assertNotNull(newBody);
		assertThat(newBody.get("nome"), is("Marcelo Sales"));
	}

	@Test
	@Order(5)
	public void delete() {
		Endpoint endpoint = context.getEndpoint("direct:clientedelete");
		Exchange exc = endpoint.createExchange();
		Map<String, Object> body = new HashMap<>();
		body.put("cpf", "111.222.333-44");
		exc.getMessage().setBody(body);
		Exchange response = producerTemplate.send(endpoint, exc);

		Integer insertCount = response.getMessage().getHeader("CamelSqlUpdateCount", Integer.class);
		assertNotNull(insertCount);
		assertThat(insertCount, is(greaterThan(0)));

		Endpoint edpSelectAll = context.getEndpoint("direct:clienteall");
		Exchange excDelete = producerTemplate.send(edpSelectAll, edpSelectAll.createExchange());

		List<Map<String, ?>> newList = (List) excDelete.getMessage().getBody();
		assertThat(newList, is(not(empty())));
		assertThat(newList, hasSize(4));
	}

}
