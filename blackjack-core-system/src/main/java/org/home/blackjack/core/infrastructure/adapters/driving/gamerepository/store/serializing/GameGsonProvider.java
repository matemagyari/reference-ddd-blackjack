package org.home.blackjack.core.infrastructure.adapters.driving.gamerepository.store.serializing;

import org.home.blackjack.core.domain.game.Game;
import org.home.blackjack.core.infrastructure.adapters.util.persistence.gson.GsonProvider;

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
