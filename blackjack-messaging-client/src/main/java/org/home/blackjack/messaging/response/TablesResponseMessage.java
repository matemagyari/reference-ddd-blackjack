package org.home.blackjack.messaging.response;

import java.util.List;
import java.util.Map;

import org.home.blackjack.messaging.common.Message;

import com.google.common.collect.Maps;

public class TablesResponseMessage extends Message {

	public final Map<String, List<String>> tablesWithPlayers;
	
	public TablesResponseMessage(Map<String, List<String>> tablesWithPlayers) {
		this.tablesWithPlayers = Maps.newHashMap(tablesWithPlayers);
	}
	
}


