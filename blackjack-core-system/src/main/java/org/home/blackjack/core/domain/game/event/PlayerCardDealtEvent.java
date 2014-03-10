package org.home.blackjack.core.domain.game.event;

import org.home.blackjack.core.domain.game.core.Card;
import org.home.blackjack.core.domain.game.core.GameID;
import org.home.blackjack.core.domain.shared.PlayerID;
import org.home.blackjack.core.domain.shared.TableID;

public class PlayerCardDealtEvent extends GameEvent {

	private final Card card;

	public PlayerCardDealtEvent(GameID gameID, TableID tableID, PlayerID playerId, Card card, int sequenceNumber) {
		super(gameID, tableID, playerId, sequenceNumber);
		this.card = card;
	}
	
	public Card getCard() {
		return card;
	}


}
