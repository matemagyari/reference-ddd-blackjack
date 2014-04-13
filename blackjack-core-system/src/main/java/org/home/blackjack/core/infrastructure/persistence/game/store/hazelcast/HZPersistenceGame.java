package org.home.blackjack.core.infrastructure.persistence.game.store.hazelcast;

import org.home.blackjack.core.domain.game.Game;
import org.home.blackjack.core.infrastructure.persistence.shared.core.PersistenceObject;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class HZPersistenceGame implements PersistenceObject<Game> {
	
	private final HZPersistenceGameId id;
	private final String json;
	
	public HZPersistenceGame(HZPersistenceGameId id, String json) {
		this.id = id;
		this.json = json;
	}
	public HZPersistenceGame(String json) {
	    this(getId(json), json);
	    
	}
    private static HZPersistenceGameId getId(String json) {
        JsonObject jsonObject = (JsonObject) new Gson().toJsonTree(json);
	    String strId = jsonObject.get("id").toString();
	    return new HZPersistenceGameId(strId);
    }
	
	public String getJson() {
		return json;
	}

	@Override
	public HZPersistenceGameId id() {
		return id;
	}

}
