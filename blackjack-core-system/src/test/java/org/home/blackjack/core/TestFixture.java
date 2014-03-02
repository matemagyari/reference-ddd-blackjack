package org.home.blackjack.core;

import java.util.concurrent.atomic.AtomicLong;

import org.home.blackjack.core.domain.shared.PlayerID;

public class TestFixture {
	
	private static AtomicLong idGenerator = new AtomicLong();

	public static PlayerID aPlayerId() {
		return new PlayerID();
	}

}
