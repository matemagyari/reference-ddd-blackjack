package org.home.blackjack.core.integration.test.config;

import org.home.blackjack.core.config.BlackjackCoreConfig;
import org.home.blackjack.core.integration.test.fakes.FakeDeckFactory;
import org.home.blackjack.core.integration.test.fakes.FakeWalletService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

@Configuration
@Import(BlackjackCoreConfig.class)
@EnableAspectJAutoProxy
public class EndToEndCucumberConfig {

    @Bean(name = "walletService")
    public FakeWalletService fakeWalletService(){
        return new FakeWalletService();
    }

    @Bean(name = "deckFactory")
    public FakeDeckFactory deckFactory(){
        return new FakeDeckFactory();
    }
}
