package org.home.blackjack.core.integration.test.util;


import org.home.blackjack.core.integration.test.config.AppLevelCucumberConfig;

public class AppLevelCucumberService extends CucumberService {

    public AppLevelCucumberService() {
        super(AppLevelCucumberConfig.class);
    }

}
