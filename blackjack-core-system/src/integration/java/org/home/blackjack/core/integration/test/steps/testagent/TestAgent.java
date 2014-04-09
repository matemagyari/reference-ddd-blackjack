package org.home.blackjack.core.integration.test.steps.testagent;

import java.util.List;
import java.util.Map;

import org.home.blackjack.core.domain.player.Player;
import org.home.blackjack.core.domain.player.PlayerRepository;
import org.home.blackjack.core.domain.player.core.PlayerName;
import org.home.blackjack.core.domain.shared.PlayerID;
import org.home.blackjack.core.domain.shared.TableID;
import org.home.blackjack.core.domain.table.Table;
import org.home.blackjack.core.domain.table.TableRepository;
import org.home.blackjack.core.integration.test.dto.CardDO;
import org.home.blackjack.core.integration.test.dto.LeaderboardDO;
import org.home.blackjack.core.integration.test.dto.PlayerDO;
import org.home.blackjack.core.integration.test.dto.TableDO;
import org.home.blackjack.core.integration.test.fakes.FakeDeckFactory;
import org.home.blackjack.core.integration.test.fakes.FakeWalletService;
import org.home.blackjack.core.integration.test.util.CucumberService;
import org.home.blackjack.core.integration.test.util.Util;
import org.junit.Assert;

import com.google.common.collect.Maps;


public abstract class  TestAgent {
	
	protected FakeDeckFactory fakeDeckFactory;
	protected TableRepository tableRepository;
	protected PlayerRepository playerRepository;
	protected FakeWalletService fakeWalletService;
	
	protected Map<String, PlayerID> playerIdNameMap = Maps.newHashMap();
	
    public TestAgent() {
        initDependencies();
    }

    protected void initDependencies() {
    	fakeDeckFactory = cucumberService().getBean(FakeDeckFactory.class);
    	tableRepository = cucumberService().getBean(TableRepository.class);
    	playerRepository = cucumberService().getBean(PlayerRepository.class);
    	fakeWalletService = cucumberService().getBean(FakeWalletService.class);
    }
    
    protected abstract CucumberService cucumberService();
    
    public void reset() {
    	fakeDeckFactory.reset();
    	tableRepository.clear();
    	playerRepository.clear();
    	fakeWalletService.reset();
    	playerIdNameMap.clear();
    }
    
	public void givenAPreparedDeck(List<CardDO> cards) {
		fakeDeckFactory.prepareDeckFrom(Util.transform(cards));
		
	}

	public void givenAnEmptyTable(Integer tableID) {
		tableRepository.create(new Table(TableID.createFrom(tableID.toString())));
	}

	public abstract void thenTablesSeenInLobby(List<TableDO> tables);

	public abstract void playerSitsToTable(Integer playerId, Integer tableId);

	public abstract void thenGameStartedAtTable(Integer tableID);

	public abstract void thenPlayerBeenDealt(Integer playerId, Integer tableId, String card);

	public abstract void playerHits(Integer playerId, Integer tableId);

	public abstract void playerStands(Integer playerId, Integer tableId);

	public abstract void thenPlayerWon(Integer playerId, Integer tableId);

	public abstract void thenPlayersLastActionWasStand(Integer playerId, Integer tableId);
	
	public abstract void playerRegisters(String name);
	
	public abstract void thenLeaderboardIsUpdated(List<LeaderboardDO> leadeboard);

	public abstract void givenRegisteredPlayers(List<PlayerDO> players) ;

	public void thenPlayerIsCreated(String name) {
		Player player = playerRepository.find(playerIdNameMap.get(name));
		Assert.assertNotNull(player);
	}
	
	public void givenRegisteredPlayer(Integer playerId) {
		playerRepository.create(new Player(convertPlayerId(playerId), new PlayerName("xx")));
	}
	

	public void thenPlayerIsDebited(Integer playerId, Integer amount) {
		fakeWalletService.assertLastAct(convertPlayerId(playerId), FakeWalletService.WalletAct.ENTRYFEE);
	}

	public void thenPlayerIsCredited(Integer playerId, Integer amount) {
		fakeWalletService.assertLastAct(convertPlayerId(playerId), FakeWalletService.WalletAct.WIN);
	}
	
	public void thenPlayerHasANewAccount(String name) {
		fakeWalletService.assertLastAct(playerIdNameMap.get(name), FakeWalletService.WalletAct.OPEN_ACCOUNT);
	}

	protected static PlayerID convertPlayerId(Integer playerId) {
		return PlayerID.createFrom(playerId.toString());
	}

	protected static PlayerID convertPlayerId(String playerId) {
		return PlayerID.createFrom(playerId);
	}
	
	protected static TableID convertTableId(Integer tableId) {
		return TableID.createFrom(tableId.toString());
	}





	
}
