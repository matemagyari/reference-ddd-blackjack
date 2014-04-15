package org.home.blackjack.core.infrastructure.persistence.shared.json;

import org.home.blackjack.core.infrastructure.persistence.shared.core.PersistenceObject;
import org.home.blackjack.core.infrastructure.persistence.shared.core.PersistenceObjectId;
import org.home.blackjack.util.ddd.pattern.Domain;
import org.home.blackjack.util.ddd.pattern.ID;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class JsonPersistenceObject <T extends Domain> implements PersistenceObject<T> {
	
	private final StringPersistenceId<? extends ID> id;
	private final String json;
	
	public JsonPersistenceObject(String json) {
		this.id = getId(json);
		this.json = json;
	    
	}
    private static <I extends ID> StringPersistenceId<I> getId(String json) {
        JsonElement element = new Gson().fromJson (json, JsonElement.class);
        JsonObject jsonObject = element.getAsJsonObject();
        String strId = jsonObject.get("id").getAsJsonObject().get("internal").getAsString();
	    return new StringPersistenceId<I>(strId);
    }
	
	public String getJson() {
		return json;
	}

	@Override
	public PersistenceObjectId id() {
		return id;
	}

}
