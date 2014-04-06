package org.home.blackjack.core.integration.test.util;


public class EndToEndCucumberService extends CucumberService {

    public EndToEndCucumberService() {
        super("src/integration/resources/applicationContext-blackjack-core-cucumber-endtoend.xml");
    }

}
