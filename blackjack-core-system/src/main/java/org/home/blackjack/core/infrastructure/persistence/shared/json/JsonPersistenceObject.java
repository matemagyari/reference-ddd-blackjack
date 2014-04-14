package org.home.blackjack.core.infrastructure.persistence.shared.json;

import org.home.blackjack.core.infrastructure.persistence.shared.core.PersistenceObject;
import org.home.blackjack.core.infrastructure.persistence.shared.core.PersistenceObjectId;
import org.home.blackjack.util.ddd.pattern.Domain;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class JsonPersistenceObject <T extends Domain> implements PersistenceObject<T> {
	
	private final StringPersistenceId<?> id;
	private final String json;
	
	public JsonPersistenceObject(String json) {
		this.id = getId(json);
		this.json = json;
	    
	}
    private static StringPersistenceId<?> getId(String json) {
        JsonObject jsonObject = (JsonObject) new Gson().toJsonTree(json);
	    String strId = jsonObject.get("id").toString();
	    return new StringPersistenceId(strId);
    }
	
	public String getJson() {
		return json;
	}

	@Override
	public PersistenceObjectId<?> id() {
		return id;
	}

}
