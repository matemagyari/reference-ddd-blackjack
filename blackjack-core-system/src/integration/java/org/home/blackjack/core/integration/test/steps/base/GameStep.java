package org.home.blackjack.core.integration.test.steps.base;

import java.util.List;

import org.home.blackjack.core.integration.test.dto.CardDO;
import org.home.blackjack.core.integration.test.dto.LeaderboardDO;
import org.home.blackjack.core.integration.test.dto.PlayerDO;
import org.home.blackjack.core.integration.test.dto.TableDO;
import org.home.blackjack.core.integration.test.steps.testagent.AppLevelTestAgent;
import org.home.blackjack.core.integration.test.steps.testagent.MessagingTestAgent;
import org.home.blackjack.core.integration.test.steps.testagent.TestAgent;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class GameStep {

	public static String scope;
	public static final String messagingLevelScope = "messagingLevelScope";
	public static final String appLevelScope = "appLevelScope";
    private static final Integer BET_AMOUNT = 500;
    private static final Integer WIN_AMOUNT = 2 * BET_AMOUNT;

	private final TestAgent testAgent;

	public GameStep() {

		if (messagingLevelScope.equals(scope)) {
			testAgent = new MessagingTestAgent();
		} else if (appLevelScope.equals(scope)) {
			testAgent = new AppLevelTestAgent();
		} else {
			throw new RuntimeException("Hey I don't know which TestAgent to use");
		}
		testAgent.reset();
	}
	
	@Then("^player '(\\d+)' is debited with the bet amount$")
	public void thenPlayerIsDebited(Integer playerId) {
		testAgent.thenPlayerIsDebited(playerId, BET_AMOUNT);
	}

	@Then("^player '(\\d+)' is credited with the win$")
	public void thenPlayerIsCredited(Integer playerId) {
		testAgent.thenPlayerIsCredited(playerId, WIN_AMOUNT);
	}

	@Given("^player '(\\d+)' is registered$")
	public void givenRegisteredPlayer(Integer playerId) {
		testAgent.givenRegisteredPlayer(playerId);
	}
	
	@Given("^registered players$")
	public void givenRegisteredPlayers(List<PlayerDO> players) {
	    testAgent.givenRegisteredPlayers(players);
	}

	@Given("^a prepared deck with cards in order$")
	public void givenAPreparedDeck(List<CardDO> cards) {
		testAgent.givenAPreparedDeck(cards);
	}

	@Given("^there is an empty table with id '(\\d+)'$")
	public void givenAnEmptyTable(Integer tableID) {
		testAgent.givenAnEmptyTable(tableID);
	}

	@Then("^players can see tables in lobby$")
	public void thenTablesSeenInLobby(List<TableDO> tables) {
		testAgent.thenTablesSeenInLobby(tables);
	}
	
	@Then("^players can see player '(\\d+)' stands at table '(\\d+)'$")
	public void thenPlayersLastActionWasStand(Integer playerId, Integer tableId) {
		testAgent.thenPlayersLastActionWasStand(playerId, tableId);
	}

	@When("^player '(\\d+)' sits to table '(\\d+)'$")
	public void playerSitsToTable(Integer playerId, Integer tableId) {
		testAgent.playerSitsToTable(playerId, tableId);
	}

	@Then("^game started on table '(\\d+)'$")
	public void thenGameStartedAtTable(Integer tableID) {
		testAgent.thenGameStartedAtTable(tableID);
	}

	@Then("^player '(\\d+)' has been dealt '(\\w+)' at table '(\\d+)'$")
	public void thenPlayerBeenDealt(Integer playerId, String card, Integer tableId) {
		testAgent.thenPlayerBeenDealt(playerId, tableId, card);
	}

	@When("^player '(\\d+)' hits at table '(\\d+)'$")
	public void playerHits(Integer playerId, Integer tableId) {
		testAgent.playerHits(playerId, tableId);
	}

	@When("^player '(\\d+)' stands at table '(\\d+)'$")
	public void playerStands(Integer playerId, Integer tableId) {
		testAgent.playerStands(playerId, tableId);
	}

	@Then("^player '(\\d+)' won at table '(\\d+)'$")
	public void thenPlayerWon(Integer playerId, Integer tableId) {
		testAgent.thenPlayerWon(playerId, tableId);
	}
	
	@When("^player '(\\w+)' registers$")
	public void playerRegisters(String name)  {
		testAgent.playerRegisters(name);
	}

	@Then("^player '(\\w+)' is now a registered player$")
	public void thenPlayerIsCreated(String name)  {
		testAgent.thenPlayerIsCreated(name);
	}

	@Then("^player '(\\w+)' has a new account$")
	public void thenPlayerHasANewAccount(String name)  {
		testAgent.thenPlayerHasANewAccount(name);
	}
	
	//--------------------------------LEADER BOARD ---------------------------------------

	@When("^player '(\\d+)' wins a game$")
	public void playerWinsAGame(String player) {
		//testAgent.playerWinsAGame(player);
	}

	@Then("^leader board update is$")
	public void leader_board_update_is(List<LeaderboardDO> leadeboard) {
		testAgent.thenLeaderboardIsUpdated(leadeboard);
	}	


}
