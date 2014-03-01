package org.home.blackjack.app.client.seating;

import org.home.blackjack.domain.shared.PlayerID;
import org.home.blackjack.domain.table.core.TableID;
import org.home.blackjack.util.locking.aspect.LockVal;

public interface SeatingApplicationService {

	void seatPlayer(@LockVal TableID tableID, PlayerID playerID);

	void unseatPlayers(@LockVal TableID tableID);

}