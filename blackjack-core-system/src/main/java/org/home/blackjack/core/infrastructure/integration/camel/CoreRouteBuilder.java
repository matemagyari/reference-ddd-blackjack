package org.home.blackjack.core.infrastructure.integration.camel;

import javax.annotation.Resource;
import javax.inject.Named;

import org.apache.camel.component.gson.GsonDataFormat;
import org.apache.camel.spring.SpringRouteBuilder;
import org.home.blackjack.core.app.dto.GameCommand;
import org.home.blackjack.core.app.dto.TableCommand;
import org.home.blackjack.core.app.events.event.EventBusManager;
import org.home.blackjack.core.app.service.game.GameActionApplicationService;
import org.home.blackjack.core.app.service.query.QueryingApplicationService;
import org.home.blackjack.core.app.service.seating.SeatingApplicationService;
import org.springframework.beans.factory.annotation.Value;

@Named
public class CoreRouteBuilder extends SpringRouteBuilder {

	@Resource
	private Echo echo;
	@Resource
	private EventBusManager eventBusManager;
	@Resource
	private QueryingApplicationService queryingApplicationService;
	@Resource
	private SeatingApplicationService seatingApplicationService;
	@Resource
	private GameActionApplicationService gameActionApplicationService;
	
	private GsonDataFormat tableCommandDF = new GsonDataFormat(TableCommand.class);
	private GsonDataFormat gameCommandDF = new GsonDataFormat(GameCommand.class);
	@Value("${blackjack.cometd.uri}")
	private String source;

	
	public void configure() {

		from(source + "/inchannel?crossOriginFilterOn=true&allowedOrigins=*&filterPath=/*")
			.to("log:aLog?showAll=true&multiline=true")
			.bean(eventBusManager, "initialize")
			.bean(echo)
			.bean(eventBusManager, "flush")
			//.setHeader("Access-Control-Allow-Origin", constant("*"))
			//.to("cometd://0.0.0.0:9099/outchannel")
			.routeId("testroute").end();
		
		from(source + "/query")
		    .to("log:aLog?showAll=true&multiline=true")
		    .bean(echo)
		    .routeId("query-route").end();

		from(source + "/command/table/sit")
		    .to("log:aLog?showAll=true&multiline=true")
		    .unmarshal(tableCommandDF)
		    .bean(eventBusManager, "initialize")
		    .bean(seatingApplicationService,"seatPlayer")
		    .bean(eventBusManager, "flush")
		. routeId("command-sit-route").end();
		
		from(source + "/command/game")
	    	.to("log:aLog?showAll=true&multiline=true")
	    	.unmarshal(gameCommandDF)
	    	.bean(eventBusManager, "initialize")
	    	.bean(gameActionApplicationService,"handlePlayerAction")
	    	.bean(eventBusManager, "flush")
	    .routeId("command-game-route").end();		
		
	}

}