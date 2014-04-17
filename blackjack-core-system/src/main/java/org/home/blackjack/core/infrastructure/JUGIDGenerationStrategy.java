package org.home.blackjack.core.infrastructure;

import java.util.UUID;

import org.home.blackjack.util.ddd.pattern.domain.IDGenerator;

import com.fasterxml.uuid.EthernetAddress;
import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedGenerator;

public class JUGIDGenerationStrategy implements IDGenerator {

	@Override
	public UUID generate() {

		EthernetAddress address = EthernetAddress.fromInterface();
		TimeBasedGenerator uuidGenerator = Generators.timeBasedGenerator(address);
		return uuidGenerator.generate();
	}

    @Override
    public String generate2() {
        return generate().toString();
    }
}
