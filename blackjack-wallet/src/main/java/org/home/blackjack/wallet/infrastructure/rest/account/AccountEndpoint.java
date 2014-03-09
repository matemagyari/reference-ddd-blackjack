package org.home.blackjack.wallet.infrastructure.rest.account;

import javax.annotation.Resource;
import javax.inject.Named;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.home.blackjack.wallet.app.client.account.AccountApplicationService;
import org.home.blackjack.wallet.domain.wallet.CashAmount;
import org.home.blackjack.wallet.domain.wallet.WalletId;

@Path("wallet/account")
@Named
public class AccountEndpoint {

    @Resource
    private AccountApplicationService accountApplicationService;

    @PUT
    @Path("/create/{playerId}/{startingBalance}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createAccount(@PathParam("playerId") String playerId, @PathParam("startingBalance") String startBalance) {
        accountApplicationService.createAccount(WalletId.createFrom(playerId), CashAmount.createFrom(startBalance));
        return Response.ok().build();
    }

    @GET
    @Path("/balance/{playerId}")
    public Response getBalance(@PathParam("playerId") String playerId) {
        CashAmount balance = accountApplicationService.getBalance(WalletId.createFrom(playerId));
        return Response.ok(balance.toString()).build();
    }
}
