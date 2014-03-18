package org.home.blackjack.core.infrastructure.integration.camel;

import javax.annotation.Resource;
import javax.inject.Named;

import org.apache.camel.spring.SpringRouteBuilder;
import org.home.blackjack.core.app.events.event.EventBusManager;

@Named
public class CoreRouteBuilder extends SpringRouteBuilder {

	@Resource
	private Echo echo;
	//@Resource
	private EventBusManager eventBusManager;
	
	public void configure() {

		from("cometd://0.0.0.0:9099/inchannel?crossOriginFilterOn=false&allowedOrigins=*&filterPath=/*")
			.to("log:aLog?showAll=true&multiline=true")
			.bean(echo)
			//.bean(eventBusManager, "initialize")
			//.bean(echo, "echo")
			//.bean(eventBusManager, "flush")
			//.setHeader("Access-Control-Allow-Origin", constant("*"))
			//.to("cometd://0.0.0.0:9099/outchannel")
			.routeId("testroute");
		
		//TODO other from for rest calls
	}

}