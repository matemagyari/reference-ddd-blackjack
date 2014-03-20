package org.home.blackjack.core.main;

import org.springframework.context.support.ClassPathXmlApplicationContext;


public class Main {

	static ClassPathXmlApplicationContext applicationContext;

	public static void main(String[] args) {
		
		applicationContext = new ClassPathXmlApplicationContext("classpath:META-INF/applicationContext-blackjack-core.xml");
		applicationContext.registerShutdownHook();
		
		System.err.println("heyy6");


	}

}
