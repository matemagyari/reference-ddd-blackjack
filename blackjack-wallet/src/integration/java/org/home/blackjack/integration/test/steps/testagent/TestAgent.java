package org.home.blackjack.integration.test.steps.testagent;

import org.home.blackjack.integration.test.util.CucumberService;


public abstract class TestAgent {
	
    public TestAgent() {
        initDependencies();
    }

    protected void initDependencies() {
    }
    
    protected abstract CucumberService cucumberService();
    
    public void reset() {
    }
    
	


}
