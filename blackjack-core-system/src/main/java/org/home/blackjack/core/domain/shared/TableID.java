package org.home.blackjack.core.domain.shared;

import org.home.blackjack.util.ddd.pattern.ID;

public class TableID extends ID {
	
	public TableID() {
		super();
	}
	
	private TableID(String id) {
		super(id);
	}

	public static TableID createFrom(String id) {
		return new TableID(id);
	}
}
