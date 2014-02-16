package org.home.blackjack.domain.game;

import org.home.blackjack.domain.core.PlayerId;

public interface Game {

	void playerHits(PlayerId player);

	void playerStands(PlayerId player);

}