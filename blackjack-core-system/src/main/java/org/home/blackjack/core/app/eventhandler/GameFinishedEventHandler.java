package org.home.blackjack.core.app.eventhandler;

import javax.annotation.Resource;
import javax.inject.Named;

import org.home.blackjack.core.app.event.ExternalEventPublisher;
import org.home.blackjack.core.domain.game.event.GameFinishedEvent;
import org.home.blackjack.core.domain.player.Player;
import org.home.blackjack.core.domain.player.PlayerRepository;
import org.home.blackjack.core.domain.player.event.PlayerWonEvent;
import org.home.blackjack.util.ddd.pattern.events.DomainEvent;
import org.home.blackjack.util.ddd.pattern.events.DomainEventSubscriber;

@Named
public class GameFinishedEventHandler implements DomainEventSubscriber<GameFinishedEvent> {
    
    @Resource
    private PlayerRepository playerRepository;
    @Resource
    private ExternalEventPublisher externalEventPublisher;

    @Override
    public boolean subscribedTo(DomainEvent event) {
        return event instanceof GameFinishedEvent;
    }

    @Override
    public void handleEvent(GameFinishedEvent event) {
        Player player = playerRepository.find(event.winner());
        player.recordWin();
        playerRepository.update(player);
        
        externalEventPublisher.publish(new PlayerWonEvent(event.winner()));
    }

}
