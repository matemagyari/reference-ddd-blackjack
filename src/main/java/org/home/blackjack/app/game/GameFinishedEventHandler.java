package org.home.blackjack.app.game;

import javax.inject.Inject;
import javax.inject.Named;

import org.home.blackjack.app.player.PlayerRecordUpdaterApplicationService;
import org.home.blackjack.domain.common.events.EventSubscriber;
import org.home.blackjack.domain.game.event.GameFinishedEvent;
import org.home.blackjack.util.ddd.pattern.DomainEvent;

@Named
public class GameFinishedEventHandler implements EventSubscriber<GameFinishedEvent> {
    
    @Inject
    private PlayerRecordUpdaterApplicationService playerRecordUpdaterApplicationService;

    public boolean subscribedTo(DomainEvent event) {
        return event instanceof GameFinishedEvent;
    }

    public void handleEvent(GameFinishedEvent event) {
        playerRecordUpdaterApplicationService.playerWon(event.getWinner());
    }

}
