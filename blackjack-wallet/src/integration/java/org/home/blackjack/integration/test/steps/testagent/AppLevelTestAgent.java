package org.home.blackjack.integration.test.steps.testagent;

import org.home.blackjack.integration.test.util.AppLevelCucumberService;
import org.home.blackjack.integration.test.util.CucumberService;
import org.home.blackjack.wallet.app.client.transaction.TransactionApplicationService;

public class AppLevelTestAgent extends TestAgent {

	private CucumberService cucumberService;
	private TransactionApplicationService transactionApplicationService;

	@Override
	public void reset() {
		super.reset();
	}

	@Override
	protected void initDependencies() {
		cucumberService = new AppLevelCucumberService();
		super.initDependencies();
		transactionApplicationService = cucumberService().getBean(TransactionApplicationService.class);

	}

	@Override
	protected CucumberService cucumberService() {
		// TODO Auto-generated method stub
		return null;
	}

	
}
