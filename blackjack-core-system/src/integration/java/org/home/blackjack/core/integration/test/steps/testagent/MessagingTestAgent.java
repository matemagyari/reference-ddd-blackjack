package org.home.blackjack.core.integration.test.steps.testagent;

import java.util.List;

import org.home.blackjack.core.app.service.query.TablesDTO;
import org.home.blackjack.core.domain.game.core.GameID;
import org.home.blackjack.core.domain.player.Player;
import org.home.blackjack.core.domain.player.core.PlayerName;
import org.home.blackjack.core.domain.shared.PlayerID;
import org.home.blackjack.core.infrastructure.integration.cometd.CometDClient;
import org.home.blackjack.core.infrastructure.integration.cometd.CometDClient.MessageMatcher;
import org.home.blackjack.core.infrastructure.integration.rest.RestClient;
import org.home.blackjack.core.integration.test.dto.CardDO;
import org.home.blackjack.core.integration.test.dto.LeaderboardDO;
import org.home.blackjack.core.integration.test.dto.PlayerDO;
import org.home.blackjack.core.integration.test.dto.TableDO;
import org.home.blackjack.core.integration.test.util.CucumberService;
import org.home.blackjack.core.integration.test.util.EndToEndCucumberService;
import org.home.blackjack.core.integration.test.util.Util;
import org.home.blackjack.messaging.command.GameCommandMessage;
import org.home.blackjack.messaging.command.SeatingCommandMessage;
import org.home.blackjack.messaging.event.GameFinishedEventMessage;
import org.home.blackjack.messaging.event.InitialCardsDealtEventMessage;
import org.home.blackjack.messaging.event.LeaderBoardChangedEventMessage;
import org.home.blackjack.messaging.event.PlayerCardDealtEventMessage;
import org.home.blackjack.messaging.event.PlayerIsSeatedEventMessage;
import org.home.blackjack.messaging.event.PlayerStandsEventMessage;
import org.home.blackjack.messaging.event.PublicPlayerCardDealtEventMessage;
import org.home.blackjack.messaging.query.TablesQueryMessage;
import org.home.blackjack.messaging.response.TablesResponseMessage;

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
	protected void initDependencies() {
		cucumberService = new EndToEndCucumberService();
		super.initDependencies();
		// TODO put into properties file
		restClient = new RestClient();
		cometDClient = new CometDClient("http://0.0.0.0:9099/cometd");
		cometDClient.handshake();
	}

	@Override
	protected CucumberService cucumberService() {
		return cucumberService;
	}

	@Override
	public void givenRegisteredPlayers(List<PlayerDO> players) {
		for (PlayerDO playerDO : players) {
			playerRepository.create(new Player(convertPlayerId(playerDO.id), new PlayerName(playerDO.name)));
		}
		cometDClient.subscribeToChannel(leaderboardChannel());
	}

	@Override
	public void thenTablesSeenInLobby(List<TableDO> tables) {
		PlayerID randomPlayer = new PlayerID();
		TablesDTO tablesDTO = Util.convert(tables, randomPlayer);
		TablesResponseMessage message = new TablesResponseMessage(tablesDTO.getTablesWithPlayers());
		String command = new Gson().toJson(new TablesQueryMessage(randomPlayer.toString()));
		String responseChannel = playerQueryResponseChannel(randomPlayer.toString());
		cometDClient.subscribeToChannel(responseChannel);
		cometDClient.publish("/query/request", command);
		cometDClient.verifyMessageArrived(responseChannel, message);
	}

	@Override
	public void playerSitsToTable(Integer playerId, Integer tableId) {
		String tableChannel = tableChannel(tableId);
		cometDClient.subscribeToChannel(tablePlayerChannel(playerId, tableId));
		cometDClient.subscribeAndPublish(tableChannel, "/command/table/sit", command(playerId, tableId));
		cometDClient.verifyMessageArrived(tableChannel, new PlayerIsSeatedEventMessage(tableId.toString(), playerId.toString()));
	}

	@Override
	public void thenGameStartedAtTable(final Integer tableId) {
		MessageMatcher matcher = new MessageMatcher() {
			@Override
			public boolean match(JsonObject jsonObject) {
				if (!Util.typeMatch(InitialCardsDealtEventMessage.class, jsonObject)) {
					return false;
				}
				InitialCardsDealtEventMessage event = Util.convert(InitialCardsDealtEventMessage.class, jsonObject);
				return event.tableID.equals(tableId.toString());
			}
		};
		JsonObject jsonObject = cometDClient.verifyMessageArrived(tableChannel(tableId), matcher);
		InitialCardsDealtEventMessage event = Util.convert(InitialCardsDealtEventMessage.class, jsonObject);
		gameID = GameID.createFrom(event.gameID);
	}

	@Override
	public void thenPlayerBeenDealt(final Integer playerId, Integer tableId, final String card) {
		cometDClient.verifyMessageArrived(tablePlayerChannel(playerId, tableId), new MessageMatcher() {
			@Override
			public boolean match(JsonObject jsonObject) {
				if (!Util.typeMatch(PlayerCardDealtEventMessage.class, jsonObject)) {
					return false;
				}
				PlayerCardDealtEventMessage anEvent = Util.convert(PlayerCardDealtEventMessage.class, jsonObject);
				return anEvent.card.equals(CardDO.toCardDTO(card)) && anEvent.actingPlayer.equals(playerId.toString())
						&& anEvent.gameID.equals(gameID.toString());
			}
		});
		cometDClient.verifyMessageArrived(tableChannel(tableId), new MessageMatcher() {
			@Override
			public boolean match(JsonObject jsonObject) {
				if (!Util.typeMatch(PublicPlayerCardDealtEventMessage.class, jsonObject)) {
					return false;
				}
				PublicPlayerCardDealtEventMessage anEvent = Util.convert(PublicPlayerCardDealtEventMessage.class, jsonObject);
				return anEvent.actingPlayer.equals(playerId.toString()) && anEvent.gameID.equals(gameID.toString());
			}
		});
	}

	@Override
	public void thenPlayersLastActionWasStand(final Integer playerId, Integer tableId) {
		cometDClient.verifyMessageArrived(tableChannel(tableId), new MessageMatcher() {
			@Override
			public boolean match(JsonObject jsonObject) {
				if (!Util.typeMatch(PlayerStandsEventMessage.class, jsonObject)) {
					return false;
				}
				PlayerStandsEventMessage anEvent = Util.convert(PlayerStandsEventMessage.class, jsonObject);
				return anEvent.actingPlayer.equals(playerId.toString()) && anEvent.gameID.equals(gameID.toString());
			}
		});
	}

	@Override
	public void playerHits(Integer playerId, Integer tableId) {
		String command = new Gson().toJson(new GameCommandMessage(playerId.toString(), gameID.toString(), "HIT"));
		cometDClient.publish("/command/game", command);
	}

	@Override
	public void thenPlayerWon(final Integer playerId, final Integer tableId) {
		cometDClient.verifyMessageArrived(tableChannel(tableId), new MessageMatcher() {

			@Override
			public boolean match(JsonObject jsonObject) {
				if (!Util.typeMatch(GameFinishedEventMessage.class, jsonObject)) {
					return false;
				}
				GameFinishedEventMessage anEvent = Util.convert(GameFinishedEventMessage.class, jsonObject);
				return anEvent.winner.equals(playerId.toString()) && anEvent.gameID.equals(gameID.toString()) && anEvent.tableID.equals(tableId.toString());
			}
		});
	}

	@Override
	public void thenLeaderboardIsUpdated(final List<LeaderboardDO> leaderboard) {
		cometDClient.verifyMessageArrived(leaderboardChannel(), new MessageMatcher() {

			@Override
			public boolean match(JsonObject jsonObject) {
				if (!Util.typeMatch(LeaderBoardChangedEventMessage.class, jsonObject)) {
					return false;
				}
				LeaderBoardChangedEventMessage anEvent = Util.convert(LeaderBoardChangedEventMessage.class, jsonObject);
				return Util.dataMatch(leaderboard, anEvent);
			}
		});
	}

	@Override
	public void playerRegisters(String name) {
		// String command = new Gson().toJson(new RegistrationCommand(new
		// PlayerName(name)));
		// String response =
		// cometDClient.requestReply("/service/command/registration",command);
		String generatedId = restClient.register(name);
		playerIdNameMap.put(name, PlayerID.createFrom(generatedId));
		cometDClient.subscribeToChannel(playerQueryResponseChannel(generatedId));
		cometDClient.subscribeToChannel(leaderboardChannel());
	}

	@Override
	public void playerStands(Integer playerId, Integer tableId) {
		String command = new Gson().toJson(new GameCommandMessage(playerId.toString(), gameID.toString(), "STAND"));
		cometDClient.publish("/command/game", command);
	}

	private static String command(Integer playerId, Integer tableId) {
		return new Gson().toJson(new SeatingCommandMessage(playerId.toString(), tableId.toString()));
	}

	private static String tableChannel(Integer tableId) {
		return "/table/" + convertTableId(tableId);
	}

	private static String playerQueryResponseChannel(String playerId) {
		return "/player/" + playerId + "/query/response";
	}

	private static String leaderboardChannel() {
		return "/leaderboard";
	}

	private static String tablePlayerChannel(Integer playerId, Integer tableId) {
		return "/table/" + tableId + "/player/" + playerId;
	}

}
