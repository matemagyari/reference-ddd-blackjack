package org.home.blackjack.core.infrastructure.adapters.driving.eventpublisher.integration.camel;

import javax.annotation.Resource;

import org.apache.camel.ProducerTemplate;
import org.home.blackjack.core.app.dto.QueryResponse;
import org.home.blackjack.core.app.events.external.ExternalDomainEvent;
import org.home.blackjack.core.app.events.external.ExternalDomainEvent.Addressee;
import org.home.blackjack.core.app.events.external.ExternalEventPublisher;
import org.home.blackjack.core.domain.shared.PlayerID;
import org.home.blackjack.util.ddd.pattern.domain.model.DomainEvent;
import org.home.blackjack.util.marker.hexagonal.DrivingAdapter;

public class CometDExternalEventPublisher implements ExternalEventPublisher, DrivingAdapter<ExternalEventPublisher> {

	@Resource
	private ProducerTemplate producerTemplate;

	@Override
	public void publish(ExternalDomainEvent event) {
		String channel = channel(event.getAddressee());
		DomainEvent domainEvent = event.getEvent();
		producerTemplate.asyncRequestBodyAndHeader("direct:events", domainEvent,"channel",channel);
	}
	
	@Override
	public void publish(QueryResponse response) {
		String channel = channel(response.getPlayerId());
		producerTemplate.asyncRequestBodyAndHeader("direct:events", response,"channel",channel);
	}

	private String channel(PlayerID playerId) {
		return "/player/"+playerId.toString()+"/query/response";
	}

	private String channel(Addressee addressee) {
		if (addressee.tableId != null)
			if (addressee.playerId != null)
				return "/table/" + addressee.tableId.toString() + "/player/" + addressee.playerId.toString();
			else
				return "/table/" + addressee.tableId.toString();
		else if (addressee.playerId != null)
			return "/player/" + addressee.playerId.toString();
		else
			return "/leaderboard";
	}


}
