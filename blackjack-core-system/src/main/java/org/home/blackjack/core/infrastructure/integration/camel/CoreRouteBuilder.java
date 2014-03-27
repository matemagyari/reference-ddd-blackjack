package org.home.blackjack.core.infrastructure.integration.camel;

import javax.annotation.Resource;
import javax.inject.Named;

import org.apache.camel.component.gson.GsonDataFormat;
import org.apache.camel.spring.SpringRouteBuilder;
import org.home.blackjack.core.app.events.event.EventBusManager;
import org.home.blackjack.core.app.service.game.GameActionApplicationService;
import org.home.blackjack.core.app.service.game.GameCommand;
import org.home.blackjack.core.app.service.query.QueryingApplicationService;
import org.home.blackjack.core.app.service.query.TablesQuery;
import org.home.blackjack.core.app.service.seating.SeatingApplicationService;
import org.home.blackjack.core.app.service.seating.SeatingCommand;
import org.springframework.beans.factory.annotation.Value;

/**
 * 
 * This class also provides the ACL between client and the application (the Driving Adapters). It's a
 * thin ACL though, since simply transforms the json messages the client sends
 * to java objects. If the java classes change (field name change for example),
 * then the Client needs to change too. Or a custom deserializer should be used
 * to transform from json to object, making the ACL a little bit "thicker".
 * 
 */
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
	private GsonDataFormat queryDF = new GsonDataFormat(TablesQuery.class);
	private GsonDataFormat gameCommandDF = new GsonDataFormat(GameCommand.class);
	@Value("${blackjack.cometd.uri}")
	private String cometdUri;
	@Value("${blackjack.rest.uri}")
	private String restUri;
	

	
	public void configure() {

		from(cometdUri + "/inchannel?crossOriginFilterOn=true&allowedOrigins=*&filterPath=/*")
			.to("log:aLog?showAll=true&multiline=true")
			//.setHeader("Access-Control-Allow-Origin", constant("*"))
			.to("cometd://0.0.0.0:9099/outchannel")
			.routeId("testroute").end();
		
		from(cometdUri + "/query/request")
		    .to("log:aLog?showAll=true&multiline=true")
		    .unmarshal(queryDF)
		    .bean(queryingApplicationService,"getTables")
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

		from("jetty:"+restUri+"?matchOnUriPrefix=true")
			.to("cxfbean:registrationEndpoint");
		
	}

}