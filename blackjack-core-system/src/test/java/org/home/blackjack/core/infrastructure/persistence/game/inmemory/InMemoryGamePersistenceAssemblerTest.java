package org.home.blackjack.core.infrastructure.persistence.game.inmemory;

import static org.junit.Assert.assertEquals;

import org.home.blackjack.core.domain.game.Game;
import org.home.blackjack.core.domain.game.GameFixture;
import org.home.blackjack.core.infrastructure.persistence.game.store.inmemory.InMemoryGamePersistenceAssembler;
import org.home.blackjack.core.infrastructure.persistence.game.store.inmemory.InMemoryPersistenceGame;
import org.junit.Test;

public class InMemoryGamePersistenceAssemblerTest {

	private InMemoryGamePersistenceAssembler testObj = new InMemoryGamePersistenceAssembler();

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
	
	private void assertIdenticalTransformBackAndForth(Game game) {
		
		InMemoryPersistenceGame persistenceGame = testObj.toPersistence(game);
		Game reinstantiatedGame = testObj.toDomain(persistenceGame);
		
		assertEquals(game, reinstantiatedGame);
		fieldsMatch(game, reinstantiatedGame);
	}
	
	private static boolean fieldsMatch(Game original, Game reinstantiated) {
		return original.toString().equals(reinstantiated.toString());
	}
}
