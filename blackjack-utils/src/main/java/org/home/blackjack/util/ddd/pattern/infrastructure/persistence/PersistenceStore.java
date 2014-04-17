package org.home.blackjack.util.ddd.pattern.infrastructure.persistence;

import org.home.blackjack.util.ddd.pattern.domain.Domain;

public interface PersistenceStore<DOMAIN extends Domain, PO extends PersistenceObject<DOMAIN>, POID extends PersistenceObjectId> {
	
	<PA extends PersistenceAssembler<DOMAIN, PO>> PA  assembler();
	PO find(POID id);
	void update(PO po);
	void create(PO po);
	void clear();
}
