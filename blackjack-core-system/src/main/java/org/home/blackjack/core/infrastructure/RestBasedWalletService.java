package org.home.blackjack.core.infrastructure;

import javax.inject.Named;

import org.home.blackjack.core.domain.game.core.GameID;
import org.home.blackjack.core.domain.shared.PlayerID;
import org.home.blackjack.core.domain.wallet.Reason;
import org.home.blackjack.core.domain.wallet.WalletService;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

@Named
public class RestBasedWalletService implements WalletService {

	@Override
	public void giveTheWin(GameID gameID, PlayerID winner) {
	}

	@Override
	public void debitEntryFee(PlayerID playerID) {
		// TODO Auto-generated method stub
	}
	@Override
	public void createAccount(PlayerID playerID, int startBalance) {
		// TODO Auto-generated method stub
	}

	private void xxx() {

		Client client = Client.create();

		WebResource webResource = client.resource("http://localhost:8080/blackjack-wallet/rest/wallet/");

		ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);

		if (response.getStatus() != 200) {
			throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
		}

		String output = response.getEntity(String.class);

		System.out.println("Output  from Server .... \n");
		System.out.println(output);

	}



}
