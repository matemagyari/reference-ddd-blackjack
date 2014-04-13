package org.home.blackjack.core.app.events.eventhandler.gamefinished;

import javax.annotation.Resource;
import javax.inject.Named;

import org.home.blackjack.core.domain.game.event.GameFinishedEvent;
import org.home.blackjack.core.domain.table.Table;
import org.home.blackjack.core.domain.table.TableRepository;
import org.home.blackjack.util.locking.aspect.WithPessimisticLock;

@Named
public class TableClearingGameFinishedEventHandler extends GameFinishedEventHandler {

	@Resource
	private TableRepository tableRepository;

	@Override
	@WithPessimisticLock(repository=TableRepository.class, lockMethod="getTableID")
	public void handleEvent(GameFinishedEvent event) {
        Table table = tableRepository.find(event.getTableID());
        table.clearTable();
        tableRepository.update(table);
	}
}
