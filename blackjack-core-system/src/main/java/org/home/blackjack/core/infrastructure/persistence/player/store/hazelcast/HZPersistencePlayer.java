package org.home.blackjack.core.infrastructure.persistence.player.store.hazelcast;

import org.home.blackjack.core.domain.player.Player;
import org.home.blackjack.core.infrastructure.persistence.shared.core.PersistenceObject;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class HZPersistencePlayer implements PersistenceObject<Player> {
	
	private final HZPersistencePlayerId id;
	private final String json;
	
	public HZPersistencePlayer(HZPersistencePlayerId id, String json) {
		this.id = id;
		this.json = json;
	}
	public HZPersistencePlayer(String json) {
	    this(getId(json), json);
	    
	}
    private static HZPersistencePlayerId getId(String json) {
        JsonObject jsonObject = (JsonObject) new Gson().toJsonTree(json);
	    String strId = jsonObject.get("id").toString();
	    return new HZPersistencePlayerId(strId);
    }
	
	public String getJson() {
		return json;
	}

	@Override
	public HZPersistencePlayerId id() {
		return id;
	}

}
