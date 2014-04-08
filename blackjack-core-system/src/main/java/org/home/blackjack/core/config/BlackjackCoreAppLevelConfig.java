package org.home.blackjack.core.config;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.home.blackjack.core.domain.game.GameRepository;
import org.home.blackjack.core.infrastructure.persistence.game.SerializingGameRepository;
import org.home.blackjack.core.infrastructure.persistence.game.store.GameStore;
import org.home.blackjack.core.infrastructure.persistence.player.SerializingPlayerRepository;
import org.home.blackjack.core.infrastructure.persistence.player.store.PlayerStore;
import org.home.blackjack.core.infrastructure.persistence.table.SerializingTableRepository;
import org.home.blackjack.core.infrastructure.persistence.table.store.TableStore;
import org.home.blackjack.util.SwitchableBeanFactory;
import org.home.blackjack.util.ddd.pattern.events.EventBusManager;
import org.home.blackjack.util.ddd.pattern.events.LightweightDomainEventBus;
import org.home.blackjack.util.locking.aspect.PessimisticLockingAspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

@Configuration
@ComponentScan({"org.home.blackjack.core.domain", "org.home.blackjack.core.app", "org.home.blackjack.core.infrastructure.persistence"})
@EnableAspectJAutoProxy
@Import(BlackjackCoreMongoConfig.class)
public class BlackjackCoreAppLevelConfig {

     @Bean(name = "lockAspect")
    public PessimisticLockingAspect lockAspect() {
        return new PessimisticLockingAspect();
    }

    @Bean
    public PropertyPlaceholderConfigurer propertyConfigurer() throws IOException {
        PropertyPlaceholderConfigurer props = new PropertyPlaceholderConfigurer();
        props.setLocations(new Resource[] {new ClassPathResource("blackjack.properties")});
        props.setSystemPropertiesMode(PropertyPlaceholderConfigurer.SYSTEM_PROPERTIES_MODE_OVERRIDE);
        props.setIgnoreUnresolvablePlaceholders(true);
        return props;
    }

    @Bean(name = "gameStore")
    public SwitchableBeanFactory<GameStore> gameStore(@Value("${blackjack.persistence.type}") String type) {
        SwitchableBeanFactory<GameStore> switchableBeanFactory = new SwitchableBeanFactory<GameStore>();
        switchableBeanFactory.setUseBean(type);
        Map<String, String> gameStores = new HashMap<String, String>();
        gameStores.put("memory","inMemoryGameStore");
        gameStores.put("hazelcast","hazelcastGameStore");
        gameStores.put("mongo","mongoGameStore");
        switchableBeanFactory.setMappings(gameStores);
        return switchableBeanFactory;
    }

    @Bean(name = "playerStore")
    public SwitchableBeanFactory<PlayerStore> playerStore(@Value("${blackjack.persistence.type}") String type) {
        SwitchableBeanFactory<PlayerStore> switchableBeanFactory = new SwitchableBeanFactory<PlayerStore>();
        switchableBeanFactory.setUseBean(type);
        Map<String, String> playerStores = new HashMap<String, String>();
        playerStores.put("memory","inMemoryPlayerStore");
        playerStores.put("hazelcast","hazelcastPlayerStore");
        playerStores.put("mongo","mongoPlayerStore");
        switchableBeanFactory.setMappings(playerStores);
        return switchableBeanFactory;
    }

    @Bean(name = "tableStore")
    public SwitchableBeanFactory<TableStore> tableStore(@Value("${blackjack.persistence.type}") String type) {
        SwitchableBeanFactory<TableStore> switchableBeanFactory = new SwitchableBeanFactory<TableStore>();
        switchableBeanFactory.setUseBean(type);
        Map<String, String> tableStores = new HashMap<String, String>();
        tableStores.put("memory","inMemoryTableStore");
        tableStores.put("hazelcast","hazelcastTableStore");
        tableStores.put("mongo","mongoTableStore");
        switchableBeanFactory.setMappings(tableStores);
        return switchableBeanFactory;
    }

    @Bean
    public SerializingGameRepository serializingGameRepository(@Qualifier("gameStore") SwitchableBeanFactory<GameStore> gameStore) {
        return new SerializingGameRepository(gameStore.getBean());
    }

    @Bean
    public SerializingPlayerRepository serializingPlayerRepository(@Qualifier("playerStore") SwitchableBeanFactory<PlayerStore> playerStore) {
        return new SerializingPlayerRepository(playerStore.getBean());
    }

    @Bean
    public SerializingTableRepository serializingTableRepository(@Qualifier("tableStore") SwitchableBeanFactory<TableStore> tableStore) {
        return new SerializingTableRepository(tableStore.getBean());
    }

    @Bean
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public LightweightDomainEventBus lightweightDomainEventBus() {
        return new LightweightDomainEventBus();
    }

    @Bean
    public EventBusManager eventBusManager() {
        return new EventBusManager();
    }
}
