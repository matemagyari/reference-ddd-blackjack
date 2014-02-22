package org.home.blackjack.domain.table;

import java.util.List;

import org.home.blackjack.domain.table.core.TableID;
import org.home.blackjack.util.ddd.pattern.Repository;

public interface TableRepository extends Repository<TableID, Table> {

	List<Table> find(TablesToSeatSpecification tablesToSeatSpecification);

	Table find(TableID tableID);

	void update(Table table);

}
