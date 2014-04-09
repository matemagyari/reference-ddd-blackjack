package org.home.blackjack.core.infrastructure.persistence.gson;

import static org.junit.Assert.assertEquals;

import org.home.blackjack.core.domain.game.Game;
import org.home.blackjack.core.domain.game.GameFixture;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class GsonTest {
	
    @Test
    public void stringToSjon() {
       String str = "{ \"name\" : \"John\"}"; 
       JsonElement element = new Gson().fromJson (str, JsonElement.class);
       JsonObject jsonObj = element.getAsJsonObject();
       assertEquals("John", jsonObj.get("name").getAsString());
    }
    
    @Test
	public void newGame() {
		Game game = GameFixture.aGame();
		assertIdenticalTransformBackAndForth(game);
	}

	@Test
	public void gameInProcess() {
		Game game = GameFixture.aGame();
		game.dealInitialCards();
		
		assertIdenticalTransformBackAndForth(game);
	}
	
	private static void assertIdenticalTransformBackAndForth(Game game) {
		
		
		String json = new Gson().toJson(game);
		System.err.println("\nGame before\n"+game);
		System.err.println("\nJson\n"+json);
		Game reinstantiatedGame = new Gson().fromJson(json, Game.class);
		System.err.println("\nGame after\n"+reinstantiatedGame);
		assertEquals(game, reinstantiatedGame);
	}

}
