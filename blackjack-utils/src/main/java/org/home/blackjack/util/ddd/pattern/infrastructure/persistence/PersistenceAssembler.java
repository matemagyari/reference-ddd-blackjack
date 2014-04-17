package org.home.blackjack.util.ddd.pattern.infrastructure.persistence;

import org.home.blackjack.util.ddd.pattern.domain.Domain;
import org.home.blackjack.util.ddd.pattern.domain.ID;

public interface PersistenceAssembler<DOMAIN extends Domain, PO extends PersistenceObject<DOMAIN>> {
	
	DOMAIN toDomain(PO persistenceObject);
	PO toPersistence(DOMAIN domainObject);
	PersistenceObjectId toPersistence(ID id);

}
