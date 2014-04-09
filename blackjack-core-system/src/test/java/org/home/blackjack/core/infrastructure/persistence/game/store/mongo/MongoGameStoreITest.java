package org.home.blackjack.core.infrastructure.persistence.game.store.mongo;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import javax.annotation.Resource;

import org.home.blackjack.core.TestFixture;
import org.home.blackjack.core.domain.game.Game;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.MongodConfig;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;

@Ignore
@ContextConfiguration("classpath:META-INF/applicationContext-blackjack-core.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class MongoGameStoreITest {

	@Resource(name = "mongoGameStore")
	private MongoGameStore store;

	private static MongodProcess mongodProcess;
	private static MongodExecutable mongodExecutable;

	@BeforeClass
	public static void initializeDB() throws IOException {
	    
		/*
		IStreamProcessor stream = new NullProcessor();
        MongodStarter runtime = MongodStarter.getInstance(new RuntimeConfigBuilder()
            .defaults(Command.MongoD)
            .processOutput(new ProcessOutput(stream, stream, stream))
            .artifactStore(new ArtifactStoreBuilder()
                .defaults(Command.MongoD)
                .executableNaming(new UserTempNaming())
                .build())
            .build());
        mongodExecutable = runtime.prepare(new MongodConfigBuilder()
            .version(Version.Main.PRODUCTION)
            .net(new Net(MONGO_TEST_PORT, Network.localhostIsIPv6()))
            .build());
         */
		
        MongodStarter runtime = MongodStarter.getDefaultInstance();
        MongodConfig mongodConfig = new MongodConfig(Version.V2_4_1, 27017, Network.localhostIsIPv6());
        
        mongodExecutable = runtime.prepare(mongodConfig);
        mongodProcess = mongodExecutable.start();
	}

	@AfterClass
	public static void shutdownDB() throws InterruptedException {
		mongodProcess.stop();
		mongodExecutable.stop();
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
		
		assertEquals(aGame.getID(), retrievedGame.getID());
	}

}
