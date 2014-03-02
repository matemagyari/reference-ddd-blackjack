package org.home.blackjack.integration.test.util;


public class Util {

	public static void sleep(int time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException ex) {
			new RuntimeException(ex);
		}
	}

}
