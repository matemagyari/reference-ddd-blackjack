package org.home.blackjack.util.ddd.pattern.domain;

import java.util.UUID;

import org.joda.time.DateTime;

/**
 * Class defining the behaviour of IDs, that is to say they should be immutable value objects with an internal UUID
 * representation.
 * 
 * @author michele.sollecito
 */
public abstract class ID extends ValueObject {

	private static IDGenerationStrategy idGenerationStrategy = new IDGenerationStrategy() {
		@Override
		public UUID generate() {
			return UUID.randomUUID();
		}
	};
	private String internal;

	private DateTime creationDate;

	protected ID(String uuid) {
		setInternal(uuid);
	}
	protected ID() {

		UUID internal = idGenerationStrategy.generate();
		setInternal(internal.toString());
		//setCreationDate(new DateTime(internal.timestamp()));
	}

	protected final DateTime getCreationDate() {

		return creationDate;
	}

	private void setInternal(final String internal) {

		if (internal == null) {
			throw new IllegalArgumentException("Internal ID cannot be null");
		}
		this.internal = internal;
	}

	private void setCreationDate(final DateTime creationDate) {

		this.creationDate = creationDate;
	}
	
	@Override
	public String toString() {
		return internal;
	}
}
