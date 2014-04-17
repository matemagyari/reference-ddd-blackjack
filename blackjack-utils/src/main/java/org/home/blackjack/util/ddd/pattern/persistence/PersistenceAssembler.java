package org.home.blackjack.util.ddd.pattern.persistence;

import org.home.blackjack.util.ddd.pattern.Domain;
import org.home.blackjack.util.ddd.pattern.ID;

public interface PersistenceAssembler<DOMAIN extends Domain, PO extends PersistenceObject<DOMAIN>> {
	
	DOMAIN toDomain(PO persistenceObject);
	PO toPersistence(DOMAIN domainObject);
	PersistenceObjectId toPersistence(ID id);

}
