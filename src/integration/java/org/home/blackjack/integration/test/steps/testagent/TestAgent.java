package org.home.blackjack.integration.test.steps.testagent;


public abstract class TestAgent {
    
    public TestAgent() {
        initDependencies();
    }

    protected abstract void initDependencies();
    public abstract void reset();



}
