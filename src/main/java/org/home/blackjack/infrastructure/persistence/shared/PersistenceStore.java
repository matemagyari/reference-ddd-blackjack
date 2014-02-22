package org.home.blackjack.infrastructure.persistence.shared;


public interface PersistenceStore<DOMAIN, PO extends PersistenceObject<DOMAIN>, POID extends PersistenceObjectId> {
	
	<PA extends PersistenceAssembler<DOMAIN, PO>> PA  assembler();
	PO find(POID id);
	void update(PO po);
}
