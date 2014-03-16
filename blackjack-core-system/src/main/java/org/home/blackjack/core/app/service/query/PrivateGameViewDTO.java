package org.home.blackjack.core.app.service.query;

import java.util.List;

import org.home.blackjack.core.domain.game.core.Card;

public class PrivateGameViewDTO {
	
	private  List<Card> playerCards;
	private  int opponentCards;
	private  PlayerStatus opponentStatus;
	private  PlayerStatus playerStatus;
	private  boolean playersTurn;
	
	public PrivateGameViewDTO() {
	}

}
