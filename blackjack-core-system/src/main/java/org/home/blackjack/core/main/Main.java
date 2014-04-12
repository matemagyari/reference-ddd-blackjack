package org.home.blackjack.core.main;

import org.home.blackjack.core.config.BlackjackCoreConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class Main {

    public static AnnotationConfigApplicationContext applicationContext;

	public static void main(String[] args) {

        applicationContext = new AnnotationConfigApplicationContext(BlackjackCoreConfig.class);
		applicationContext.registerShutdownHook();
		
		System.out.println("Blackjack started");


	}

}
