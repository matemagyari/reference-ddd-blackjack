package org.home.blackjack.app.table;

import java.util.List;

import javax.inject.Inject;

import org.home.blackjack.app.player.SeatingService;
import org.home.blackjack.domain.common.events.EventSubscriber;
import org.home.blackjack.domain.common.events.SubscribableEventBus;
import org.home.blackjack.domain.game.Game;
import org.home.blackjack.domain.game.GameFactory;
import org.home.blackjack.domain.game.GameRepository;
import org.home.blackjack.domain.game.core.GameID;
import org.home.blackjack.domain.game.event.GameFinishedEvent;
import org.home.blackjack.domain.shared.PlayerID;
import org.home.blackjack.domain.table.Table;
import org.home.blackjack.domain.table.TableRepository;
import org.home.blackjack.domain.table.core.TableID;
import org.home.blackjack.domain.table.event.TableIsFullEvent;
import org.home.blackjack.util.ddd.pattern.DomainEvent;
import org.home.blackjack.util.locking.FinegrainedLockable;
import org.home.blackjack.util.locking.LockTemplate;
import org.home.blackjack.util.locking.VoidWriteLockingAction;

public class TableApplicationService {
	
	@Inject
	private SubscribableEventBus eventBuffer;
	@Inject
	private TableRepository tableRepository;
	@Inject
	private GameRepository gameRepository;
	@Inject
	private FinegrainedLockable<TableID> lockableTableRepository;
	
	private final LockTemplate lockTemplate = new LockTemplate();
	
	public void seatPlayerInTransaction(final PlayerID playerID, final TableID tableID) {
	
		subscribeForTableIsFullEvent();
		
		lockTemplate.doWithLock(lockableTableRepository, tableID,  new VoidWriteLockingAction<TableID>() {
            @Override
            public void withWriteLock(TableID key) {
            	seatPlayer(playerID, tableID);
            }
        } );

		eventBuffer.flush();
	}
	
	public void seatPlayer(PlayerID playerID, TableID tableID) {
		
		Table table = tableRepository.find(tableID);
		boolean playerSeated = table.playerSits(playerID);
		if (playerSeated) {
			tableRepository.update(table);
		}
	}

	public void unseatPlayers(TableID tableID) {
		Table table = tableRepository.find(tableID);
		table.clearTable();
		tableRepository.update(table);
	}
	
	private void subscribeForTableIsFullEvent() {
		eventBuffer.register(new EventSubscriber<TableIsFullEvent>() {

			public boolean subscribedTo(DomainEvent event) {
				return event instanceof TableIsFullEvent;
			}

			public void handleEvent(TableIsFullEvent event) {
				Game game = event.getGame();
				gameRepository.create(game);
				game.dealInitialCards();
				//no need for transaction here
				gameRepository.update(game);
			}
		});
	}
}
