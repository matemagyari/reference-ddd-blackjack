package org.home.blackjack.core.config;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.home.blackjack.core.domain.game.GameRepository;
import org.home.blackjack.core.infrastructure.persistence.game.SerializingGameRepository;
import org.home.blackjack.util.SwitchableBeanFactory;
import org.home.blackjack.util.ddd.pattern.events.EventBusManager;
import org.home.blackjack.util.ddd.pattern.events.LightweightDomainEventBus;
import org.home.blackjack.util.locking.aspect.PessimisticLockingAspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

@Configuration
@ComponentScan({"org.home.blackjack.core.domain", "org.home.blackjack.core.app", "org.home.blackjack.core.infrastructure.persistence"})
@EnableAspectJAutoProxy
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
    public SwitchableBeanFactory gameStore(@Value("${blackjack.persistence.type}") String type) {
        SwitchableBeanFactory switchableBeanFactory = new SwitchableBeanFactory();
        switchableBeanFactory.setUseBean(type);
        Map<String, String> gameStores = new HashMap<String, String>();
        gameStores.put("memory","inMemoryGameStore");
        gameStores.put("hazelcast","hazelcastGameStore");
        gameStores.put("mongo","mongoGameStore");
        switchableBeanFactory.setMappings(gameStores);
        return switchableBeanFactory;
    }
    
    @Bean(name = "gameRepository")
    public GameRepository gameRepository() {
        return new SerializingGameRepository(gameStore);
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
