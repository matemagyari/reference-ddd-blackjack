package org.home.blackjack.wallet.infrastructure.rest.transaction;

import javax.inject.Named;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.home.blackjack.wallet.app.service.transaction.TransactionApplicationService;
import org.home.blackjack.wallet.app.service.transaction.TransactionResult;
import org.home.blackjack.wallet.domain.transaction.TransactionCommand;
import org.home.blackjack.wallet.domain.transaction.TransactionId;
import org.home.blackjack.wallet.domain.transaction.TransactionType;
import org.home.blackjack.wallet.domain.wallet.CashAmount;
import org.home.blackjack.wallet.domain.wallet.Currency;
import org.home.blackjack.wallet.domain.wallet.WalletId;
import org.springframework.beans.factory.annotation.Autowired;

@Path("wallet")
@Named
public class TransactionEndpoint {

	@Autowired
	private TransactionApplicationService transactionApplicationService;

	@PUT
	@Path("/transaction/{transactionId}/{playerId}/{transactionType}/{amount}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response executeTransaction(@PathParam("transactionId") String transactionIdStr,
	                                   @PathParam("playerId") String playerId,
	                                   @PathParam("transactionType") String transactionTypeStr,
	                                   @PathParam("amount") String amountStr) {

		WalletId walletId = WalletId.createFrom(playerId);
		CashAmount amount = CashAmount.createFrom(amountStr, Currency.CHIPS);
		TransactionType transactionType = TransactionType.valueOf(transactionTypeStr);
		TransactionId transactionId = TransactionId.createFrom(transactionIdStr);
		TransactionCommand command = new TransactionCommand(transactionId, transactionType, amount);

		TransactionResult transactionResult = transactionApplicationService.handleTransaction(walletId, command);

		TransactionResponse response = new TransactionResponse(
				transactionResult.id().toString(), 
				transactionResult.originalAmount().toString(),
				transactionResult.updatedAmount().toString());
		
		return Response.ok(response).build();
	}

}
