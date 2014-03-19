package org.home.blackjack.core.app.service.seating;

import javax.annotation.Resource;
import javax.inject.Named;

import org.home.blackjack.core.domain.shared.PlayerID;
import org.home.blackjack.core.domain.shared.TableID;
import org.home.blackjack.core.domain.table.Table;
import org.home.blackjack.core.domain.table.TableRepository;
import org.home.blackjack.util.ddd.util.Validator;
import org.home.blackjack.util.locking.aspect.WithPessimisticLock;

@Named
public class SeatingApplicationServiceImpl implements SeatingApplicationService {
	
	@Resource
	private TableRepository tableRepository;
	
	@WithPessimisticLock(repository=TableRepository.class)
	@Override
	public void seatPlayer(final TableID tableID, final PlayerID playerID) {
        Validator.notNull(playerID, tableID);
        
        Table table = tableRepository.find(tableID);
        boolean playerSeated = table.playerSits(playerID);
        if (playerSeated) {
            tableRepository.update(table);
        }
	}

	@Override
	public void unseatPlayers(TableID tableID) {
		Validator.notNull(tableID);
		
		Table table = tableRepository.find(tableID);
		table.clearTable();
		tableRepository.update(table);
	}
	
}
