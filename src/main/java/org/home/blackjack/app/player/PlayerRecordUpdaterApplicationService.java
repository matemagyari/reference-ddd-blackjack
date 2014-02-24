package org.home.blackjack.app.player;

import javax.inject.Inject;

import org.home.blackjack.domain.common.events.SubscribableEventBus;
import org.home.blackjack.domain.player.Player;
import org.home.blackjack.domain.player.PlayerRepository;
import org.home.blackjack.domain.shared.PlayerID;
import org.home.blackjack.util.locking.FinegrainedLockable;
import org.home.blackjack.util.locking.LockTemplate;
import org.home.blackjack.util.locking.VoidWriteLockingAction;

public class PlayerRecordUpdaterApplicationService {

	@Inject
	private PlayerRepository playerRepository;
	@Inject
	private SubscribableEventBus eventBuffer;
	@Inject
	private FinegrainedLockable<PlayerID> lockablePlayerRepository;
	@Inject
	private PlayerWonEventHandler playerWonEventHandler;

	private final LockTemplate lockTemplate = new LockTemplate();

	public void playerWon(PlayerID winner) {

	    eventBuffer.register(playerWonEventHandler);

		lockTemplate.doWithLock(lockablePlayerRepository, winner, new VoidWriteLockingAction<PlayerID>() {
			@Override
			public void withWriteLock(PlayerID key) {
				performTransaction(key);
			}
		});
		
		eventBuffer.flush();
	}

	private void performTransaction(PlayerID winner) {
		Player player = playerRepository.find(winner);
		player.recordWin();
		playerRepository.update(player);
	}


}
