package org.home.blackjack.core.infrastructure.integration.camel;

import javax.annotation.Resource;
import javax.inject.Named;

import org.apache.camel.ProducerTemplate;
import org.apache.log4j.Logger;
import org.home.blackjack.core.app.service.registration.RegistrationApplicationService;
import org.home.blackjack.core.app.service.registration.RegistrationCommand;
import org.home.blackjack.core.domain.shared.PlayerID;
import org.springframework.beans.factory.annotation.Value;

import com.google.gson.Gson;
import com.google.gson.JsonObject;


//TODO remove if not needed
@Named
public class CometDRegistrationService {
	
	private static Logger LOGGER = Logger.getLogger(CometDExternalEventPublisher.class);

	@Resource
	private RegistrationApplicationService registrationApplicationService;
	@Resource
	private ProducerTemplate producerTemplate;
	@Value("${blackjack.cometd.uri}")
	private String source;
	
	public void register(RegistrationCommand command) {
		PlayerID playerID = registrationApplicationService.playerJoins(command);
		JsonObject jsonObject = new Gson().toJsonTree(playerID).getAsJsonObject();
		jsonObject.addProperty("type", PlayerID.class.getSimpleName());
		//producerTemplate.asyncSendBody(source + "/registration/"+targetId, jsonObject.toString());
	}

}
