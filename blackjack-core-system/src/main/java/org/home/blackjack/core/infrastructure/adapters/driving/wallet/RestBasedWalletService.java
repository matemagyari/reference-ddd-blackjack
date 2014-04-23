package org.home.blackjack.core.infrastructure.adapters.driving.wallet;

import java.util.UUID;

import org.home.blackjack.core.domain.cashier.WalletService;
import org.home.blackjack.core.domain.shared.PlayerID;
import org.home.blackjack.util.marker.hexagonal.DrivingAdapter;

import com.sun.jersey.api.client.Client;

public class RestBasedWalletService implements WalletService, DrivingAdapter<WalletService> {

	private static final String WALLET_REST_URL = "http://localhost:8080/blackjack-wallet/rest/wallet";
	private final Client client;
	
	public RestBasedWalletService() {
		client = Client.create();
	}

	@Override
	public void credit(PlayerID player, Integer amount) {
		doTransaction(player, amount, "CREDIT");
	}

	@Override
	public void debit(PlayerID player, Integer amount) {
		doTransaction(player, amount, "DEBIT");
	}
	
	@Override
	public void createAccount(PlayerID player, Integer startBalance) {
		String url = String.format(WALLET_REST_URL + "/account/create/%s/%s", player.toString(), startBalance);
		String response = client.resource(url).accept("application/json").put(String.class);
		//TODO mmagyari - error handling
	}
	
	private void doTransaction(PlayerID player, Integer amount, String type) {
		String transactionId = UUID.randomUUID().toString();
		String url = String.format(WALLET_REST_URL + "/transaction/%s/%s/%s/%d",transactionId, player.toString(), type, amount);
		String response = client.resource(url).accept("application/json").put(String.class);
		//TODO mmagyari - error handling
	}

}
