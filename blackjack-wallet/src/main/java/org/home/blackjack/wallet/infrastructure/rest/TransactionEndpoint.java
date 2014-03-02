package org.home.blackjack.wallet.infrastructure.rest;

import javax.inject.Named;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.home.blackjack.wallet.app.client.transaction.TransactionApplicationService;
import org.home.blackjack.wallet.app.client.transaction.TransactionResult;
import org.home.blackjack.wallet.domain.transaction.TransactionCommand;
import org.home.blackjack.wallet.domain.transaction.TransactionId;
import org.home.blackjack.wallet.domain.transaction.TransactionType;
import org.home.blackjack.wallet.domain.wallet.CashAmount;
import org.home.blackjack.wallet.domain.wallet.WalletId;
import org.springframework.beans.factory.annotation.Autowired;

@Path("wallet")
@Named
public class TransactionEndpoint {

	@Autowired
	private TransactionApplicationService transactionApplicationService;

	@GET
	@Path("/echo")
	@Produces(MediaType.APPLICATION_JSON)
	public String getMessage() {
		return "{\"message\":\"hello\"}";
	}

	@PUT
	@Path("/transaction")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response executeTransaction(TransactionRequest request) {

		WalletId walletId = WalletId.createFrom(request.walletId());
		CashAmount amount = CashAmount.createFrom(request.amount());
		TransactionType transactionType = TransactionType.valueOf(request.transactionType());
		TransactionId transactionId = TransactionId.createFrom(request.transactionId());
		TransactionCommand command = new TransactionCommand(transactionId, transactionType, amount);

		TransactionResult transactionResult = transactionApplicationService.handleTransaction(walletId, command);

		TransactionResponse response = new TransactionResponse(
				transactionResult.id().toString(), 
				transactionResult.originalAmount().toString(),
				transactionResult.updatedAmount().toString());
		
		return Response.ok(response).build();
	}

	@GET
	@Path("/balance")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getBalance(String walletId) {
		CashAmount balance = transactionApplicationService.getBalance(WalletId.createFrom(walletId));
		return Response.ok(balance.toString()).build();
	}
}
