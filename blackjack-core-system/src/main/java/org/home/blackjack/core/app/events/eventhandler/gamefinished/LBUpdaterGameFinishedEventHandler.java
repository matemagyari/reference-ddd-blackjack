package org.home.blackjack.core.app.events.eventhandler.gamefinished;

import javax.annotation.Resource;
import javax.inject.Named;

import org.home.blackjack.core.app.events.external.ExternalDomainEvent;
import org.home.blackjack.core.app.events.external.ExternalEventPublisher;
import org.home.blackjack.core.domain.game.event.GameFinishedEvent;
import org.home.blackjack.core.domain.player.LeaderboardUpdater;
import org.home.blackjack.core.domain.player.event.LeaderBoardChangedEvent;

@Named
public class LBUpdaterGameFinishedEventHandler extends GameFinishedEventHandler {
    
    @Resource
    private LeaderboardUpdater leaderboardUpdater;
    @Resource
    private ExternalEventPublisher externalEventPublisher;

    @Override
    public void handleEvent(GameFinishedEvent event) {
        LeaderBoardChangedEvent leaderBoardChangedEvent = leaderboardUpdater.updateAndReport(event.winner());
        externalEventPublisher.publish(new ExternalDomainEvent(leaderBoardChangedEvent));
    }

}
