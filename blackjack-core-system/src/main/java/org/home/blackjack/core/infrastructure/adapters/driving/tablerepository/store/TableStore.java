package org.home.blackjack.core.infrastructure.adapters.driving.tablerepository.store;

import java.util.List;
import java.util.concurrent.locks.Lock;

import org.home.blackjack.core.domain.shared.TableID;
import org.home.blackjack.core.domain.table.Table;
import org.home.blackjack.util.ddd.pattern.infrastructure.persistence.PersistenceObject;
import org.home.blackjack.util.ddd.pattern.infrastructure.persistence.PersistenceObjectId;
import org.home.blackjack.util.ddd.pattern.infrastructure.persistence.PersistenceStore;

public interface TableStore extends PersistenceStore<Table, PersistenceObject<Table>, PersistenceObjectId<TableID>> {
    
    Lock getLockForKey(TableID key);

    List<PersistenceObject<Table>> findAll();

	boolean isEmpty();

}
