package org.home.blackjack.core.infrastructure.persistence.table.store.inmemory;

import javax.inject.Named;

import org.home.blackjack.core.domain.table.Table;
import org.home.blackjack.core.infrastructure.persistence.shared.GsonBasedAssembler;
import org.home.blackjack.core.infrastructure.persistence.shared.PersistenceAssembler;
import org.home.blackjack.core.infrastructure.persistence.table.json.TableGsonProvider;
import org.home.blackjack.util.ddd.pattern.ID;

@Named
public class InMemoryTablePersistenceAssembler extends GsonBasedAssembler implements PersistenceAssembler<Table, InMemoryPersistenceTable> {

	public InMemoryTablePersistenceAssembler() {
		super(new TableGsonProvider());
	}
	
	@Override
	public Table toDomain(InMemoryPersistenceTable persistenceObject) {
		return fromJson(persistenceObject.getJson(), Table.class);
	}

	@Override
	public InMemoryPersistenceTable toPersistence(Table domainObject) {
		InMemoryPersistenceTableId id = toPersistence(domainObject.getID());
		String json = toJson(domainObject);
		return new InMemoryPersistenceTable(id,json);
	}

	@Override
	public InMemoryPersistenceTableId toPersistence(ID id) {
		return new InMemoryPersistenceTableId(id.toString());
	}

}
