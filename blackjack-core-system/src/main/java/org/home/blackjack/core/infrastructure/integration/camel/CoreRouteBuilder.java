package org.home.blackjack.core.infrastructure.integration.camel;

import javax.annotation.Resource;
import javax.inject.Named;

import org.apache.camel.spring.SpringRouteBuilder;
import org.home.blackjack.core.app.events.event.EventBusManager;
import org.home.blackjack.core.app.service.query.QueryingApplicationService;

@Named
public class CoreRouteBuilder extends SpringRouteBuilder {

	@Resource
	private Echo echo;
	@Resource
	private EventBusManager eventBusManager;
	@Resource
	private QueryingApplicationService queryingApplicationService;
	
	public void configure() {

		from("cometd://0.0.0.0:9099/inchannel?crossOriginFilterOn=true&allowedOrigins=*&filterPath=/*")
			.to("log:aLog?showAll=true&multiline=true")
			.bean(eventBusManager, "initialize")
			.bean(echo)
			.bean(eventBusManager, "flush")
			//.setHeader("Access-Control-Allow-Origin", constant("*"))
			//.to("cometd://0.0.0.0:9099/outchannel")
			.routeId("testroute").end();
		
		from("cometd://0.0.0.0:9099/service/query?matchOnUriPrefix=true")
		    .to("log:aLog?showAll=true&multiline=true")
		    .bean(echo)
		    .routeId("testroute").end();

		from("cometd://0.0.0.0:9099/service/command?matchOnUriPrefix=true")
		    .to("log:aLog?showAll=true&multiline=true")
		    .bean(eventBusManager, "initialize")
		    .bean(echo)
		    .bean(eventBusManager, "flush")
		. routeId("testroute").end();
		
	}

}