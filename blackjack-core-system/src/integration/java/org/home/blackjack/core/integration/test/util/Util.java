package org.home.blackjack.core.integration.test.util;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.home.blackjack.core.app.service.query.TableViewDTO;
import org.home.blackjack.core.app.service.query.TablesDTO;
import org.home.blackjack.core.domain.game.core.Card;
import org.home.blackjack.core.domain.player.core.PlayerName;
import org.home.blackjack.core.domain.player.event.LeaderBoardChangedEvent;
import org.home.blackjack.core.domain.player.event.LeaderBoardChangedEvent.LeaderBoardRecord;
import org.home.blackjack.core.domain.shared.PlayerID;
import org.home.blackjack.core.domain.shared.TableID;
import org.home.blackjack.core.integration.test.dto.CardDO;
import org.home.blackjack.core.integration.test.dto.LeaderboardDO;
import org.home.blackjack.core.integration.test.dto.TableDO;
import org.home.blackjack.util.ddd.pattern.events.DomainEvent;
import org.junit.Assert;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Util {

    public static List<Card> transform(List<CardDO> cards) {
        return Lists.transform(cards, new Function<CardDO, Card>() {
            public Card apply(CardDO card) {
                return card.card();
            }
        });
    }

    public static void sleep(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException ex) {
            new RuntimeException(ex);
        }
    }

    public static <T extends DomainEvent> void assertType(Class<T> clazz, JsonObject event) {
        Assert.assertEquals(clazz.getSimpleName(), event.get("type").toString());
    }

    public static <T> boolean typeMatch(T expectedDomainEvent, JsonObject event) {
        return expectedDomainEvent.getClass().getSimpleName().equals(event.get("type").toString().replace("\"", ""));
    }

    public static <T extends DomainEvent> boolean typeMatch(Class<T> clazz, JsonObject event) {
        return clazz.getSimpleName().equals(event.get("type").toString().replace("\"", ""));
    }

    public static <T extends DomainEvent> boolean equalsOld(T expectedDomainEvent, JsonObject event) {
        if (!typeMatch(expectedDomainEvent, event)) {
            return false;
        }
        DomainEvent fromJson = convert(expectedDomainEvent.getClass(), event);
        return expectedDomainEvent.equals(fromJson);
    }
    
    public static <T> boolean equals(T expectedEvent, JsonObject event) {
        if (!typeMatch(expectedEvent, event)) {
            return false;
        }
        T fromJson = convert((Class<T>)expectedEvent.getClass(), event);
        return expectedEvent.equals(fromJson);
    }

    public static <T> T convert(Class<T> clazz, JsonObject event) {
        JsonObject copiedJson = (JsonObject) new JsonParser().parse(event.toString());
        copiedJson.remove("type");
        return new Gson().fromJson(copiedJson, clazz);
    }

    public static List<PlayerID> splitToPlayerIds(String players) {
        List<PlayerID> result = Lists.newArrayList();
        if (StringUtils.isNotBlank(players)) {
            for (String id : players.trim().split(",")) {
                result.add(PlayerID.createFrom(id));
            }
        }
        return result;
    }
    
    public static TablesDTO convert(List<TableDO> tables, PlayerID playerID) {
        List<TableViewDTO> tableViews = Lists.newArrayList();
        for (TableDO tableDO : tables) {
            List<PlayerID> players = Util.splitToPlayerIds(tableDO.players);
            tableViews.add(new TableViewDTO(TableID.createFrom(tableDO.tableId), players));
        }
        return new TablesDTO(playerID, tableViews);
    }

	public static boolean dataMatch(List<LeaderboardDO> leaderboard, LeaderBoardChangedEvent anEvent) {
		List<LeaderBoardRecord> records = Lists.newArrayList();
        for (LeaderboardDO entry : leaderboard) {
            records.add(new LeaderBoardRecord(new PlayerName(entry.name), entry.winNumber));
        }
        
        LeaderBoardChangedEvent expectedEvent = new LeaderBoardChangedEvent(records);
		return expectedEvent.equals(anEvent);
	}

}
