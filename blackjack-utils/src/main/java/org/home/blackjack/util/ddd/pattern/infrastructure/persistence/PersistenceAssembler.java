package org.home.blackjack.util.ddd.pattern.infrastructure.persistence;

import org.home.blackjack.util.ddd.pattern.domain.model.Domain;
import org.home.blackjack.util.ddd.pattern.domain.model.ID;

public interface PersistenceAssembler<D extends Domain, P extends PersistenceObject<D>> {
	
	D toDomain(P persistenceObject);
	P toPersistence(D domainObject);
	PersistenceObjectId toPersistence(ID id);

}
