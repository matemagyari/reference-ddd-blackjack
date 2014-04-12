package org.home.blackjack.core.integration.test.scan;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features={"src/integration/resources/features"}
                 ,glue    ={"org.home.blackjack.core.integration.test.steps.base","org.home.blackjack.core.integration.test.steps.messaging"})
public class MessagingAcceptanceTests_IT {

}
