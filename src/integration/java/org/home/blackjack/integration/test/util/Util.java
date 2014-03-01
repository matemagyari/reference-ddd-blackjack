package org.home.blackjack.integration.test.util;

import java.util.List;

import org.home.blackjack.domain.game.core.Card;
import org.home.blackjack.integration.test.dto.CardDO;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

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

}
