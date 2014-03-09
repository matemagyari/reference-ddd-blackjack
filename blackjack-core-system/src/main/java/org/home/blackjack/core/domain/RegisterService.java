package org.home.blackjack.core.domain;

import javax.annotation.Resource;
import javax.inject.Named;

import org.home.blackjack.core.domain.player.Player;
import org.home.blackjack.core.domain.player.PlayerName;
import org.home.blackjack.core.domain.player.PlayerRepository;
import org.home.blackjack.core.domain.shared.PlayerID;
import org.home.blackjack.core.domain.wallet.WalletService;

@Named
public class RegisterService {
	
	@Resource
	private PlayerRepository playerRepository;
	@Resource
	private WalletService walletService;
	
	public void registerPlayer(PlayerName playerName) {
		PlayerID playerID = new PlayerID();
		playerRepository.create(new Player(playerID, playerName));
		walletService.createAccount(playerID, 5000);
	}
}
