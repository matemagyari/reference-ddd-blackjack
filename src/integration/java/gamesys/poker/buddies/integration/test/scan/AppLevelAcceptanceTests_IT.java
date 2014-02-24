package gamesys.poker.buddies.integration.test.scan;

import org.junit.runner.RunWith;

import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@Cucumber.Options(features={"src/test/resources/features"}
                 ,glue    ={"gamesys.poker.buddies.integration.test.steps.base","gamesys.poker.buddies.integration.test.steps.app"})

public class AppLevelAcceptanceTests_IT {

}
