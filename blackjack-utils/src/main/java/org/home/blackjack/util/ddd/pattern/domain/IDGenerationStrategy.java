package org.home.blackjack.util.ddd.pattern.domain;

import java.util.UUID;

/**
 * UUID generation strategy.
 * 
 * @author michele.sollecito
 */
public interface IDGenerationStrategy {

	/**
	 * Generates a UUID.
	 * 
	 * @return the generated UUID
	 */
	UUID generate();
}
