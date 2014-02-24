package org.home.blackjack.app.eventhandler;

import javax.inject.Inject;
import javax.inject.Named;

import org.home.blackjack.app.event.ExternalEventPublisher;
import org.home.blackjack.domain.common.events.EventSubscriber;
import org.home.blackjack.domain.game.event.GameFinishedEvent;
import org.home.blackjack.domain.player.Player;
import org.home.blackjack.domain.player.PlayerRepository;
import org.home.blackjack.domain.player.event.PlayerWonEvent;
import org.home.blackjack.util.ddd.pattern.DomainEvent;

@Named
public class GameFinishedEventHandler implements EventSubscriber<GameFinishedEvent> {
    
    @Inject
    private PlayerRepository playerRepository;
    @Inject
    private ExternalEventPublisher externalEventPublisher;

    public boolean subscribedTo(DomainEvent event) {
        return event instanceof GameFinishedEvent;
    }

    public void handleEvent(GameFinishedEvent event) {
        Player player = playerRepository.find(event.getWinner());
        player.recordWin();
        playerRepository.update(player);
        
        externalEventPublisher.publish(new PlayerWonEvent(event.getWinner()));
    }

}
