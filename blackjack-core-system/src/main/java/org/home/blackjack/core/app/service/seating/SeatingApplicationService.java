package org.home.blackjack.core.app.service.seating;

import org.home.blackjack.core.app.dto.TableCommand;

public interface SeatingApplicationService {

	void seatPlayer(TableCommand tableCommand);

}