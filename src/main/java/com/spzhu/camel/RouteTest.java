package com.spzhu.camel;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.model.ModelCamelContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RouteTest extends RouteBuilder {



    public static void main(String[] args) throws Exception {
        Logger Log = LoggerFactory.getLogger(RouteTest.class);
        Log.debug("In main()");

        ModelCamelContext modelCamelContext = new DefaultCamelContext();
        modelCamelContext.start();
        modelCamelContext.addRoutes(new RouteTest());

        synchronized (RouteTest.class) {
            RouteTest.class.wait();
        }
    }

    @Override
    public void configure() {
        Logger Log = LoggerFactory.getLogger(RouteTest.class);
        Log.debug("In configure()");
        /*
        restConfiguration()
                .host("localhost")
                .component("jetty")
                .port(8085);
        */
        from("jetty:http://0.0.0.0:8282/doRouteTest")
                .process(new HttpProcessor())
                .to("log:hi?showExchangeId=true");
    }
}
