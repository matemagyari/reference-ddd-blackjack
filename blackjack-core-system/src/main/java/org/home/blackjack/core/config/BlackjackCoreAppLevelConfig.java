package org.home.blackjack.core.config;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.home.blackjack.core.domain.cashier.WalletService;
import org.home.blackjack.core.domain.game.DeckFactory;
import org.home.blackjack.core.infrastructure.JUGIDGenerationStrategy;
import org.home.blackjack.core.infrastructure.persistence.game.repository.SerializingGameRepository;
import org.home.blackjack.core.infrastructure.persistence.game.store.GameStore;
import org.home.blackjack.core.infrastructure.persistence.player.repository.SerializingPlayerRepository;
import org.home.blackjack.core.infrastructure.persistence.player.store.PlayerStore;
import org.home.blackjack.core.infrastructure.persistence.table.repository.SerializingTableRepository;
import org.home.blackjack.core.infrastructure.persistence.table.store.TableStore;
import org.home.blackjack.core.infrastructure.wallet.RestBasedWalletService;
import org.home.blackjack.util.ddd.pattern.app.event.EventBusManager;
import org.home.blackjack.util.ddd.pattern.domain.idgeneration.IDGenerator;
import org.home.blackjack.util.ddd.pattern.infrastructure.event.LightweightDomainEventBus;
import org.home.blackjack.util.locking.aspect.PessimisticLockingAspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

@Configuration
@ImportResource("classpath:META-INF/applicationContext-blackjack-core-app-scan.xml")
@EnableAspectJAutoProxy
public class BlackjackCoreAppLevelConfig {

    @Value("${blackjack.persistence.type}")
    private String type;

    @Inject
    private ApplicationContext applicationContext;

    @Bean(name = "lockAspect")
    public PessimisticLockingAspect lockAspect() {
        return new PessimisticLockingAspect();
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() throws IOException {
        PropertySourcesPlaceholderConfigurer props = new PropertySourcesPlaceholderConfigurer();
        props.setLocations(new Resource[] {new ClassPathResource("blackjack.properties")});
//        props.(PropertyPlaceholderConfigurer.SYSTEM_PROPERTIES_MODE_OVERRIDE);
        props.setIgnoreUnresolvablePlaceholders(true);
        return props;
    }

    @Bean
    public GameStore gameStore() {
        Map<String, String> beanMap = new HashMap<String, String>();
        beanMap.put("memory","inMemoryGameStore");
        beanMap.put("hazelcast","HZGameStore");
        beanMap.put("mongo","mongoGameStore");
        return (GameStore) applicationContext.getBean(beanMap.get(type));
    }

    @Bean
    public PlayerStore playerStore() {
        Map<String, String> beanMap = new HashMap<String, String>();
        beanMap.put("memory","inMemoryPlayerStore");
        beanMap.put("hazelcast","HZPlayerStore");
        beanMap.put("mongo","mongoPlayerStore");
        return (PlayerStore) applicationContext.getBean(beanMap.get(type));
    }

    @Bean
    public TableStore tableStore() {
        Map<String, String> beanMap = new HashMap<String, String>();
        beanMap.put("memory","inMemoryTableStore");
        beanMap.put("hazelcast","HZTableStore");
        beanMap.put("mongo","mongoTableStore");
        return (TableStore) applicationContext.getBean(beanMap.get(type));
    }
    
    @Bean
    public IDGenerator idGenerator() {
        return new JUGIDGenerationStrategy();
    }

    @Bean
    public SerializingGameRepository serializingGameRepository() {
        return new SerializingGameRepository(gameStore());
    }

    @Bean
    public SerializingPlayerRepository serializingPlayerRepository() {
        return new SerializingPlayerRepository(playerStore());
    }

    @Bean
    public SerializingTableRepository serializingTableRepository() {
        return new SerializingTableRepository(tableStore());
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

    @Bean
    public DeckFactory deckFactory(){
        return new DeckFactory();
    }

    @Bean
    public WalletService walletService(){
        return new RestBasedWalletService();
    }
}
