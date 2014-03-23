package org.home.blackjack.core.integration.test.steps.testagent;

import java.util.List;
import java.util.Map;

import org.home.blackjack.core.app.events.eventhandler.PublicPlayerCardDealtEvent;
import org.home.blackjack.core.app.service.game.GameCommand;
import org.home.blackjack.core.app.service.seating.SeatingCommand;
import org.home.blackjack.core.domain.game.core.GameID;
import org.home.blackjack.core.domain.game.event.GameFinishedEvent;
import org.home.blackjack.core.domain.game.event.InitalCardsDealtEvent;
import org.home.blackjack.core.domain.game.event.PlayerCardDealtEvent;
import org.home.blackjack.core.domain.game.event.PlayerStandsEvent;
import org.home.blackjack.core.domain.player.PlayerName;
import org.home.blackjack.core.domain.shared.PlayerID;
import org.home.blackjack.core.domain.table.event.PlayerIsSeatedEvent;
import org.home.blackjack.core.infrastructure.integration.cometd.CometDClient;
import org.home.blackjack.core.infrastructure.integration.cometd.CometDClient.MessageMatcher;
import org.home.blackjack.core.infrastructure.integration.rest.RestClient;
import org.home.blackjack.core.integration.test.dto.CardDO;
import org.home.blackjack.core.integration.test.dto.TableDO;
import org.home.blackjack.core.integration.test.util.CucumberService;
import org.home.blackjack.core.integration.test.util.EndToEndCucumberService;
import org.home.blackjack.core.integration.test.util.Util;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class MessagingTestAgent extends TestAgent {
    
	private CucumberService cucumberService;
	private CometDClient cometDClient;
	private RestClient restClient;
	private GameID gameID;
	
    @Override
    public void reset() {
    	super.reset();
    }

    
    @Override
    protected  void initDependencies() {
        cucumberService = new EndToEndCucumberService();
		super.initDependencies();
		//TODO put into properties file
		restClient = new RestClient();
		cometDClient = new CometDClient("http://0.0.0.0:9099/cometd");
		cometDClient.handshake();
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
		String tableChannel = tableChannel(tableId);
		cometDClient.subscribeToChannel(tablePlayerChannel(playerId, tableId));
		cometDClient.subscribeToChannel(playerChannel(playerId));
		cometDClient.subscribeAndPublish(tableChannel, "/command/table/sit", command(playerId, tableId));
		cometDClient.verifyMessageArrived(tableChannel, new PlayerIsSeatedEvent(convertTableId(tableId), convertPlayerId(playerId)));
	}


	@Override
	public void thenGameStartedAtTable(final Integer tableId) {
		MessageMatcher matcher = new MessageMatcher() {
			@Override
			public boolean match(JsonObject jsonObject) {
				if (!Util.typeMatch(InitalCardsDealtEvent.class, jsonObject)) {
					return false;
				}
				InitalCardsDealtEvent event = Util.convert(InitalCardsDealtEvent.class, jsonObject);
				return event.getTableID().equals(convertTableId(tableId));
			}
		};
		JsonObject jsonObject = cometDClient.verifyMessageArrived(tableChannel(tableId), matcher);
		InitalCardsDealtEvent event = Util.convert(InitalCardsDealtEvent.class, jsonObject);
		gameID = event.getGameID();
	}


	@Override
	public void thenPlayerBeenDealt(final Integer playerId, Integer tableId, final String card) {
		cometDClient.verifyMessageArrived(tablePlayerChannel(playerId, tableId), new MessageMatcher() {
			@Override
			public boolean match(JsonObject jsonObject) {
				if (!Util.typeMatch(PlayerCardDealtEvent.class, jsonObject)) {
					return false;
				}
				PlayerCardDealtEvent anEvent = Util.convert(PlayerCardDealtEvent.class, jsonObject);
				return anEvent.getCard().equals(CardDO.toCard(card)) 
						&& anEvent.getActingPlayer().equals(convertPlayerId(playerId))
						&& anEvent.getGameID().equals(gameID);
			}
		});
		cometDClient.verifyMessageArrived(tableChannel(tableId), new MessageMatcher() {
			@Override
			public boolean match(JsonObject jsonObject) {
				if (!Util.typeMatch(PublicPlayerCardDealtEvent.class, jsonObject)) {
					return false;
				}
				PublicPlayerCardDealtEvent anEvent = Util.convert(PublicPlayerCardDealtEvent.class, jsonObject);
				return anEvent.getActingPlayer().equals(convertPlayerId(playerId))
						&& anEvent.getGameID().equals(gameID);
			}
		});		
	}

	@Override
	public void thenPlayersLastActionWasStand(final Integer playerId, Integer tableId) {
		cometDClient.verifyMessageArrived(tableChannel(tableId), new MessageMatcher() {
			@Override
			public boolean match(JsonObject jsonObject) {
				if (!Util.typeMatch(PlayerStandsEvent.class, jsonObject)) {
					return false;
				}
				PlayerStandsEvent anEvent = Util.convert(PlayerStandsEvent.class, jsonObject);
				return anEvent.getActingPlayer().equals(convertPlayerId(playerId))
						&& anEvent.getGameID().equals(gameID);
			}
		});	
	}
	
	@Override
	public void playerHits(Integer playerId, Integer tableId) {
		String command = new Gson().toJson(new GameCommand(playerId.toString(), gameID.toString(), "HIT"));
		cometDClient.publish("/command/game", command);
	}


	@Override
	public void thenPlayerWon(final Integer playerId, final Integer tableId) {
		cometDClient.verifyMessageArrived(tableChannel(tableId), new MessageMatcher() {
			
			@Override
			public boolean match(JsonObject jsonObject) {
				if (!Util.typeMatch(GameFinishedEvent.class, jsonObject)) {
					return false;
				}
				GameFinishedEvent anEvent = Util.convert(GameFinishedEvent.class, jsonObject);
				return anEvent.winner().equals(convertPlayerId(playerId))
						&& anEvent.getGameID().equals(gameID)
						&& anEvent.getTableID().equals(convertTableId(tableId));
			}
		});
	}

	@Override
	public void playerRegisters(String name) {
		String response = restClient.register(name);
	}
	
	@Override
	public void playerStands(Integer playerId, Integer tableId) {
		String command = new Gson().toJson(new GameCommand(playerId.toString(), gameID.toString(), "STAND"));
		cometDClient.publish("/command/game", command);
	}
	
	private static String command(Integer playerId, Integer tableId) {
		return new Gson().toJson(new SeatingCommand(playerId.toString(), tableId.toString()));
	}
	private static String tableChannel(Integer tableId) {
		return "/table/"+convertTableId(tableId);
	}
	private static String playerChannel(Integer playerId) {
		return "/player/"+playerId;
	}

	private static String tablePlayerChannel(Integer playerId, Integer tableId) {
		return "/table/"+tableId+"/player/"+playerId;
	}


}
