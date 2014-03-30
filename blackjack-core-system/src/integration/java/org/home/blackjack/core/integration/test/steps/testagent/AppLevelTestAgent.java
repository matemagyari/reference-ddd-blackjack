package org.home.blackjack.core.integration.test.steps.testagent;

import java.util.List;

import org.home.blackjack.core.app.events.event.EventBusManager;
import org.home.blackjack.core.app.events.eventhandler.PublicPlayerCardDealtEvent;
import org.home.blackjack.core.app.service.game.GameActionApplicationService;
import org.home.blackjack.core.app.service.game.GameActionType;
import org.home.blackjack.core.app.service.game.GameCommand;
import org.home.blackjack.core.app.service.query.QueryingApplicationService;
import org.home.blackjack.core.app.service.query.TablesDTO;
import org.home.blackjack.core.app.service.query.TablesQuery;
import org.home.blackjack.core.app.service.registration.RegistrationApplicationService;
import org.home.blackjack.core.app.service.registration.RegistrationCommand;
import org.home.blackjack.core.app.service.seating.SeatingApplicationService;
import org.home.blackjack.core.app.service.seating.SeatingCommand;
import org.home.blackjack.core.domain.game.core.GameID;
import org.home.blackjack.core.domain.game.event.GameFinishedEvent;
import org.home.blackjack.core.domain.game.event.PlayerCardDealtEvent;
import org.home.blackjack.core.domain.game.event.PlayerStandsEvent;
import org.home.blackjack.core.domain.player.Player;
import org.home.blackjack.core.domain.player.PlayerName;
import org.home.blackjack.core.domain.player.event.LeaderBoardChangedEvent;
import org.home.blackjack.core.domain.shared.PlayerID;
import org.home.blackjack.core.integration.test.dto.CardDO;
import org.home.blackjack.core.integration.test.dto.LeaderboardDO;
import org.home.blackjack.core.integration.test.dto.PlayerDO;
import org.home.blackjack.core.integration.test.dto.TableDO;
import org.home.blackjack.core.integration.test.fakes.FakeExternalEventPublisher;
import org.home.blackjack.core.integration.test.fakes.FakeExternalEventPublisher.DomainEventMatcher;
import org.home.blackjack.core.integration.test.util.AppLevelCucumberService;
import org.home.blackjack.core.integration.test.util.CucumberService;
import org.home.blackjack.core.integration.test.util.Util;

public class AppLevelTestAgent extends TestAgent {

	private CucumberService cucumberService;
	private SeatingApplicationService seatingApplicationService;
	private GameActionApplicationService gameActionApplicationService;
	private RegistrationApplicationService registrationApplicationService;
	private QueryingApplicationService queryingApplicationService;
	private FakeExternalEventPublisher fakeExternalEventPublisher;
	private EventBusManager eventBusManager;
	private GameID gameID;
	
	@Override
	public void reset() {
		super.reset();
		fakeExternalEventPublisher.reset();
	}

	@Override
	protected void initDependencies() {
		cucumberService = new AppLevelCucumberService();
		super.initDependencies();
		seatingApplicationService = cucumberService().getBean(SeatingApplicationService.class);
		gameActionApplicationService = cucumberService().getBean(GameActionApplicationService.class);
		registrationApplicationService = cucumberService().getBean(RegistrationApplicationService.class);
		queryingApplicationService = cucumberService().getBean(QueryingApplicationService.class);
		
		fakeExternalEventPublisher = cucumberService().getBean(FakeExternalEventPublisher.class);
		eventBusManager = cucumberService().getBean(EventBusManager.class);

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
    }

	@Override
	public void thenTablesSeenInLobby(List<TableDO> tables) {

		PlayerID playerID = new PlayerID();
		queryingApplicationService.getTables(new TablesQuery(playerID.toString()));
		TablesDTO tablesDTO = Util.convert(tables, playerID);
		fakeExternalEventPublisher.assertArrived(tablesDTO);

	}

	@Override
	public void playerSitsToTable(Integer playerId, Integer tableId) {
	    eventBusManager.initialize();
		seatingApplicationService.seatPlayer(new SeatingCommand(playerId.toString(), tableId.toString()));
		eventBusManager.flush();

	}

	@Override
	public void thenGameStartedAtTable(Integer tableID) {
		gameID = fakeExternalEventPublisher.assertInitalCardsDealtEvent(convertTableId(tableID));

	}

	@Override
	public void thenPlayerBeenDealt(final Integer playerId, Integer tableId, final String card) {
		fakeExternalEventPublisher.assertArrived(PlayerCardDealtEvent.class, new DomainEventMatcher<PlayerCardDealtEvent>() {
			@Override
			public boolean match(PlayerCardDealtEvent anEvent) {
				return anEvent.getCard().equals(CardDO.toCard(card)) 
						&& anEvent.getActingPlayer().equals(convertPlayerId(playerId))
						&& anEvent.getGameID().equals(gameID);
			}
		});
		fakeExternalEventPublisher.assertArrived(PublicPlayerCardDealtEvent.class, new DomainEventMatcher<PublicPlayerCardDealtEvent>() {
			@Override
			public boolean match(PublicPlayerCardDealtEvent anEvent) {
				return anEvent.getActingPlayer().equals(convertPlayerId(playerId))
						&& anEvent.getGameID().equals(gameID);
			}
		});		
	}
	
	@Override
	public void thenPlayersLastActionWasStand(final Integer playerId, Integer tableId) {
		fakeExternalEventPublisher.assertArrived(PlayerStandsEvent.class, new DomainEventMatcher<PlayerStandsEvent>() {
			@Override
			public boolean match(PlayerStandsEvent anEvent) {
				return anEvent.getActingPlayer().equals(convertPlayerId(playerId))
						&& anEvent.getGameID().equals(gameID);
			}
		});	
	}

	@Override
	public void playerHits(Integer playerId, Integer tableId) {
	    eventBusManager.initialize();
	    GameCommand command = new GameCommand(playerId.toString(), gameID.toString(), GameActionType.HIT.name());
		gameActionApplicationService.handlePlayerAction(command);
		eventBusManager.flush();
	}

	@Override
	public void playerStands(Integer playerId, Integer tableId) {
	    eventBusManager.initialize();
	    GameCommand command = new GameCommand(playerId.toString(), gameID.toString(), GameActionType.STAND.name());
		gameActionApplicationService.handlePlayerAction(command);
		eventBusManager.flush();
	}

	@Override
	public void thenPlayerWon(final Integer playerId, final Integer tableId) {
		fakeExternalEventPublisher.assertArrived(GameFinishedEvent.class, new DomainEventMatcher<GameFinishedEvent>() {
			@Override
			public boolean match(GameFinishedEvent anEvent) {
				return anEvent.winner().equals(convertPlayerId(playerId))
						&& anEvent.getGameID().equals(gameID)
						&& anEvent.getTableID().equals(convertTableId(tableId));
			}
		});
	}

	@Override
	public void playerRegisters(String name) {
		PlayerID playerID = registrationApplicationService.playerJoins(new RegistrationCommand(name));
		playerIdNameMap.put(name, playerID);
	}

	@Override
	public void thenLeaderboardIsUpdated(final List<LeaderboardDO> leaderboard) {
		fakeExternalEventPublisher.assertArrived(LeaderBoardChangedEvent.class, new DomainEventMatcher<LeaderBoardChangedEvent>() {
			@Override
			public boolean match(LeaderBoardChangedEvent anEvent) {
				return Util.dataMatch(leaderboard, anEvent);
			}
		});
	}
}
