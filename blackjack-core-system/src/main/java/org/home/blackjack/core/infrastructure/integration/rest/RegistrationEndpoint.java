package org.home.blackjack.core.infrastructure.integration.rest;

import javax.annotation.Resource;
import javax.inject.Named;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.home.blackjack.core.app.service.registration.RegistrationApplicationService;
import org.home.blackjack.core.app.service.registration.RegistrationCommand;
import org.home.blackjack.core.domain.player.PlayerName;
import org.home.blackjack.core.domain.shared.PlayerID;
import org.home.blackjack.util.marker.hexagonal.DrivenAdapter;

import com.google.gson.JsonObject;

@Path("/blackjack/register")
@Named
public class RegistrationEndpoint  implements DrivenAdapter<RegistrationApplicationService> {

	@Resource
	private RegistrationApplicationService registrationApplicationService;
	
	@PUT
	@Path("/{playerName}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN })
	public Response createAccount(@PathParam("playerName") String playerName) {
		PlayerID playerID = registrationApplicationService.playerJoins(new RegistrationCommand(playerName));
		return Response.ok(playerID.toString()).build();
	}

	//PUT doesn't work from ajax. Got sick of it..
	@GET
	@Path("/{playerName}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN })
	public Response createAccount2(@PathParam("playerName") String playerName) {
		return createAccount(playerName);
	}
	
	@GET
	@Path("/echo/{msg}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response echo(@PathParam("msg") String msg){
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("hey", msg + "response");
		//return Response.ok(jsonObject.toString()).build();
		return Response.ok("hiiii").build();
	}
}
