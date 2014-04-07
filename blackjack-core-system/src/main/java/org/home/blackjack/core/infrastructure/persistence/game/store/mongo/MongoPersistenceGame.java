package org.home.blackjack.core.infrastructure.persistence.game.store.mongo;

import org.home.blackjack.core.domain.game.Game;
import org.home.blackjack.core.infrastructure.persistence.shared.PersistenceObject;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class MongoPersistenceGame implements PersistenceObject<Game> {
	
	private final MongoPersistenceGameId id;
	private final String json;
	
	public MongoPersistenceGame(MongoPersistenceGameId id, String json) {
		this.id = id;
		this.json = json;
	}
	public MongoPersistenceGame(String json) {
	    this(getId(json), json);
	    
	}
    private static MongoPersistenceGameId getId(String json) {
        JsonObject jsonObject = (JsonObject) new Gson().toJsonTree(json);
	    String strId = jsonObject.get("id").toString();
	    return new MongoPersistenceGameId(strId);
    }
	
	public String getJson() {
		return json;
	}

	@Override
	public MongoPersistenceGameId id() {
		return id;
	}

}
