package org.home.blackjack.integration.test.steps.testagent;

import org.home.blackjack.integration.test.util.AppLevelCucumberService;
import org.home.blackjack.integration.test.util.CucumberService;

public class AppLevelTestAgent extends TestAgent {
    
    
    @Override
    public void reset() {
    }

    @Override
    protected void initDependencies() {
        CucumberService cucumberService = new AppLevelCucumberService();
    }


}
