package org.home.blackjack.core.infrastructure.persistence.game.store.hazelcast;

import static org.junit.Assert.fail;

import javax.annotation.Resource;

import org.home.blackjack.core.infrastructure.persistence.game.store.GameStore;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.hazelcast.core.HazelcastInstance;

@Ignore
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

	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
