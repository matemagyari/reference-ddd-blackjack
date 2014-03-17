package org.home.blackjack.core.infrastructure.persistence.game.json;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.home.blackjack.core.domain.game.Game;
import org.home.blackjack.core.domain.game.GameFixture;
import org.home.blackjack.core.infrastructure.persistence.game.store.json.GameGsonProvider;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

@Ignore
public class GameGsonProviderVersioningTest {

	private GameGsonProvider gsonProvider = new GameGsonProvider();
	private Game game;
	private JsonObject json;

	@Before
	public void setup() {
		game = GameFixture.aGame();
		game.dealInitialCards();
		json = (JsonObject) new Gson().toJsonTree(game);
	}

	@Test
	public void fieldNameChange() {

		changeFieldName(json);
		assertSuccessfulDeserialization();
	}

	@Test
	public void structureChange() {
		String oldJson = readFromFile("\\src\\test\\resources\\oldversiongame.json");
		try {
			new Gson().fromJson(oldJson, Game.class);
			fail();
		} catch (JsonSyntaxException ex) {

		}
		Game fromJson = gsonProvider.gson().fromJson(oldJson, Game.class);
	}

	// C:\Users\Mate\Documents\gitrepos\blackjack\infrastructure\persistence\game\json\oldversiongame.json
	private static String readFromFile(String relativeFileName) {
		try {
			String absolutFilePath = new File("").getAbsolutePath() + relativeFileName;
			return FileUtils.readFileToString(new File(absolutFilePath));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private void assertSuccessfulDeserialization() {
		Game fromJson = new Gson().fromJson(json, Game.class);
		assertFalse(fieldsMatch(game, fromJson));
		Game reinstantiatedGame = gsonProvider.gson().fromJson(json, Game.class);
		assertTrue(fieldsMatch(game, reinstantiatedGame));
		System.err.println(game);
		System.err.println(json);
	}

	private static boolean fieldsMatch(Game original, Game reinstantiated) {
		return original.toString().equals(reinstantiated.toString());
	}

	private void changeFieldName(JsonObject jsonObject) {
		// the original json contains 'actionCounter' but no 'counter' field
		assertNotNull(jsonObject.get("actionCounter"));
		assertNull(jsonObject.get("counter"));

		jsonObject.add("counter", jsonObject.get("actionCounter"));
		jsonObject.remove("actionCounter");

		// the manipulated json
		assertNotNull(jsonObject.get("counter"));
		assertNull(jsonObject.get("actionCounter"));
	}

}
