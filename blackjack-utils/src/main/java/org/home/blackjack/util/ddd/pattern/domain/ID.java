package org.home.blackjack.util.ddd.pattern.domain;


/**
 * Class defining the behaviour of IDs, that is to say they should be immutable value objects with an internal UUID
 * representation.
 * 
 * @author michele.sollecito
 */
public abstract class ID extends ValueObject {

	private String value;

	protected ID(final String value) {
        this.value = value;
	}
	
	@Override
	public String toString() {
		return value;
	}
}
