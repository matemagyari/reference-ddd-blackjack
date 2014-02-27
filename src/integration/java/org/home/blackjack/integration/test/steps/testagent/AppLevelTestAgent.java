package org.home.blackjack.integration.test.steps.testagent;

import java.util.List;

import org.home.blackjack.app.client.game.GameAction;
import org.home.blackjack.app.client.game.GameActionApplicationService;
import org.home.blackjack.app.client.game.GameActionType;
import org.home.blackjack.app.client.player.SeatingApplicationService;
import org.home.blackjack.app.event.ExternalEventPublisher;
import org.home.blackjack.domain.game.core.GameID;
import org.home.blackjack.domain.game.event.GameFinishedEvent;
import org.home.blackjack.domain.table.core.TableID;
import org.home.blackjack.integration.test.dto.TableDO;
import org.home.blackjack.integration.test.fakes.FakeExternalEventPublisher;
import org.home.blackjack.integration.test.util.AppLevelCucumberService;
import org.home.blackjack.integration.test.util.CucumberService;

public class AppLevelTestAgent extends TestAgent {
    
	private CucumberService cucumberService;
	private SeatingApplicationService seatingApplicationService;
	private GameActionApplicationService gameActionApplicationService;
    private FakeExternalEventPublisher fakeExternalEventPublisher;
	private GameID gameID;

	@Override
    public void reset() {
    	super.reset();
    }

    @Override
    protected void initDependencies() {
    	cucumberService = new AppLevelCucumberService();
    	super.initDependencies();
    	seatingApplicationService = cucumberService().getBean(SeatingApplicationService.class);
    	gameActionApplicationService = cucumberService().getBean(GameActionApplicationService.class);
    	fakeExternalEventPublisher = cucumberService().getBean(FakeExternalEventPublisher.class);
    	
    }

	@Override
	protected CucumberService cucumberService() {
		return cucumberService;
	}

	@Override
	public void thenTablesSeenInLobby(List<TableDO> tables) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void playerSitsToTable(Integer playerId, Integer tableId) {
		seatingApplicationService.seatPlayer(generatePlayerId(playerId), generateTableId(tableId));
		
	}

	@Override
	public void thenGameStartedAtTable(Integer tableID) {
		gameID = fakeExternalEventPublisher.assertInitalCardsDealtEvent(getRealTableId(tableID));
		
	}

	@Override
	public void thenPlayerBeenDealt(Integer playerId, Integer tableId, String card) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void playerHits(Integer playerId, Integer tableId) {
		gameActionApplicationService.handlePlayerAction(new GameAction(gameID, getRealPlayerId(playerId), GameActionType.HIT));
		
	}

	@Override
	public void thenPlayerWon(Integer playerId, Integer tableId) {
		fakeExternalEventPublisher.assertDispatched(new GameFinishedEvent(gameID, 0, getRealPlayerId(playerId)));
	}
    
    


}
