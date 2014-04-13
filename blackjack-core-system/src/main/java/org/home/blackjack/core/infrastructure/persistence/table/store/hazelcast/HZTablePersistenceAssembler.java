package org.home.blackjack.core.infrastructure.persistence.table.store.hazelcast;

import javax.inject.Named;

import org.home.blackjack.core.domain.table.Table;
import org.home.blackjack.core.infrastructure.persistence.shared.core.PersistenceAssembler;
import org.home.blackjack.core.infrastructure.persistence.shared.gson.GsonBasedAssembler;
import org.home.blackjack.core.infrastructure.persistence.table.store.json.TableGsonProvider;
import org.home.blackjack.util.ddd.pattern.ID;

@Named
public class HZTablePersistenceAssembler extends GsonBasedAssembler implements PersistenceAssembler<Table, HZPersistenceTable> {

	public HZTablePersistenceAssembler() {
		super(new TableGsonProvider());
	}
	
	@Override
	public Table toDomain(HZPersistenceTable persistenceObject) {
		return fromJson(persistenceObject.getJson(), Table.class);
	}

	@Override
	public HZPersistenceTable toPersistence(Table domainObject) {
		HZPersistenceTableId id = toPersistence(domainObject.getID());
		String json = toJson(domainObject);
		return new HZPersistenceTable(id,json);
	}

	@Override
	public HZPersistenceTableId toPersistence(ID id) {
		return new HZPersistenceTableId(id.toString());
	}
	
}
