package org.home.blackjack.infrastructure.persistence.game.store.json;

import org.home.blackjack.domain.game.Game;
import org.home.blackjack.infrastructure.persistence.shared.GsonProvider;

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
