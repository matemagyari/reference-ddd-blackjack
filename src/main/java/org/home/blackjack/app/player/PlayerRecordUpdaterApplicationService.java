package org.home.blackjack.app.player;

import javax.inject.Inject;

import org.home.blackjack.app.event.ExternalEventPublisher;
import org.home.blackjack.domain.common.events.EventSubscriber;
import org.home.blackjack.domain.common.events.SubscribableEventBus;
import org.home.blackjack.domain.player.Player;
import org.home.blackjack.domain.player.PlayerRepository;
import org.home.blackjack.domain.player.event.PlayerWonEvent;
import org.home.blackjack.domain.shared.PlayerID;
import org.home.blackjack.util.ddd.pattern.DomainEvent;
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
	private ExternalEventPublisher externalEventPublisher;

	private final LockTemplate lockTemplate = new LockTemplate();

	public void playerWon(PlayerID winner) {

		subscribeForPlayerWonEvent();

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

	private void subscribeForPlayerWonEvent() {
		eventBuffer.register(new EventSubscriber<PlayerWonEvent>() {

			@Override
			public boolean subscribedTo(DomainEvent event) {
				return event instanceof PlayerWonEvent;
			}

			@Override
			public void handleEvent(PlayerWonEvent event) {
				externalEventPublisher.publish(event);
			}

		});
	}

}
