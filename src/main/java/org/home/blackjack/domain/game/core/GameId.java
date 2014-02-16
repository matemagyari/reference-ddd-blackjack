package org.home.blackjack.domain.game.core;

import org.home.blackjack.domain.common.NumericId;


/**
 * Value Object
 * 
 * @author Mate
 * 
 */
public class GameId extends NumericId {
	
	public GameId(long id) {
		super(id);
	}

	@Override
	public String toString() {
		return ""+id;
	}
}
