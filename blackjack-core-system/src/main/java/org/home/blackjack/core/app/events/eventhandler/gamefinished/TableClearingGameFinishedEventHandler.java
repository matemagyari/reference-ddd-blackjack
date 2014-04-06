package org.home.blackjack.core.app.events.eventhandler.gamefinished;

import javax.annotation.Resource;
import javax.inject.Named;

import org.home.blackjack.core.domain.game.event.GameFinishedEvent;
import org.home.blackjack.core.domain.table.Table;
import org.home.blackjack.core.domain.table.TableRepository;

@Named
public class TableClearingGameFinishedEventHandler extends GameFinishedEventHandler {

	@Resource
	private TableRepository tableRepository;

	@Override
	public void handleEvent(GameFinishedEvent event) {
        Table table = tableRepository.find(event.getTableID());
        table.clearTable();
        tableRepository.update(table);
	}
}
