package org.home.blackjack.core.app.events.eventhandler;

import org.home.blackjack.core.domain.game.core.GameID;
import org.home.blackjack.core.domain.game.event.GameEvent;
import org.home.blackjack.core.domain.game.event.PlayerCardDealtEvent;
import org.home.blackjack.core.domain.shared.PlayerID;
import org.home.blackjack.core.domain.shared.TableID;

/**
 * Derived domain event. One domain event can be split into multiple. That logic belongs to the app layer (event handlers).
 * @author Mate
 *
 */
public class PublicPlayerCardDealtEvent extends GameEvent {

	public PublicPlayerCardDealtEvent(GameID gameID, TableID tableID, PlayerID actingPlayer, int sequenceNumber) {
		super(gameID, tableID, actingPlayer, sequenceNumber);
	}

	public static PublicPlayerCardDealtEvent from(PlayerCardDealtEvent event) {
		return new PublicPlayerCardDealtEvent(event.getGameID(), event.getTableID(), event.getActingPlayer(), event.getSequenceNumber());
	}

}
