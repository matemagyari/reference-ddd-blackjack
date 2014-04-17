package org.home.blackjack.core.infrastructure.persistence.game.store.hazelcast;

import static org.junit.Assert.assertEquals;

import javax.annotation.Resource;

import org.home.blackjack.core.TestFixture;
import org.home.blackjack.core.domain.game.Game;
import org.home.blackjack.core.domain.game.core.GameID;
import org.home.blackjack.core.infrastructure.persistence.game.store.GameStore;
import org.home.blackjack.core.infrastructure.persistence.shared.json.JsonPersistenceAssembler;
import org.home.blackjack.core.infrastructure.persistence.shared.json.JsonPersistenceObject;
import org.home.blackjack.util.ddd.pattern.infrastructure.persistence.PersistenceObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.hazelcast.core.HazelcastInstance;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
public class HZGameStoreITest {

	
	@Configuration
	@ImportResource("classpath:META-INF/applicationContext-blackjack-core-hazelcast.xml")
	static class ContextConfiguration {

		@Resource
		private HazelcastInstance hzInstance;
		// this bean will be injected into the OrderServiceTest class
		@Bean
		public GameStore gameStore() {
			return new HZGameStore(hzInstance);
		}
	}
	
	@Resource
    private HZGameStore store;

    @Test
    public void lifeCycle() {
        Game aGame = TestFixture.aGame();
        aGame.dealInitialCards();

        JsonPersistenceAssembler<Game> assembler = store.assembler();
        JsonPersistenceObject<Game> persistenceGame = assembler.toPersistence(aGame);
        store.create(persistenceGame);

        JsonPersistenceObject<Game> retrievedPGame = store.find((PersistenceObjectId<GameID>) persistenceGame.id());

        Game retrievedGame = assembler.toDomain(retrievedPGame);

        assertEquals(aGame.getID(), retrievedGame.getID());
    }

}
