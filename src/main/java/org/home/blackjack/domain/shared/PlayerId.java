package org.home.blackjack.domain.shared;

import org.home.blackjack.domain.common.NumericId;



/**
 * Value Object
 * 
 * @author Mate
 * 
 */
public class PlayerId extends NumericId {
	
	public PlayerId(long id) {
		super(id);
	}

	@Override
	public String toString() {
		return id + "";
	}

}
