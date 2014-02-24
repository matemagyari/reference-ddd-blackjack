package org.home.blackjack.app.client.player;

import javax.inject.Inject;

import org.home.blackjack.app.eventhandler.TableIsFullEventHandler;
import org.home.blackjack.domain.common.events.SubscribableEventBus;
import org.home.blackjack.domain.shared.PlayerID;
import org.home.blackjack.domain.table.Table;
import org.home.blackjack.domain.table.TableRepository;
import org.home.blackjack.domain.table.core.TableID;
import org.home.blackjack.util.locking.FinegrainedLockable;
import org.home.blackjack.util.locking.LockTemplate;
import org.home.blackjack.util.locking.VoidWriteLockingAction;

public class SeatingApplicationService {
	
	@Inject
	private SubscribableEventBus eventBuffer;
	@Inject
	private TableRepository tableRepository;
	@Inject
	private TableIsFullEventHandler tableIsFullEventHandler;
	@Inject
	private FinegrainedLockable<TableID> lockableTableRepository;
	
	private final LockTemplate lockTemplate = new LockTemplate();
	
	public void seatPlayerInTransaction(final PlayerID playerID, final TableID tableID) {
	
		eventBuffer.register(tableIsFullEventHandler);
		
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
	
}
