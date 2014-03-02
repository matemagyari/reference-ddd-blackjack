package org.home.blackjack.core.infrastructure.persistence.shared;

import org.home.blackjack.util.ddd.pattern.ID;

public interface PersistenceAssembler<DOMAIN, PO extends PersistenceObject<DOMAIN>> {
	
	DOMAIN toDomain(PO persistenceObject);
	PO toPersistence(DOMAIN domainObject);
	PersistenceObjectId toPersistence(ID id);

}
