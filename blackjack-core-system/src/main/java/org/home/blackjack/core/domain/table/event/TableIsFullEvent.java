package org.home.blackjack.core.domain.table.event;

import java.util.List;

import org.home.blackjack.core.domain.shared.PlayerID;
import org.home.blackjack.core.domain.shared.TableID;

import com.google.common.collect.Lists;

public class TableIsFullEvent extends TableEvent {

	private List<PlayerID> players;

	public TableIsFullEvent(TableID id, List<PlayerID> players) {
		super(id);
		this.players = Lists.newArrayList(players);
	}
	
	public List<PlayerID> players() {
		return Lists.newArrayList(players);
	}

}
