package org.home.blackjack.core.app.service.seating;

import org.home.blackjack.util.locking.aspect.WithPessimisticLock;
import org.home.blackjack.util.marker.hexagonal.DrivenPort;

/**
 * Interface needed for the AspectJ proxy for {@link WithPessimisticLock}
 */
public interface SeatingApplicationService extends DrivenPort {
	
	void seatPlayer(SeatingCommand seatingCommand);

}
