package org.home.blackjack.integration.test.scan;

import org.junit.runner.RunWith;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features={"src/integration/resources/features"}
                 ,glue    ={"org.home.blackjack.wallet.integration.test.steps.base","org.home.blackjack.wallet.integration.test.steps.app"})

public class AppLevelAcceptanceTests_IT {

}
