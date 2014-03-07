package org.home.blackjack.integration.test.steps.base;

import org.home.blackjack.integration.test.steps.testagent.AppLevelTestAgent;
import org.home.blackjack.integration.test.steps.testagent.MessagingTestAgent;
import org.home.blackjack.integration.test.steps.testagent.TestAgent;

public class WalletStep {

	public static String scope;
	public static final String messagingLevelScope = "messagingLevelScope";
	public static final String appLevelScope = "appLevelScope";

	private final TestAgent testAgent;

	public WalletStep() {

		if (messagingLevelScope.equals(scope)) {
			testAgent = new MessagingTestAgent();
		} else if (appLevelScope.equals(scope)) {
			testAgent = new AppLevelTestAgent();
		} else {
			throw new RuntimeException("Hey I don't know which TestAgent to use");
		}
		testAgent.reset();
	}
}
