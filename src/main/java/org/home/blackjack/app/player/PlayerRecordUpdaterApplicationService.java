package org.home.blackjack.app.player;

import javax.inject.Inject;

import org.home.blackjack.domain.player.Player;
import org.home.blackjack.domain.player.PlayerRepository;
import org.home.blackjack.domain.shared.PlayerID;

public class PlayerRecordUpdaterApplicationService {
	
	@Inject
	private PlayerRepository playerRepository;

	public void playerWon(PlayerID winner) {
		// TODO lock acquire
		Player player = playerRepository.get(winner);
		player.recordWin();
		//TODO lock release
		
	}

}
