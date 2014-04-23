package org.home.blackjack.core.app.service.query;

import java.util.List;
import java.util.Map;

import org.home.blackjack.core.app.dto.QueryResponse;
import org.home.blackjack.core.domain.shared.PlayerID;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class TablesDTO extends QueryResponse {

	private final Map<String, List<String>> tablesWithPlayers;

	public TablesDTO(PlayerID playerID, List<TableViewDTO> tables) {
		super(playerID);
		tablesWithPlayers = Maps.newHashMap();
		for (TableViewDTO table : tables) {
			List<String> playerIds = Lists.newArrayList();
			for (PlayerID player : table.getPlayers()) {
				playerIds.add(player.toString());
			}
			tablesWithPlayers.put(table.getTableId().toString(), playerIds);
		}
	}
	
	public Map<String, List<String>> getTablesWithPlayers() {
        return Maps.newHashMap(tablesWithPlayers);
    }

	@Override
	public String toString() {
		return "[tablesWithPlayers=" + tablesWithPlayers + "]";
	}
}
