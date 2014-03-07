package org.home.blackjack.integration.test.steps.messaging;

import org.home.blackjack.integration.test.steps.base.WalletStep;

import cucumber.api.java.Before;

public class MessagingHelper {
    
    @Before
    public void setup() {
        WalletStep.scope = WalletStep.messagingLevelScope;
    }

}
