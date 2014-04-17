package org.home.blackjack.core.domain.game;

import java.util.List;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.inject.Named;

import org.home.blackjack.core.domain.game.core.GameID;
import org.home.blackjack.core.domain.shared.PlayerID;
import org.home.blackjack.core.domain.shared.TableID;
import org.home.blackjack.util.ddd.pattern.domain.events.DomainEventPublisherFactory;
import org.home.blackjack.util.ddd.pattern.domain.idgeneration.IDGenerator;

/**
 * Factory. Not a real member of the Domain, rather a technical necessity.
 */
@Named
public class GameFactory {

    @Resource
    private DeckFactory deckFactory;
    @Resource
    private DomainEventPublisherFactory domainEventPublisherFactory;
    @Inject
    private IDGenerator idGenerator;

    public Game createNewGame(TableID tableId, List<PlayerID> players) {
        return createNew2PlayerGame(tableId, players.get(0), players.get(1));
    }

    private Game createNew2PlayerGame(TableID tableId, PlayerID dealer, PlayerID player) {
        GameID gameId = GameID.createFrom(idGenerator.generate());
        return new Game(gameId , tableId, dealer, player, deckFactory, domainEventPublisherFactory.domainEventPublisherInstance());
    }

}
