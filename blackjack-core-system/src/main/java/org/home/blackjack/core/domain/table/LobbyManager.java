package org.home.blackjack.core.domain.table;

import javax.inject.Inject;
import javax.inject.Named;

import org.home.blackjack.core.domain.shared.TableID;
import org.home.blackjack.util.ddd.pattern.domain.IDGenerator;

/**
 * Domain service. Responsible for creating tables.
 * 
 */
@Named
public class LobbyManager {

	private static final int NUM_OF_TABLES = 9;
	@Inject
	private TableRepository tableRepository;
	@Inject
	private IDGenerator idGenerator;

	public void setupLobbyBeforePlayersCome() {

		if (tableRepository.isEmpty()) {
			for (int i = 0; i < NUM_OF_TABLES; i++) {
				TableID tableID = TableID.createFrom(idGenerator.generate2());
                tableRepository.create(new Table(tableID));
			}
		}

	}

}
