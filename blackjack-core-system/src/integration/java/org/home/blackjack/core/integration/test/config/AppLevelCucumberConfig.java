package org.home.blackjack.core.integration.test.config;

import org.home.blackjack.core.app.events.external.ExternalEventPublisher;
import org.home.blackjack.core.config.BlackjackCoreAppLevelConfig;
import org.home.blackjack.core.domain.cashier.WalletService;
import org.home.blackjack.core.domain.game.DeckFactory;
import org.home.blackjack.core.integration.test.fakes.FakeDeckFactory;
import org.home.blackjack.core.integration.test.fakes.FakeExternalEventPublisher;
import org.home.blackjack.core.integration.test.fakes.FakeWalletService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;

@Configuration
@Import(BlackjackCoreAppLevelConfig.class)
@EnableAspectJAutoProxy
public class AppLevelCucumberConfig {

    @Bean
    @Primary
    public WalletService walletService(){
        return new FakeWalletService();
    }

    @Bean
    @Primary
    public ExternalEventPublisher externalEventPublisher(){
        return new FakeExternalEventPublisher();
    }

    @Bean
    @Primary
    public DeckFactory deckFactory(){
        return new FakeDeckFactory();
    }
}