package org.home.blackjack.core.app.service.query;

import java.util.List;
import java.util.Map;

import org.home.blackjack.core.domain.shared.PlayerID;
import org.home.blackjack.core.domain.table.Table;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class TablesDTO {
	
	private final Map<String, List<String>> tablesWithPlayers;
	
	public TablesDTO(List<Table> tables) {
		tablesWithPlayers = Maps.newHashMap();
		for (Table table : tables) {
			List<String> playerIds = Lists.newArrayList();
			for (PlayerID playerID : table.getPlayers()) {
				playerIds.add(playerID.toString());
			}
			tablesWithPlayers.put(table.getID().toString(), playerIds);
		}
	}
	

}
