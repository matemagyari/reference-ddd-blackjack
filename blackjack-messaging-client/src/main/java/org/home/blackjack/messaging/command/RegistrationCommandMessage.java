package org.home.blackjack.messaging.command;

import org.home.blackjack.messaging.common.Message;


public class RegistrationCommandMessage extends Message {

	public final String name;

	public RegistrationCommandMessage(String name) {
		this.name = name;
	}
	
}
