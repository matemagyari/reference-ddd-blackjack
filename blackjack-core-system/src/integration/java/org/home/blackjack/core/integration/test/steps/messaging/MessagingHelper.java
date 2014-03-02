package org.home.blackjack.core.integration.test.steps.messaging;

import org.home.blackjack.core.integration.test.steps.base.GameStep;

import cucumber.api.java.Before;

public class MessagingHelper {
    
    @Before
    public void setup() {
        GameStep.scope = GameStep.messagingLevelScope;
    }

}
