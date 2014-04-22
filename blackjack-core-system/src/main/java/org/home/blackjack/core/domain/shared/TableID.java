package org.home.blackjack.core.domain.shared;

import org.home.blackjack.util.ddd.pattern.domain.model.ID;

public class TableID extends ID {
	
	private TableID(String id) {
		super(id);
	}

	public static TableID createFrom(String id) {
		return new TableID(id);
	}
}
