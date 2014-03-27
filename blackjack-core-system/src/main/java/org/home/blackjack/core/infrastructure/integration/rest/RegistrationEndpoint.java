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

@Path("/blackjack/register")
@Named
public class RegistrationEndpoint  implements DrivenAdapter<RegistrationApplicationService> {

	@Resource
	private RegistrationApplicationService registrationApplicationService;
	
	@PUT
	@Path("/{playerName}")
	@Produces(MediaType.TEXT_PLAIN)
	public Response createAccount(@PathParam("playerName") String playerName) {
		PlayerID playerID = registrationApplicationService.playerJoins(new RegistrationCommand(new PlayerName(playerName)));
		Response response = Response.ok(playerID.toString()).build();
		return response;
	}
	
	@GET
	@Path("/echo/{msg}")
	public Response echo(@PathParam("msg") String msg){
		return Response.ok(msg + "response").build();
	}
}
