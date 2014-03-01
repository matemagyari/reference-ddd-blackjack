package org.home.blackjack.app.client.game;

import org.home.blackjack.domain.game.core.GameID;
import org.home.blackjack.util.locking.aspect.LockVal;
import org.home.blackjack.util.marker.hexagonal.DrivenPort;

public interface GameActionApplicationService extends DrivenPort {

	void handlePlayerAction(@LockVal GameID gameID, GameAction gameAction);

}