package org.home.blackjack.core.infrastructure.persistence.game.store.mongo;

import javax.annotation.Resource;

import org.home.blackjack.core.TestFixture;
import org.home.blackjack.core.domain.game.Game;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration("classpath:META-INF/applicationContext-blackjack-core.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class MongoGameStoreITest {

    @Resource(name="mongoGameStore")
    private MongoGameStore store;
    
    @BeforeClass
    public static void setupBeforeClass() {
        System.setProperty("blackjack.persistence.type", "mongo");
    }
    
    @Before
    public void setup() {
        store.clear();
    }
    
    @Test
    public void lifeCycle() {
        Game aGame = TestFixture.aGame();
        aGame.dealInitialCards();

        MongoGamePersistenceAssembler assembler = store.assembler();
        MongoPersistenceGame persistenceGame = assembler.toPersistence(aGame);
        store.create(persistenceGame);
        
        MongoPersistenceGame retrievedPGame = store.find(persistenceGame.id());
        
        Game retrievedGame = assembler.toDomain(retrievedPGame);
    }

}
