package org.home.blackjack.domain;


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
