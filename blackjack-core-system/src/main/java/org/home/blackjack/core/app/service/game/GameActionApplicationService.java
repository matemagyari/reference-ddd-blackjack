package org.home.blackjack.core.app.service.game;

import org.home.blackjack.util.locking.aspect.WithPessimisticLock;
import org.home.blackjack.util.marker.hexagonal.DrivenPort;

/**
 * Interface needed for the AspectJ proxy for {@link WithPessimisticLock}
 */
public interface GameActionApplicationService extends DrivenPort {
	
	 void handlePlayerAction(GameCommand command);

}
