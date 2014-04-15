package org.home.blackjack.core.infrastructure.persistence.shared.core;

import org.home.blackjack.util.ddd.pattern.Domain;

public interface PersistenceObject<DOMAIN extends Domain> {
	
	PersistenceObjectId id();
	
	

}
