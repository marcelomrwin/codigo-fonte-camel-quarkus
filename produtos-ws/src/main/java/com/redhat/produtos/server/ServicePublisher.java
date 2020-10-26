package com.redhat.produtos.server;

import javax.enterprise.event.Observes;

import org.apache.cxf.frontend.ServerFactoryBean;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import io.quarkus.runtime.StartupEvent;

public class ServicePublisher {

	@ConfigProperty(name = "server.port", defaultValue = "8080")
	private Integer port;

	void onStart(@Observes StartupEvent ev) {
		// Create our service implementation
		ProdutoWS service = new ProdutoWSImpl();

		// Create our Server
		ServerFactoryBean svrFactory = new ServerFactoryBean();
		svrFactory.setServiceClass(ProdutoWS.class);
		svrFactory.setAddress("http://localhost:" + port + "/produtos");
		svrFactory.setServiceBean(service);
		svrFactory.create();
	}

}
