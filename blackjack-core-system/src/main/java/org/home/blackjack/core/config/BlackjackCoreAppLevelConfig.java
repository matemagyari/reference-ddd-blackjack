package org.home.blackjack.core.config;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.home.blackjack.core.app.events.external.ExternalEventPublisher;
import org.home.blackjack.core.domain.cashier.WalletService;
import org.home.blackjack.core.domain.game.DeckFactory;
import org.home.blackjack.core.infrastructure.integration.camel.CometDExternalEventPublisher;
import org.home.blackjack.core.infrastructure.persistence.game.SerializingGameRepository;
import org.home.blackjack.core.infrastructure.persistence.game.store.GameStore;
import org.home.blackjack.core.infrastructure.persistence.player.SerializingPlayerRepository;
import org.home.blackjack.core.infrastructure.persistence.player.store.PlayerStore;
import org.home.blackjack.core.infrastructure.persistence.table.SerializingTableRepository;
import org.home.blackjack.core.infrastructure.persistence.table.store.TableStore;
import org.home.blackjack.core.infrastructure.wallet.RestBasedWalletService;
import org.home.blackjack.util.SwitchableBeanFactory;
import org.home.blackjack.util.ddd.pattern.events.EventBusManager;
import org.home.blackjack.util.ddd.pattern.events.LightweightDomainEventBus;
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
//@Lazy
//@ComponentScan({"org.home.blackjack.core.domain", "org.home.blackjack.core.app", "org.home.blackjack.core.infrastructure.persistence"})
@ImportResource("classpath:META-INF/applicationContext-blackjack-core-scan.xml")
@EnableAspectJAutoProxy
public class BlackjackCoreAppLevelConfig {

    @Value("${blackjack.persistence.type}")
    String type;

    @Inject
    ApplicationContext applicationContext;

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
        SwitchableBeanFactory<GameStore> switchableBeanFactory = new SwitchableBeanFactory<GameStore>();
        switchableBeanFactory.setUseBean(type);
        Map<String, String> gameStores = new HashMap<String, String>();
        gameStores.put("memory","inMemoryGameStore");
        gameStores.put("hazelcast","hazelcastGameStore");
        gameStores.put("mongo","mongoGameStore");
        switchableBeanFactory.setMappings(gameStores);
        switchableBeanFactory.setApplicationContext(applicationContext);
        return switchableBeanFactory.getBean();
    }

    @Bean
    public PlayerStore playerStore() {
        SwitchableBeanFactory<PlayerStore> switchableBeanFactory = new SwitchableBeanFactory<PlayerStore>();
        switchableBeanFactory.setUseBean(type);
        Map<String, String> playerStores = new HashMap<String, String>();
        playerStores.put("memory","inMemoryPlayerStore");
        playerStores.put("hazelcast","hazelcastPlayerStore");
        playerStores.put("mongo","mongoPlayerStore");
        switchableBeanFactory.setMappings(playerStores);
        switchableBeanFactory.setApplicationContext(applicationContext);
        return switchableBeanFactory.getBean();
    }

    @Bean
    public TableStore tableStore() {
        SwitchableBeanFactory<TableStore> switchableBeanFactory = new SwitchableBeanFactory<TableStore>();
        switchableBeanFactory.setUseBean(type);
        Map<String, String> tableStores = new HashMap<String, String>();
        tableStores.put("memory","inMemoryTableStore");
        tableStores.put("hazelcast","hazelcastTableStore");
        tableStores.put("mongo","mongoTableStore");
        switchableBeanFactory.setMappings(tableStores);
        switchableBeanFactory.setApplicationContext(applicationContext);
        return switchableBeanFactory.getBean();
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
    public ExternalEventPublisher externalEventPublisher(){
        return new CometDExternalEventPublisher();
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
