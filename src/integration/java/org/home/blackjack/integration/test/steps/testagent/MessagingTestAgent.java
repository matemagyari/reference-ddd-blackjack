package org.home.blackjack.integration.test.steps.testagent;

import java.util.List;

import org.home.blackjack.integration.test.dto.TableDO;
import org.home.blackjack.integration.test.util.CucumberService;
import org.home.blackjack.integration.test.util.EndToEndCucumberService;

public class MessagingTestAgent extends TestAgent {
    

    @Override
    public void reset() {
    }

    
    @Override
    protected  void initDependencies() {
        CucumberService cucumberService = new EndToEndCucumberService();
    }


	@Override
	protected CucumberService cucumberService() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void thenTablesSeenInLobby(List<TableDO> tables) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void playerSitsToTable(Integer playerId, Integer tableId) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void thenGameStartedAtTable(Integer tableID) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void thenPlayerBeenDealt(Integer playerId, Integer tableId, String card) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void playerHits(Integer playerId, Integer tableId) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void thenPlayerWon(Integer playerId, Integer tableId) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void playerStands(Integer playerId, Integer tableId) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void thenPlayersLastActionWasStand(Integer playerId, Integer tableId) {
		// TODO Auto-generated method stub
		
	}

    
}
