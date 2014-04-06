package org.home.blackjack.core.integration.test.util;


public class AppLevelCucumberService extends CucumberService {

    public AppLevelCucumberService() {
        super("src/integration/resources/applicationContext-blackjack-core-cucumber-applevel.xml");
    }

}
