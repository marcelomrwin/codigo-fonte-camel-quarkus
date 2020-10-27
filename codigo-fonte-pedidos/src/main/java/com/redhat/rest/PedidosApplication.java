package com.redhat.rest;

import javax.ws.rs.core.Application;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@OpenAPIDefinition(
	    tags = {
	            @Tag(name="Pedidos", description="API de serviços Novo sistema de pedidos utilizando Quarkus")
	    },
	    info = @Info(
	        title="Novo sistema de pedidos",
	        version = "1.0.0-SNAPSHOT",
	        contact = @Contact(
	            name = "Marcelo Sales",
	            url = "http://redhat.com",
	            email = "masales@redhat.com"),
	        license = @License(
	            name = "Apache 2.0",
	            url = "http://www.apache.org/licenses/LICENSE-2.0.html"))
	)
public class PedidosApplication extends Application {

}