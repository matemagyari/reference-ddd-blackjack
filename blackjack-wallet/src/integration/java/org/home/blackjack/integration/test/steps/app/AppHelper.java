package org.home.blackjack.integration.test.steps.app;

import org.home.blackjack.integration.test.steps.base.WalletStep;

import cucumber.api.java.Before;

public class AppHelper {

    @Before
    public void setup() {
        WalletStep.scope = WalletStep.appLevelScope;
    }
}
