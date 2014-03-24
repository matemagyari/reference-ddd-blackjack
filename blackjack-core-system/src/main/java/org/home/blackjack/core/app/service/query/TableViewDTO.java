package org.home.blackjack.core.app.service.query;

import java.util.List;

import org.home.blackjack.core.domain.shared.PlayerID;
import org.home.blackjack.core.domain.shared.TableID;

import com.google.common.collect.Lists;

public class TableViewDTO {
	
	private final TableID tableId;
	private final List<PlayerID> players;

	public TableViewDTO(TableID tableId, List<PlayerID> players) {
		this.tableId = tableId;
		this.players = Lists.newArrayList(players);
	}
	
	public List<PlayerID> getPlayers() {
		return Lists.newArrayList(players);
	}
	public TableID getTableId() {
		return tableId;
	}
}
