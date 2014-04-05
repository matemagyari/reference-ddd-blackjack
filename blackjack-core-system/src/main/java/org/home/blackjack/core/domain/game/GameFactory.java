package org.home.blackjack.core.domain.game;

import java.util.List;

import javax.annotation.Resource;
import javax.inject.Named;

import org.home.blackjack.core.domain.game.core.GameID;
import org.home.blackjack.core.domain.shared.PlayerID;
import org.home.blackjack.core.domain.shared.TableID;
import org.home.blackjack.util.ddd.pattern.events.DomainEventPublisherFactory;

/**
 * Factory. Not a real member of the Domain, rather a technical necessity.
 */
@Named
public class GameFactory {

	@Resource
	private DeckFactory deckFactory;
	@Resource
    private DomainEventPublisherFactory domainEventPublisherFactory;

	public Game createNewGame(TableID tableId, List<PlayerID> players) {
		return createNew2PlayerGame(tableId, players.get(0), players.get(1));
	}
	
	private Game createNew2PlayerGame(TableID tableId, PlayerID dealer, PlayerID player) {
		return new Game(new GameID(),tableId, dealer, player, deckFactory, domainEventPublisherFactory.domainEventPublisherInstance());
	}



}
