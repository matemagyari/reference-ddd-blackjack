package org.home.blackjack.core.infrastructure.persistence.table.store.mongo;

import javax.inject.Named;

import org.home.blackjack.core.domain.table.Table;
import org.home.blackjack.core.infrastructure.persistence.shared.core.PersistenceAssembler;
import org.home.blackjack.core.infrastructure.persistence.shared.gson.GsonBasedAssembler;
import org.home.blackjack.core.infrastructure.persistence.table.store.json.TableGsonProvider;
import org.home.blackjack.util.ddd.pattern.ID;

@Named
public class MongoTablePersistenceAssembler extends GsonBasedAssembler implements PersistenceAssembler<Table, MongoPersistenceTable> {

	public MongoTablePersistenceAssembler() {
		super(new TableGsonProvider());
	}
	
	@Override
	public Table toDomain(MongoPersistenceTable persistenceObject) {
		return fromJson(persistenceObject.getJson(), Table.class);
	}

	@Override
	public MongoPersistenceTable toPersistence(Table domainObject) {
		MongoPersistenceTableId id = toPersistence(domainObject.getID());
		String json = toJson(domainObject);
		return new MongoPersistenceTable(id,json);
	}

	@Override
	public MongoPersistenceTableId toPersistence(ID id) {
		return new MongoPersistenceTableId(id.toString());
	}
	
}
