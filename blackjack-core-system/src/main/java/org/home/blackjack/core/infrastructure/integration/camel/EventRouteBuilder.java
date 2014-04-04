package org.home.blackjack.core.infrastructure.integration.camel;

import javax.annotation.Resource;
import javax.inject.Named;

import org.apache.camel.spring.SpringRouteBuilder;
import org.home.blackjack.core.infrastructure.messaging.assembler.EventToMessageAssembler;
import org.home.blackjack.core.infrastructure.messaging.assembler.MessageToJsonAssembler;
import org.home.blackjack.messaging.common.Message;
import org.springframework.beans.factory.annotation.Value;

/**
 * This class provides the ACL between the application and the client.
 * Transforms messages (DTOs) extending {@link Message} to JSONs, appending a
 * type field, which is currently, for the sake of simplicity, the simple class
 * name of the DTO.
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