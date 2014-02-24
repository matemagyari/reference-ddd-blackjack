package org.home.blackjack.app.eventhandler;

import javax.inject.Inject;
import javax.inject.Named;

import org.home.blackjack.domain.common.events.EventSubscriber;
import org.home.blackjack.domain.game.Game;
import org.home.blackjack.domain.game.GameRepository;
import org.home.blackjack.domain.table.event.TableIsFullEvent;
import org.home.blackjack.util.ddd.pattern.DomainEvent;

@Named
public class TableIsFullEventHandler implements EventSubscriber<TableIsFullEvent> {
    
    @Inject
    private GameRepository gameRepository;

    public boolean subscribedTo(DomainEvent event) {
        return event instanceof TableIsFullEvent;
    }

    public void handleEvent(TableIsFullEvent event) {
        Game game = event.getGame();
        gameRepository.create(game);
        game.dealInitialCards();
        //no need for transaction here
        gameRepository.update(game);
    }

}
