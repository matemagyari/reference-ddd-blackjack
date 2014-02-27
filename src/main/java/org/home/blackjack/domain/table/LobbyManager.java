package org.home.blackjack.domain.table;

import javax.inject.Inject;

import org.home.blackjack.domain.table.core.TableID;

/**
 * Domain service. Responsible for creating tables.
 *
 */
public class LobbyManager {
	
	private static final int NUM_OF_TABLES = 9;
	@Inject
	private TableRepository tableRepository;
	
	public void setupLobbyBeforePlayersCome() {
		
		for(int i=0; i< NUM_OF_TABLES;i++) {
			tableRepository.create(new Table(new TableID()));
		}
		
	}

}
