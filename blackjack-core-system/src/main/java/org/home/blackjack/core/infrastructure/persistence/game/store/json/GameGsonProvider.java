package org.home.blackjack.core.infrastructure.persistence.game.store.json;

import org.home.blackjack.core.domain.game.Game;
import org.home.blackjack.core.infrastructure.persistence.shared.gson.GsonProvider;

/**
 * Infrastructure Service.
 * @author Mate
 *
 */
public class GameGsonProvider extends GsonProvider {

	public GameGsonProvider() {
		gsonBuilder.registerTypeAdapter(Game.class, new GameDeserializer());
	}

}
