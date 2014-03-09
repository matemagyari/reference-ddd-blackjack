package org.home.blackjack.core.app.service.game;

import org.home.blackjack.core.domain.game.core.GameID;
import org.home.blackjack.util.locking.aspect.LockVal;
import org.home.blackjack.util.marker.hexagonal.DrivenPort;

public interface GameActionApplicationService extends DrivenPort {

	void handlePlayerAction(@LockVal GameID gameID, GameAction gameAction);

}