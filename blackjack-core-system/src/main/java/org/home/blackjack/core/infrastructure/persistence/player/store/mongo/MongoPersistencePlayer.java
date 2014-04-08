package org.home.blackjack.core.infrastructure.persistence.player.store.mongo;

import org.home.blackjack.core.domain.player.Player;
import org.home.blackjack.core.infrastructure.persistence.shared.PersistenceObject;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class MongoPersistencePlayer implements PersistenceObject<Player> {
	
	private final MongoPersistencePlayerId id;
	private final String json;
	
	public MongoPersistencePlayer(MongoPersistencePlayerId id, String json) {
		this.id = id;
		this.json = json;
	}
	public MongoPersistencePlayer(String json) {
	    this(getId(json), json);
	    
	}
    private static MongoPersistencePlayerId getId(String json) {
        JsonObject jsonObject = (JsonObject) new Gson().toJsonTree(json);
	    String strId = jsonObject.get("id").toString();
	    return new MongoPersistencePlayerId(strId);
    }
	
	public String getJson() {
		return json;
	}

	@Override
	public MongoPersistencePlayerId id() {
		return id;
	}

}
