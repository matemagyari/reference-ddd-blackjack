package org.home.blackjack.app.client.player;

import javax.annotation.Resource;
import javax.inject.Named;

import org.home.blackjack.app.eventhandler.TableIsFullEventHandler;
import org.home.blackjack.domain.common.Validator;
import org.home.blackjack.domain.shared.PlayerID;
import org.home.blackjack.domain.table.Table;
import org.home.blackjack.domain.table.TableRepository;
import org.home.blackjack.domain.table.core.TableID;
import org.home.blackjack.util.ddd.pattern.events.LightweightDomainEventBus;
import org.home.blackjack.util.ddd.pattern.events.SubscribableEventBus;

@Named
public class SeatingApplicationService {
	
	@Resource
	private TableRepository tableRepository;
	@Resource
	private TableIsFullEventHandler tableIsFullEventHandler;
	
	public void seatPlayer(final PlayerID playerID, final TableID tableID) {
		Validator.notNull(playerID, tableID);
	
		SubscribableEventBus eventBus = LightweightDomainEventBus.subscribableEventBusInstance();
		eventBus.reset();
		eventBus.register(tableIsFullEventHandler);
		
		seatPlayerInTransaction(playerID, tableID);
		
		eventBus.flush();
		
	}
	
	private void seatPlayerInTransaction(PlayerID playerID, TableID tableID) {
		Validator.notNull(playerID, tableID);
		
		Table table = tableRepository.find(tableID);
		boolean playerSeated = table.playerSits(playerID);
		if (playerSeated) {
			tableRepository.update(table);
		}
	}

	public void unseatPlayers(TableID tableID) {
		Validator.notNull(tableID);
		
		Table table = tableRepository.find(tableID);
		table.clearTable();
		tableRepository.update(table);
	}
	
}
