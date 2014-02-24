package org.home.blackjack.app.table;

import javax.inject.Named;

import org.home.blackjack.domain.common.events.EventSubscriber;
import org.home.blackjack.domain.table.event.TableIsFullEvent;
import org.home.blackjack.util.ddd.pattern.DomainEvent;

@Named
public class TableIsFullEventHandler implements EventSubscriber<TableIsFullEvent> {
    

    public boolean subscribedTo(DomainEvent event) {
        return event instanceof TableIsFullEvent;
    }

    public void handleEvent(TableIsFullEvent event) {
        /*
        Game game = event.getGame();
        gameRepository.create(game);
        game.dealInitialCards();
        //no need for transaction here
        gameRepository.update(game);
        */
    }

}
