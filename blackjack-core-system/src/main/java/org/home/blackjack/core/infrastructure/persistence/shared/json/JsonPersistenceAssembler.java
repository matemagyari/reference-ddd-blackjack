package org.home.blackjack.core.infrastructure.persistence.shared.json;

import org.home.blackjack.core.infrastructure.persistence.shared.core.PersistenceAssembler;
import org.home.blackjack.core.infrastructure.persistence.shared.gson.GsonBasedAssembler;
import org.home.blackjack.core.infrastructure.persistence.shared.gson.GsonProvider;
import org.home.blackjack.util.ddd.pattern.Domain;
import org.home.blackjack.util.ddd.pattern.ID;

public class JsonPersistenceAssembler <T extends Domain> 
	extends GsonBasedAssembler 
	implements PersistenceAssembler<T, JsonPersistenceObject<T>> {

	private final Class<T> clazz;
	
	public JsonPersistenceAssembler(Class<T> clazz, GsonProvider gsonProvider) {
		super(gsonProvider);
		this.clazz = clazz;
	}
	
	@Override
	public T toDomain(JsonPersistenceObject<T> persistenceObject) {
		return fromJson(persistenceObject.getJson(), clazz);
	}

	@Override
	public JsonPersistenceObject<T> toPersistence(T domainObject) {
		String json = toJson(domainObject);
		return new JsonPersistenceObject<T>(json);
	}

	@Override
	public StringPersistenceId<ID> toPersistence(ID id) {
		return new StringPersistenceId<ID>(id.toString());
	}
	
}
