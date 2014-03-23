package org.home.blackjack.core.app.service.seating;

import javax.annotation.Resource;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.home.blackjack.core.app.dto.TableCommand;
import org.home.blackjack.core.domain.table.Table;
import org.home.blackjack.core.domain.table.TableRepository;
import org.home.blackjack.util.locking.aspect.WithPessimisticLock;

@Named
public class SeatingApplicationServiceImpl implements SeatingApplicationService {
	
	private static Logger LOGGER = Logger.getLogger(SeatingApplicationServiceImpl.class);
	
	@Resource
	private TableRepository tableRepository;
	
	@WithPessimisticLock(repository=TableRepository.class, lockMethod="getTableId")
	@Override
	public void seatPlayer(TableCommand tableCommand) {
        Table table = tableRepository.find(tableCommand.getTableId());
        boolean playerSeated = table.playerSits(tableCommand.getPlayerId());
        if (playerSeated) {
            tableRepository.update(table);
        }
	}
	
}
