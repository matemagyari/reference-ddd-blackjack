package org.home.blackjack.core.infrastructure.persistence.table.store;

import java.util.List;
import java.util.concurrent.locks.Lock;

import org.home.blackjack.core.domain.shared.TableID;
import org.home.blackjack.core.domain.table.Table;
import org.home.blackjack.core.infrastructure.persistence.shared.core.PersistenceObject;
import org.home.blackjack.core.infrastructure.persistence.shared.core.PersistenceObjectId;
import org.home.blackjack.core.infrastructure.persistence.shared.core.PersistenceStore;

public interface TableStore extends PersistenceStore<Table, PersistenceObject<Table>, PersistenceObjectId<TableID>> {
    
    Lock getLockForKey(TableID key);

    List<PersistenceObject<Table>> findAll();

	boolean isEmpty();

}
