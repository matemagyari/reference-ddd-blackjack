package org.home.blackjack.core.domain;

import javax.annotation.Resource;
import javax.inject.Named;

import org.home.blackjack.core.domain.cashier.Cashier;
import org.home.blackjack.core.domain.player.Player;
import org.home.blackjack.core.domain.player.PlayerName;
import org.home.blackjack.core.domain.player.PlayerRepository;
import org.home.blackjack.core.domain.shared.PlayerID;

@Named
public class RegisterService {
	
	@Resource
	private PlayerRepository playerRepository;
	@Resource
	private Cashier cashier;
	
	public PlayerID registerPlayer(PlayerName playerName) {
		PlayerID playerID = new PlayerID();
		playerRepository.create(new Player(playerID , playerName));
		cashier.createAccount(playerID);
		return playerID;
	}
}
