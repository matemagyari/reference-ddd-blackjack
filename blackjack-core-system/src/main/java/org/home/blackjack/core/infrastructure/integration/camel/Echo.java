package org.home.blackjack.core.infrastructure.integration.camel;

import javax.inject.Named;

@Named
public class Echo {

	public String echo(String input) {
		System.err.println("input arrived " + input);
		return input + "_" + input;
	}
}
