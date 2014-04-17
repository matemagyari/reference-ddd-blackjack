package org.home.blackjack.util.ddd.pattern.domain;


/**
 * Class defining behaviour of Entities, that is to say having an id and equals and hashCode based on that id.
 * 
 * @param <T>
 *            ID type
 * 
 * @author michele.sollecito
 */
public abstract class Entity<T extends ID> implements Domain {

	private T id;
	//private final DateTime creationDate;

	protected Entity(final T id) {
		setID(id);
		//creationDate = DateTime.now();
	}
/*
	public DateTime getCreationDate() {
		return creationDate;
	}
	*/

	@Override
	public final boolean equals(final Object other) {

		if (this == other) {
			return true;
		}
		if (other == null || getClass() != other.getClass()) {
			return false;
		}
		Entity entity = (Entity) other;
		if (id != null ? !id.equals(entity.id) : entity.id != null) {
			return false;
		}
		return true;
	}

	@Override
	public final int hashCode() {

		return id != null ? id.hashCode() : 0;
	}

	public final T getID() {

		return id;
	}

	private void setID(final T id) {

		if (id == null) {
			throw new IllegalArgumentException("ID cannot be null");
		}
		this.id = id;
	}

	@Override
	public String toString() {

		return id.toString();
	}
}
