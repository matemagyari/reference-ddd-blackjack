package org.home.blackjack.integration.test.steps.testagent;

import org.home.blackjack.integration.test.util.CucumberService;
import org.home.blackjack.integration.test.util.EndToEndCucumberService;

public class MessagingTestAgent extends TestAgent {
    

    @Override
    public void reset() {
    }

    
    @Override
    protected  void initDependencies() {
        CucumberService cucumberService = new EndToEndCucumberService();
    }

    
}
