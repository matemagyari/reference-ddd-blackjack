package org.home.blackjack.domain.table;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import org.home.blackjack.domain.game.event.NoTablesAvailable;
import org.home.blackjack.domain.shared.PlayerID;
import org.home.blackjack.domain.table.core.TableID;
import org.home.blackjack.util.ddd.pattern.EventPublisher;
import org.home.blackjack.util.locking.FinegrainedLockable;
import org.home.blackjack.util.locking.LockTemplate;
import org.home.blackjack.util.locking.VoidWriteLockingAction;

/**
 * Domain service
 * 
 * @author mate.magyari
 * 
 */
public class SeatingService {

	private FinegrainedLockable<TableID> lockableTableRepository;
	private TableRepository tableRepository;
	private EventPublisher eventPublisher;

	private final LockTemplate lockTemplate = new LockTemplate();

	private boolean seatPlayer(final PlayerID playerID, TableID tableID) {
		final AtomicBoolean seatingSuccess = new AtomicBoolean(false);
		lockTemplate.doWithLock(lockableTableRepository, tableID, new VoidWriteLockingAction<TableID>() {
			@Override
			public void withWriteLock(TableID key) {
				boolean result = performTransaction(key, playerID);
				seatingSuccess.set(result);
			}
		});
		return seatingSuccess.get();
	}

	public void seatPlayer(final PlayerID playerID) {
		List<Table> tables = tableRepository.find(new TablesToSeatSpecification());
		if (tables.isEmpty()) {
			eventPublisher.publish(new NoTablesAvailable(playerID));
		}
		for (Table table : tables) {
			if (seatPlayer(playerID, table.getID())) {
				break;
			}
		}
	}

	private boolean performTransaction(TableID tableID, PlayerID playerID) {
		Table table = tableRepository.find(tableID);
		if (table.playerSits(playerID)) {
			tableRepository.update(table);
			return true;
		}
		return false;
	}

}
