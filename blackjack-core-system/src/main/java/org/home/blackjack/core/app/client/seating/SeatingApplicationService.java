package org.home.blackjack.core.app.client.seating;

import org.home.blackjack.core.domain.shared.PlayerID;
import org.home.blackjack.core.domain.shared.TableID;
import org.home.blackjack.util.locking.aspect.LockVal;

public interface SeatingApplicationService {

	void seatPlayer(@LockVal TableID tableID, PlayerID playerID);

	void unseatPlayers(@LockVal TableID tableID);

}