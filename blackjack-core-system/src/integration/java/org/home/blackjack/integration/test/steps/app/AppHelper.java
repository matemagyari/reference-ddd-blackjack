package org.home.blackjack.integration.test.steps.app;

import org.home.blackjack.integration.test.steps.base.GameStep;

import cucumber.api.java.Before;

public class AppHelper {

    @Before
    public void setup() {
        GameStep.scope = GameStep.appLevelScope;
    }
}
