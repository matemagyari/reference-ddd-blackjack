package org.home.blackjack.core.app.events.eventhandler;

import javax.annotation.Resource;
import javax.inject.Named;

import org.home.blackjack.core.app.events.external.ExternalDomainEvent;
import org.home.blackjack.core.app.events.external.ExternalEventPublisher;
import org.home.blackjack.core.domain.game.event.GameFinishedEvent;
import org.home.blackjack.core.domain.player.LeaderboardUpdater;
import org.home.blackjack.core.domain.player.event.LeaderBoardChangedEvent;
import org.home.blackjack.util.ddd.pattern.events.DomainEvent;
import org.home.blackjack.util.ddd.pattern.events.DomainEventSubscriber;

@Named
public class GameFinishedEventHandler implements DomainEventSubscriber<GameFinishedEvent> {
    
    @Resource
    private LeaderboardUpdater leaderboardUpdater;
    @Resource
    private ExternalEventPublisher externalEventPublisher;

    @Override
    public boolean subscribedTo(DomainEvent event) {
        return event instanceof GameFinishedEvent;
    }

    @Override
    public void handleEvent(GameFinishedEvent event) {
        LeaderBoardChangedEvent leaderBoardChangedEvent = leaderboardUpdater.updateAndReport(event.winner());
        
        externalEventPublisher.publish(new ExternalDomainEvent(leaderBoardChangedEvent));
    }

}
