package org.home.blackjack.core.infrastructure.integration.camel;

import javax.annotation.Resource;
import javax.inject.Named;

import org.apache.camel.spring.SpringRouteBuilder;
import org.home.blackjack.core.infrastructure.messaging.assembler.EventToMessageAssembler;
import org.home.blackjack.core.infrastructure.messaging.assembler.MessageToJsonAssembler;
import org.springframework.beans.factory.annotation.Value;

/**
 * This class also provides the ACL between the application and the client
 */
@Named
public class EventRouteBuilder extends SpringRouteBuilder {
	
	@Value("${blackjack.cometd.uri}")
	private String cometdUri;
	@Resource
	private EventToMessageAssembler eventToMessageAssembler;
	@Resource
	private MessageToJsonAssembler messageToJsonAssembler;
	
	
	public void configure() {

		from("direct:events").routeId("event-route")
			.bean(eventToMessageAssembler)
			.bean(messageToJsonAssembler)
			.log("event ${body} sent to ${header.channel}")
		    .recipientList(simple(cometdUri+"${header.channel}"));
	}
}