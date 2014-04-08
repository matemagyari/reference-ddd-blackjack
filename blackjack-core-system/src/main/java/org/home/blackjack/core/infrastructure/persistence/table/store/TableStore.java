package org.home.blackjack.core.infrastructure.persistence.table.store;

import java.util.List;

import org.home.blackjack.core.domain.shared.TableID;
import org.home.blackjack.core.domain.table.Table;
import org.home.blackjack.core.infrastructure.persistence.shared.PersistenceObject;
import org.home.blackjack.core.infrastructure.persistence.shared.PersistenceObjectId;
import org.home.blackjack.core.infrastructure.persistence.shared.PersistenceStore;

public interface TableStore extends PersistenceStore<Table, PersistenceObject<Table>, PersistenceObjectId<TableID>> {

    List<PersistenceObject<Table>> findAll();

}
