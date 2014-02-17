package org.home.blackjack.util.ddd.pattern;

import java.util.UUID;

import javax.inject.Inject;

import org.joda.time.DateTime;

/**
 * Class defining the behaviour of IDs, that is to say they should be immutable value objects with an internal UUID
 * representation.
 * 
 * @author michele.sollecito
 */
public abstract class ID extends ValueObject {

	@Inject
	private static IDGenerationStrategy idGenerationStrategy;
	private UUID internal;

	private DateTime creationDate;

	protected ID() {

		UUID internal = idGenerationStrategy.generate();
		setInternal(internal);
		setCreationDate(new DateTime(internal.timestamp()));
	}

	protected final DateTime getCreationDate() {

		return creationDate;
	}

	private void setInternal(final UUID internal) {

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

		return internal.toString();
	}
}
