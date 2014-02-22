package org.home.blackjack.infrastructure.persistence.game.json;

import org.home.blackjack.domain.game.Game;
import org.home.blackjack.infrastructure.persistence.shared.GsonProvider;

public class GameGsonProvider extends GsonProvider {

	public GameGsonProvider() {
		gsonBuilder.registerTypeAdapter(Game.class, new GameDeserializer());
	}

}
