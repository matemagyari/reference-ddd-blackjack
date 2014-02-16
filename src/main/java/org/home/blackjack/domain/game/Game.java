package org.home.blackjack.domain.game;

import org.home.blackjack.domain.shared.PlayerId;

public interface Game {

	void playerHits(PlayerId player);

	void playerStands(PlayerId player);

}