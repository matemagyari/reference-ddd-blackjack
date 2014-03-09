package org.home.blackjack.core.integration.test.fakes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.home.blackjack.core.domain.game.core.GameID;
import org.home.blackjack.core.domain.shared.PlayerID;
import org.home.blackjack.core.domain.wallet.WalletService;
import org.junit.Assert;

import com.google.common.collect.Maps;

public class FakeWalletService implements WalletService {
	
	private final Map<PlayerID, List<WalletAct>> walletActs = Maps.newHashMap();

	@Override
	public void giveTheWin(GameID gameID, PlayerID winner) {
		actList(winner).add(WalletAct.WIN);
	}

	@Override
	public void debitEntryFee(PlayerID playerID) {
		actList(playerID).add(WalletAct.ENTRYFEE);
	}

	public void reset() {
		walletActs.clear();
	}
	
	private List<WalletAct> actList(PlayerID player) {
		if (walletActs.get(player) == null) {
			walletActs.put(player, new ArrayList<WalletAct>());
		}
		return walletActs.get(player);
	}

	public static enum WalletAct {
		WIN, ENTRYFEE
	}

	public void assertLastAct(PlayerID playerId, WalletAct walletAct) {
		List<WalletAct> actList = actList(playerId);
		WalletAct walletAct2 = actList.get(actList.size()-1);
		Assert.assertEquals(walletAct, walletAct2);
	}
}
