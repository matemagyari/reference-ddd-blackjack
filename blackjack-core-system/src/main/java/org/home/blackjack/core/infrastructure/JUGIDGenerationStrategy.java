package org.home.blackjack.core.infrastructure;

import java.util.UUID;

import javax.inject.Named;

import org.home.blackjack.util.ddd.pattern.domain.IDGenerationStrategy;

import com.fasterxml.uuid.EthernetAddress;
import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedGenerator;

/**
 * UUID generation strategy that delegates to java.util.UUID randomUUID() utility.
 * 
 * @author michele.sollecito
 */
@Named
public class JUGIDGenerationStrategy implements IDGenerationStrategy {

	@Override
	public UUID generate() {

		EthernetAddress address = EthernetAddress.fromInterface();
		TimeBasedGenerator uuidGenerator = Generators.timeBasedGenerator(address);
		return uuidGenerator.generate();
	}
}
