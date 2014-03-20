package org.home.blackjack.core.infrastructure.integration.camel;

import javax.annotation.Resource;

import org.apache.camel.ProducerTemplate;
import org.home.blackjack.core.app.events.external.ExternalDomainEvent;
import org.home.blackjack.core.app.events.external.ExternalDomainEvent.Addressee;
import org.home.blackjack.core.app.events.external.ExternalEventPublisher;
import org.home.blackjack.util.ddd.pattern.events.DomainEvent;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class CometDExternalEventPublisher implements ExternalEventPublisher {

	@Resource
	private ProducerTemplate producerTemplate;

	@Override
	public void publish(ExternalDomainEvent event) {
		String channel = channel(event.getAddressee());
		DomainEvent domainEvent = event.getEvent();
		JsonObject jsonObject = new Gson().toJsonTree(domainEvent).getAsJsonObject();
		jsonObject.addProperty("type", domainEvent.getClass().getSimpleName());
		producerTemplate.asyncSendBody("cometd://0.0.0.0:9099" + channel, jsonObject.getAsString());
	}

	private String channel(Addressee addressee) {
		if (addressee.tableId != null)
			if (addressee.playerId != null)
				return "/table/" + addressee.tableId.toString() + "/player/" + addressee.playerId.toString();
			else
				return "/table/" + addressee.tableId.toString();
		else
			return "/player/" + addressee.playerId.toString();
	}

}
