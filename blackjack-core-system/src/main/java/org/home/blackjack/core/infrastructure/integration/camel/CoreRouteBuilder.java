package org.home.blackjack.core.infrastructure.integration.camel;

import javax.annotation.Resource;
import javax.inject.Named;

import org.apache.camel.component.gson.GsonDataFormat;
import org.apache.camel.spring.SpringRouteBuilder;
import org.home.blackjack.core.app.service.game.GameActionApplicationService;
import org.home.blackjack.core.app.service.query.QueryingApplicationService;
import org.home.blackjack.core.app.service.seating.SeatingApplicationService;
import org.home.blackjack.core.domain.shared.EventBusManager;
import org.home.blackjack.core.infrastructure.messaging.assembler.MessageToDTOAssembler;
import org.home.blackjack.messaging.command.GameCommandMessage;
import org.home.blackjack.messaging.command.SeatingCommandMessage;
import org.home.blackjack.messaging.query.TablesQueryMessage;
import org.springframework.beans.factory.annotation.Value;

/**
 * 
 * This class also provides the ACL between client and the application (the
 * Driving Adapters). It transforms the json messages the client sends to java
 * message objects. The message classes are defined in the Blackjack Messaging
 * Client project. The message objects then are transformed to application level
 * DTOs, Commands&Queries.
 */
@Named
public class CoreRouteBuilder extends SpringRouteBuilder {

	@Resource
	private EventBusManager eventBusManager;
	
	//Application services
	@Resource
	private QueryingApplicationService queryingApplicationService;
	@Resource
	private SeatingApplicationService seatingApplicationService;
	@Resource
	private GameActionApplicationService gameActionApplicationService;
	
	//Assemblers
	@Resource
	private MessageToDTOAssembler messageToDTOAssembler;
	
	//JSON data formatters
	private GsonDataFormat seatingCommandDF = new GsonDataFormat(SeatingCommandMessage.class);
	private GsonDataFormat gameCommandDF = new GsonDataFormat(GameCommandMessage.class);
	private GsonDataFormat queryDF = new GsonDataFormat(TablesQueryMessage.class);
	
	//URIs
	@Value("${blackjack.cometd.uri}")
	private String cometdUri;
	@Value("${blackjack.rest.uri}")
	private String restUri;
	

	
	public void configure() {

		from(cometdUri + "/echoin").routeId("echoroute")
			.to(cometdUri+"/echoout");
		
		from(cometdUri + "/query/request").routeId("query-route")
			.log("request ${body}")
		    .unmarshal(queryDF)
		    .bean(messageToDTOAssembler)
		    .bean(queryingApplicationService);

		from(cometdUri + "/command/table/sit").routeId("command-sit-route")
			.log("request ${body}")
		    .unmarshal(seatingCommandDF)
		    .bean(eventBusManager, "initialize")
		    .bean(messageToDTOAssembler)
		    .bean(seatingApplicationService)
		    .bean(eventBusManager, "flush");
		
		from(cometdUri + "/command/game").routeId("command-game-route")
			.log("request ${body}")
	    	.unmarshal(gameCommandDF)
	    	.bean(eventBusManager, "initialize")
	    	.bean(messageToDTOAssembler)
	    	.bean(gameActionApplicationService)
	    	.bean(eventBusManager, "flush");	

		from("jetty:"+restUri+"?matchOnUriPrefix=true").routeId("command-registration-route")
			.log("request ${body}")
			.setHeader("Access-Control-Allow-Origin", constant("*"))
			.setHeader("Access-Control-Allow-Methods", constant("GET,POST"))
			.setHeader("Access-Control-Allow-Headers", constant("Authorization, X-Requested-With, Content-Type, Origin, Accept"))
			.setHeader("Access-Control-Allow-Credentials", constant("true"))
			.to("cxfbean:registrationEndpoint");
		
	}

}