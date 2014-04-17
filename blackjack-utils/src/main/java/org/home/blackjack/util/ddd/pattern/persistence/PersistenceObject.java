package org.home.blackjack.util.ddd.pattern.persistence;

import org.home.blackjack.util.ddd.pattern.Domain;

public interface PersistenceObject<DOMAIN extends Domain> {
	
	PersistenceObjectId id();
	
	

}
