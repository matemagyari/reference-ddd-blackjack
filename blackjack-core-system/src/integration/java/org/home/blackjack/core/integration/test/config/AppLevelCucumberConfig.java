package org.home.blackjack.core.integration.test.config;

import org.home.blackjack.core.config.BlackjackCoreAppLevelConfig;
import org.home.blackjack.core.config.BlackjackCoreConfig;
import org.home.blackjack.core.integration.test.fakes.FakeDeckFactory;
import org.home.blackjack.core.integration.test.fakes.FakeExternalEventPublisher;
import org.home.blackjack.core.integration.test.fakes.FakeWalletService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

@Configuration
@Import(BlackjackCoreAppLevelConfig.class)
@EnableAspectJAutoProxy
public class AppLevelCucumberConfig {

    @Bean(name = "walletService")
    public FakeWalletService fakeWalletService(){
        return new FakeWalletService();
    }

    @Bean(name = "externalEventPublisher")
    public FakeExternalEventPublisher fakeExternalEventPublisher(){
        return new FakeExternalEventPublisher();
    }

    @Bean(name = "fakeDeckFactory")
    public FakeDeckFactory fakeDeckFactory(){
        return new FakeDeckFactory();
    }
}