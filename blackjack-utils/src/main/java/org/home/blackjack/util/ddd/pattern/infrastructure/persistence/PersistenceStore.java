package org.home.blackjack.util.ddd.pattern.infrastructure.persistence;

import org.home.blackjack.util.ddd.pattern.domain.Domain;

public interface PersistenceStore<D extends Domain, P extends PersistenceObject<D>, PI extends PersistenceObjectId> {
	
	<PA extends PersistenceAssembler<D, P>> PA  assembler();
	P find(PI id);
	void update(P po);
	void create(P po);
	void clear();
}
