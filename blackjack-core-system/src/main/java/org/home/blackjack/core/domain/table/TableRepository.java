package org.home.blackjack.core.domain.table;

import org.home.blackjack.core.domain.table.core.TableID;
import org.home.blackjack.util.ddd.pattern.Repository;

public interface TableRepository extends Repository<TableID, Table> {

	Table find(TableID tableID);

	void update(Table table);

	void create(Table table);

	void clear();

}
