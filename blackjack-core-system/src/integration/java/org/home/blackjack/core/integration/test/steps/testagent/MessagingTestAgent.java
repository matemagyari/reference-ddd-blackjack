package org.home.blackjack.core.integration.test.steps.testagent;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.cometd.bayeux.Message;
import org.cometd.bayeux.client.ClientSessionChannel;
import org.cometd.bayeux.client.ClientSessionChannel.MessageListener;
import org.home.blackjack.core.domain.game.core.GameID;
import org.home.blackjack.core.infrastructure.integration.cometd.CometDClient;
import org.home.blackjack.core.integration.test.dto.TableDO;
import org.home.blackjack.core.integration.test.util.CucumberService;
import org.home.blackjack.core.integration.test.util.EndToEndCucumberService;

public class MessagingTestAgent extends TestAgent {
    
	private CucumberService cucumberService;
	private CometDClient cometDClient;
	private GameID gameID;
	
    @Override
    public void reset() {
    }

    
    @Override
    protected  void initDependencies() {
        cucumberService = new EndToEndCucumberService();
		super.initDependencies();
		cometDClient = new CometDClient();
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
		String channel = tableChannel(tableId);
		cometDClient.subscribeToChannel(channel);
		cometDClient.waitForMessage(channel, );
	}


	private static String tableChannel(Integer tableId) {
		return "/table/"+tableId;
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
