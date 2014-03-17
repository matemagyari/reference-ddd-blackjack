package org.home.blackjack.core.infrastructure.integration.camel;

import javax.annotation.Resource;
import javax.inject.Named;

import org.apache.camel.builder.RouteBuilder;
import org.home.blackjack.core.app.events.event.EventBusManager;

@Named
public class CoreRouteBuilder extends RouteBuilder {

	@Resource
	private Echo echo;
	//@Resource
	private EventBusManager eventBusManager;
	
	public void configure() {

		from("cometd://0.0.0.0:9099/inchannel")
			.to("log:aLog?showAll=true&multiline=true")
			//.bean(eventBusManager, "initialize")
			//.bean(echo, "echo")
			//.bean(eventBusManager, "flush")
			.setHeader("Access-Control-Allow-Origin", constant("*"))
			.to("cometd://0.0.0.0:9099/outchannel")
			.routeId("testroute");
		
		//TODO other from for rest calls
	}

}