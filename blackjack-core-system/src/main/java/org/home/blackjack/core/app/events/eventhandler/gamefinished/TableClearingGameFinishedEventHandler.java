package org.home.blackjack.core.app.events.eventhandler.gamefinished;

import javax.annotation.Resource;
import javax.inject.Named;

import org.home.blackjack.core.domain.game.event.GameFinishedEvent;
import org.home.blackjack.core.domain.shared.EventBusManager;
import org.home.blackjack.core.domain.table.Table;
import org.home.blackjack.core.domain.table.TableRepository;

@Named
public class TableClearingGameFinishedEventHandler extends GameFinishedEventHandler {

	@Resource
	private TableRepository tableRepository;
	@Resource
    private EventBusManager eventBusManager;

	@Override
	public void handleEvent(GameFinishedEvent event) {
        eventBusManager.initialize();
        
        Table table = tableRepository.find(event.getTableID());
        table.clearTable();
        tableRepository.update(table);
        
        eventBusManager.flush();
	}
}
