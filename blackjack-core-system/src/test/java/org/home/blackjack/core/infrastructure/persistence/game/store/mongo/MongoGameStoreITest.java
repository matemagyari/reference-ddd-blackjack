package org.home.blackjack.core.infrastructure.persistence.game.store.mongo;

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

import com.mongodb.Mongo;

import de.flapdoodle.embed.mongo.MongodProcess;

@Ignore
@ContextConfiguration("classpath:META-INF/applicationContext-blackjack-core.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class MongoGameStoreITest {

	@Resource(name = "mongoGameStore")
	private MongoGameStore store;

	private static final String LOCALHOST = "127.0.0.1";
	private static final String DB_NAME = "game";
	private static final int MONGO_TEST_PORT = 27017;
	private static MongodProcess mongoProcess;
	private static Mongo mongo;

	@BeforeClass
	public static void initializeDB() throws IOException {
		/*
		System.setProperty("blackjack.persistence.type", "mongo");
		RuntimeConfig config = new RuntimeConfig();
		config.setExecutableNaming(new UserTempNaming());
		MongodStarter starter = MongodStarter.getInstance(config);
		MongodExecutable mongoExecutable = starter.prepare(new MongodConfig(Version.V2_2_0, MONGO_TEST_PORT, false));
		mongoProcess = mongoExecutable.start();
		mongo = new Mongo(LOCALHOST, MONGO_TEST_PORT);
		mongo.getDB(DB_NAME);
		*/
	}

	@AfterClass
	public static void shutdownDB() throws InterruptedException {
		mongo.close();
		mongoProcess.stop();
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
