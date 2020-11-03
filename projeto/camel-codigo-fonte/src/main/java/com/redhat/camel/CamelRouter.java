package com.redhat.camel;

import org.apache.camel.builder.RouteBuilder;

public class CamelRouter extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("timer:foo?period=2000")
                .log("Hello World");
    }
    
}
