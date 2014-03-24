package org.home.blackjack.core.app.service.query;

import java.util.List;

import javax.annotation.Resource;
import javax.inject.Named;

import org.home.blackjack.core.app.events.external.ExternalEventPublisher;
import org.home.blackjack.core.domain.game.Game;
import org.home.blackjack.core.domain.game.GameRepository;
import org.home.blackjack.core.domain.game.view.PlayerGameView;
import org.home.blackjack.core.domain.shared.PlayerID;
import org.home.blackjack.core.domain.shared.TableID;
import org.home.blackjack.core.domain.table.Table;
import org.home.blackjack.core.domain.table.TableRepository;

import com.google.common.collect.Lists;

@Named
public class QueryingApplicationService {

	@Resource
	private TableRepository tableRepository;
	@Resource
	private GameRepository gameRepository;
	@Resource
	private ExternalEventPublisher externalEventPublisher;
	
	public PrivateGameViewDTO readMyGame(TableID tableId, PlayerID playerID) {
		Game game = gameRepository.find(tableId);
		
		PlayerGameView playerGameView = null;
		return null;
	}
	
	public void getTables(PlayerID playerID) {
		List<TableViewDTO> tableViewDTOs = Lists.newArrayList();
		List<Table> tables =  tableRepository.findAll();
		for (Table table : tables) {
			tableViewDTOs.add(new TableViewDTO(table.getID(),  table.getPlayers()));
		}
		externalEventPublisher.publish(new TablesDTO(playerID, tableViewDTOs));
	}
	
}
