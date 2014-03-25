package org.home.blackjack.core.domain.player;

import java.util.List;

import javax.annotation.Resource;
import javax.inject.Named;

import org.home.blackjack.core.domain.player.event.LeaderBoardChangedEvent;
import org.home.blackjack.core.domain.player.event.LeaderBoardChangedEvent.LeaderBoardRecord;
import org.home.blackjack.core.domain.shared.PlayerID;

import com.google.common.collect.Lists;

/**
 * Domain Service
 *
 */
@Named
public class LeaderboardUpdater {
    
    @Resource
    private PlayerRepository playerRepository;

    public LeaderBoardChangedEvent updateAndReport(PlayerID lastWinner) {
        Player player = playerRepository.find(lastWinner);
        player.recordWin();
        playerRepository.update(player);
        
        List<LeaderBoardRecord> records = Lists.newArrayList();
        List<Player> players = playerRepository.findAllSortedByWinNumber();
        for (Player aPlayer : players) {
            records.add(new LeaderBoardRecord(aPlayer.getName(), aPlayer.getWinNumber()));
        }
        
        return new LeaderBoardChangedEvent(records);
    }
    

}
