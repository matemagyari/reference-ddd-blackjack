package org.home.blackjack.core.app.service.query;

import java.util.List;

import javax.annotation.Resource;
import javax.inject.Named;

import org.home.blackjack.core.app.events.external.ExternalEventPublisher;
import org.home.blackjack.core.domain.table.Table;
import org.home.blackjack.core.domain.table.TableRepository;
import org.home.blackjack.util.marker.hexagonal.DrivenPort;

import com.google.common.collect.Lists;

@Named
public class QueryingApplicationService implements DrivenPort {

	@Resource
	private TableRepository tableRepository;
	@Resource
	private ExternalEventPublisher externalEventPublisher;
	
	public void getTables(TablesQuery tablesQuery) {
		List<TableViewDTO> tableViewDTOs = Lists.newArrayList();
		List<Table> tables =  tableRepository.findAll();
		for (Table table : tables) {
			tableViewDTOs.add(new TableViewDTO(table.getID(),  table.getPlayers()));
		}
		externalEventPublisher.publish(new TableListViewDTO(tablesQuery.getPlayerID(), tableViewDTOs));
	}
	
}
