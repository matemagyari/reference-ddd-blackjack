package org.home.blackjack.integration.test.steps.testagent;

import java.util.List;

import org.home.blackjack.core.app.client.game.GameAction;
import org.home.blackjack.core.app.client.game.GameActionApplicationService;
import org.home.blackjack.core.app.client.game.GameActionType;
import org.home.blackjack.core.app.client.seating.SeatingApplicationService;
import org.home.blackjack.core.app.client.seating.SeatingApplicationServiceImpl;
import org.home.blackjack.core.domain.game.core.GameID;
import org.home.blackjack.core.domain.game.event.GameFinishedEvent;
import org.home.blackjack.core.domain.game.event.PlayerCardDealtEvent;
import org.home.blackjack.core.domain.game.event.PlayerStandsEvent;
import org.home.blackjack.integration.test.dto.CardDO;
import org.home.blackjack.integration.test.dto.TableDO;
import org.home.blackjack.integration.test.fakes.FakeExternalEventPublisher;
import org.home.blackjack.integration.test.fakes.FakeExternalEventPublisher.DomainEventMatcher;
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
		seatingApplicationService.seatPlayer(getRealTableId(tableId), getRealPlayerId(playerId));

	}

	@Override
	public void thenGameStartedAtTable(Integer tableID) {
		gameID = fakeExternalEventPublisher.assertInitalCardsDealtEvent(getRealTableId(tableID));

	}

	@Override
	public void thenPlayerBeenDealt(final Integer playerId, Integer tableId, final String card) {
		fakeExternalEventPublisher.assertArrived(PlayerCardDealtEvent.class, new DomainEventMatcher<PlayerCardDealtEvent>() {
			@Override
			public boolean match(PlayerCardDealtEvent anEvent) {
				return anEvent.getCard().equals(CardDO.toCard(card)) 
						&& anEvent.getPlayer().equals(getRealPlayerId(playerId))
						&& anEvent.getGameID().equals(gameID);
			}
		});
	}
	
	@Override
	public void thenPlayersLastActionWasStand(final Integer playerId, Integer tableId) {
		fakeExternalEventPublisher.assertArrived(PlayerStandsEvent.class, new DomainEventMatcher<PlayerStandsEvent>() {
			@Override
			public boolean match(PlayerStandsEvent anEvent) {
				return anEvent.getPlayer().equals(getRealPlayerId(playerId))
						&& anEvent.getGameID().equals(gameID);
			}
		});	
	}

	@Override
	public void playerHits(Integer playerId, Integer tableId) {
		gameActionApplicationService.handlePlayerAction(gameID, new GameAction(gameID, getRealPlayerId(playerId), GameActionType.HIT));
	}

	@Override
	public void playerStands(Integer playerId, Integer tableId) {
		gameActionApplicationService.handlePlayerAction(gameID, new GameAction(gameID, getRealPlayerId(playerId), GameActionType.STAND));
	}

	@Override
	public void thenPlayerWon(final Integer playerId, final Integer tableId) {
		fakeExternalEventPublisher.assertArrived(GameFinishedEvent.class, new DomainEventMatcher<GameFinishedEvent>() {
			@Override
			public boolean match(GameFinishedEvent anEvent) {
				return anEvent.winner().equals(getRealPlayerId(playerId))
						&& anEvent.getGameID().equals(gameID)
						&& anEvent.tableID().equals(getRealTableId(tableId));
			}
		});
	}

}
