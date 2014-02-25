package org.home.blackjack.integration.test.scan;

import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@Cucumber.Options(features={"src/integration/resources/features"}
                 ,glue    ={"org.home.blackjack.integration.test.steps.base","org.home.blackjack.integration.test.steps.app"})

public class AppLevelAcceptanceTests_IT {

}
