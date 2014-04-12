package org.home.blackjack.core.infrastructure.persistence.table.store.inmemory;

import org.home.blackjack.core.domain.table.Table;
import org.home.blackjack.core.infrastructure.persistence.shared.PersistenceObject;

public class InMemoryPersistenceTable implements PersistenceObject<Table> {
	
	private final InMemoryPersistenceTableId id;
	private final String json;
	
	public InMemoryPersistenceTable(InMemoryPersistenceTableId id, String json) {
		this.id = id;
		this.json = json;
	}
	
	public String getJson() {
		return json;
	}

	@Override
	public InMemoryPersistenceTableId id() {
		return id;
	}

}
