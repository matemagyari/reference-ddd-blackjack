package org.home.blackjack.core.app.service.seating;

import javax.annotation.Resource;
import javax.inject.Named;

import org.home.blackjack.core.domain.table.Table;
import org.home.blackjack.core.domain.table.TableRepository;
import org.home.blackjack.util.locking.aspect.WithPessimisticLock;

@Named
public class SeatingApplicationServiceImpl implements SeatingApplicationService {
	
	@Resource
	private TableRepository tableRepository;
	
	@Override
	@WithPessimisticLock(repository=TableRepository.class, lockMethod="getTableId")
	public void seatPlayer(SeatingCommand seatingCommand) {
        Table table = tableRepository.find(seatingCommand.getTableId());
        boolean playerSeated = table.playerSits(seatingCommand.getPlayerId());
        if (playerSeated) {
            tableRepository.update(table);
        }
	}
	
}
