package org.home.blackjack.domain.game;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.home.blackjack.domain.game.core.GameID;
import org.home.blackjack.domain.shared.PlayerID;
import org.home.blackjack.domain.table.core.TableID;
import org.home.blackjack.util.ddd.pattern.EventPublisher;

/**
 * Factory. Not a real member of the Domain, rather a technical necessity.
 */
@Named
public class GameFactory {

	@Inject
	private EventPublisher eventPublisher;

	@Inject
	private DeckFactory deckFactory;

	public Game createNewGame(TableID tableId, List<PlayerID> players) {
		return createNew2PlayerGame(tableId, players.get(0), players.get(1));
	}
	
	private Game createNew2PlayerGame(TableID tableId, PlayerID dealer, PlayerID player) {
		return new Game(new GameID(),tableId, dealer, player, deckFactory, eventPublisher);
	}



}
