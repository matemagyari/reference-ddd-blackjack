package org.home.blackjack.core.app.service.game;

import org.home.blackjack.core.app.dto.GameCommand;
import org.home.blackjack.util.marker.hexagonal.DrivenPort;

public interface GameActionApplicationService extends DrivenPort {

	void handlePlayerAction(GameCommand command);
	
}