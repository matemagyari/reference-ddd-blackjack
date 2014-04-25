package org.home.blackjack.core.infrastructure.adapters.driving.persistence.game.store.serializing;

import java.lang.reflect.Type;

import org.home.blackjack.core.domain.game.Game;
import org.home.blackjack.core.domain.game.core.Card;
import org.home.blackjack.core.domain.game.core.Card.Rank;
import org.home.blackjack.core.domain.game.core.Card.Suite;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class GameDeserializer implements JsonDeserializer<Game> {

	@Override
	public Game deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		JsonObject jsonObject = json.getAsJsonObject();

		// example: if in older versions the 'actionCounter' field was called
		// 'counter'
		if (jsonObject.get("actionCounter") == null) {
			jsonObject.add("actionCounter", jsonObject.get("counter"));
		}

		// example: if in an older version the players had the cards in
		// List<String> instead of List<Card>

		updateCardsInNeeded((JsonObject) jsonObject.get("dealer"));
		updateCardsInNeeded((JsonObject) jsonObject.get("player"));
		updateCardsInNeeded((JsonObject) jsonObject.get("lastToAct"));
		return new Gson().fromJson(jsonObject, Game.class);
	}

	private void updateCardsInNeeded(JsonObject player) {
		if (player == null) {
			return;
		}
		JsonArray cards = (JsonArray) player.get("cards");
		if (cards.size() > 0 && !cards.get(0).isJsonObject()) {

			JsonArray updatedCards = new JsonArray();
			for (int i = 0; i < cards.size(); i++) {
				String[] suiteAndRank = cards.get(0).getAsString().split("-");
				Card updatedCard = new Card(Suite.valueOf(suiteAndRank[0]), Rank.valueOf(suiteAndRank[1]));
				JsonElement updatedCardJson = new Gson().toJsonTree(updatedCard);
				updatedCards.add(updatedCardJson);
			}

			player.add("cards", updatedCards);

		}
	}

}
