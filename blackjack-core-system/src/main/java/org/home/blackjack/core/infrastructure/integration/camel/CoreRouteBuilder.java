package org.home.blackjack.core.infrastructure.integration.camel;

import javax.annotation.Resource;
import javax.inject.Named;

import org.apache.camel.component.gson.GsonDataFormat;
import org.apache.camel.spring.SpringRouteBuilder;
import org.home.blackjack.core.app.events.event.EventBusManager;
import org.home.blackjack.core.app.service.game.GameActionApplicationService;
import org.home.blackjack.core.app.service.game.GameCommand;
import org.home.blackjack.core.app.service.query.QueryingApplicationService;
import org.home.blackjack.core.app.service.seating.SeatingApplicationService;
import org.home.blackjack.core.app.service.seating.SeatingCommand;
import org.springframework.beans.factory.annotation.Value;

@Named
public class CoreRouteBuilder extends SpringRouteBuilder {

	@Resource
	private EventBusManager eventBusManager;
	@Resource
	private QueryingApplicationService queryingApplicationService;
	@Resource
	private SeatingApplicationService seatingApplicationService;
	@Resource
	private GameActionApplicationService gameActionApplicationService;
	
	private GsonDataFormat tableCommandDF = new GsonDataFormat(SeatingCommand.class);
	private GsonDataFormat gameCommandDF = new GsonDataFormat(GameCommand.class);
	@Value("${blackjack.cometd.uri}")
	private String cometdUri;
	@Value("${blackjack.rest.uri}")
	private String restUri;
	

	
	public void configure() {

		from(cometdUri + "/inchannel?crossOriginFilterOn=true&allowedOrigins=*&filterPath=/*")
			.to("log:aLog?showAll=true&multiline=true")
			.bean(eventBusManager, "initialize")
			.bean(eventBusManager, "flush")
			//.setHeader("Access-Control-Allow-Origin", constant("*"))
			//.to("cometd://0.0.0.0:9099/outchannel")
			.routeId("testroute").end();
		
		from(cometdUri + "/query")
		    .to("log:aLog?showAll=true&multiline=true")
		    .routeId("query-route").end();

		from(cometdUri + "/command/table/sit")
		    .to("log:aLog?showAll=true&multiline=true")
		    .unmarshal(tableCommandDF)
		    .bean(eventBusManager, "initialize")
		    .bean(seatingApplicationService,"seatPlayer")
		    .bean(eventBusManager, "flush")
		. routeId("command-sit-route").end();
		
		from(cometdUri + "/command/game")
	    	.to("log:aLog?showAll=true&multiline=true")
	    	.unmarshal(gameCommandDF)
	    	.bean(eventBusManager, "initialize")
	    	.bean(gameActionApplicationService,"handlePlayerAction")
	    	.bean(eventBusManager, "flush")
	    .routeId("command-game-route").end();	
		
		from("cxfrs://"+restUri+"/route?resourceClasses=org.home.blackjack.core.infrastructure.integration.rest.RegistrationEndpoint")
    		.log("something came: ${body}");
		
		
	}

}