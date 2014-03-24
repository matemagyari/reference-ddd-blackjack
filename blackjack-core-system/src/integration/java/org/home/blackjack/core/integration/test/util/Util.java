package org.home.blackjack.core.integration.test.util;

import java.util.List;

import org.home.blackjack.core.domain.game.core.Card;
import org.home.blackjack.core.domain.shared.PlayerID;
import org.home.blackjack.core.integration.test.dto.CardDO;
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
	
	public static <T extends DomainEvent> boolean typeMatch(T expectedDomainEvent, JsonObject event) {
		return expectedDomainEvent.getClass().getSimpleName().equals(event.get("type").toString().replace("\"", ""));
	}

	public static <T extends DomainEvent> boolean typeMatch(Class<T> clazz, JsonObject event) {
		return clazz.getSimpleName().equals(event.get("type").toString().replace("\"", ""));
	}

	public static <T extends DomainEvent> boolean equals(T expectedDomainEvent, JsonObject event) {
		if (!typeMatch(expectedDomainEvent, event)) {
			return false;
		}
		DomainEvent fromJson = convert(expectedDomainEvent.getClass(), event);
		return expectedDomainEvent.equals(fromJson);
	}

	public static <T extends DomainEvent> T convert(Class<T> clazz, JsonObject event) {
		JsonObject copiedJson = (JsonObject) new JsonParser().parse(event.toString());
		copiedJson.remove("type");
		return new Gson().fromJson(copiedJson, clazz);
	}

	public static List<PlayerID> splitToPlayerIds(String players) {
		List<PlayerID> result = Lists.newArrayList();
		for(String id : players.trim().split(",")) {
			result.add(PlayerID.createFrom(id));
		}
		return result;
	}


}
