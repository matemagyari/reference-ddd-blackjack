package org.home.blackjack.core.integration.test.cucumber;


import org.home.blackjack.core.integration.test.config.EndToEndCucumberConfig;

public class EndToEndCucumberService extends CucumberService {

    public EndToEndCucumberService() {
        super(EndToEndCucumberConfig.class);
    }

}
