package org.home.blackjack.core.infrastructure.service;

import java.util.UUID;

import org.home.blackjack.util.ddd.pattern.domain.idgeneration.IDGenerator;

import com.fasterxml.uuid.EthernetAddress;
import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedGenerator;

public class JUGIDGenerationStrategy implements IDGenerator<String> {

	private UUID generateUUID() {

		EthernetAddress address = EthernetAddress.fromInterface();
		TimeBasedGenerator uuidGenerator = Generators.timeBasedGenerator(address);
		return uuidGenerator.generate();
	}

    @Override
    public String generate() {
        return generateUUID().toString();
    }
}
