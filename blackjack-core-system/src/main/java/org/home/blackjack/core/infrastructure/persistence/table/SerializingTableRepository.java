package org.home.blackjack.core.infrastructure.persistence.table;

import java.util.List;

import javax.annotation.Resource;
import javax.inject.Inject;

import org.home.blackjack.core.domain.shared.TableID;
import org.home.blackjack.core.domain.table.Table;
import org.home.blackjack.core.domain.table.TableNotFoundException;
import org.home.blackjack.core.domain.table.TableRepository;
import org.home.blackjack.core.infrastructure.persistence.shared.PersistenceAssembler;
import org.home.blackjack.core.infrastructure.persistence.shared.PersistenceObject;
import org.home.blackjack.core.infrastructure.persistence.table.store.TableStore;
import org.home.blackjack.util.ddd.pattern.events.DomainEventPublisherFactory;
import org.home.blackjack.util.marker.hexagonal.DrivingAdapter;

import com.google.common.collect.Lists;

public class SerializingTableRepository implements TableRepository, DrivingAdapter<TableRepository> {
	
	private final TableStore tableStore;
	private final PersistenceAssembler<Table, PersistenceObject<Table>> tableStoreAssembler;
	
	@Resource
    private DomainEventPublisherFactory domainEventPublisherFactory;
	
	@Inject
	public SerializingTableRepository(TableStore tableStore) {
		this.tableStore = tableStore;
		this.tableStoreAssembler = tableStore.assembler();
	}
	
	@Override
	public Table find(TableID tableID) {
		PersistenceObject<Table> po = tableStore.find(tableStoreAssembler.toPersistence(tableID));
		if (po == null) {
			throw new TableNotFoundException(tableID);
		}
		Table table = toDomain(po);
		return table;
	}


	@Override
	public void update(Table table) {
		PersistenceObject<Table> po = tableStoreAssembler.toPersistence(table);
		tableStore.update(po);
	}

	@Override
	public void create(Table table) {
		PersistenceObject<Table> po = tableStoreAssembler.toPersistence(table);
		tableStore.create(po);
	}

	@Override
	public void clear() {
		tableStore.clear();
	}
	
    private Table toDomain(PersistenceObject<Table> po) {
        Table table = tableStoreAssembler.toDomain(po);
        table.setDomainEventPublisher(domainEventPublisherFactory.domainEventPublisherInstance());
        return table;
    }

    @Override
    public List<Table> findAll() {
        List<Table> tables = Lists.newArrayList();
        List<PersistenceObject<Table>> pos = tableStore.findAll();
        for (PersistenceObject<Table> persistenceObject : pos) {
            tables.add(tableStoreAssembler.toDomain(persistenceObject));
        }
        return tables;
    }
	
}
