package org.home.blackjack;

import java.util.concurrent.atomic.AtomicLong;

import org.home.blackjack.domain.shared.PlayerID;

public class TestFixture {
	
	private static AtomicLong idGenerator = new AtomicLong();

	public static PlayerID aPlayerId() {
		return new PlayerID();
	}

}
