package org.home.blackjack.core.infrastructure.persistence.table.store.mongo;

import org.home.blackjack.core.domain.table.Table;
import org.home.blackjack.core.infrastructure.persistence.shared.core.PersistenceObject;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class MongoPersistenceTable implements PersistenceObject<Table> {
	
	private final MongoPersistenceTableId id;
	private final String json;
	
	public MongoPersistenceTable(MongoPersistenceTableId id, String json) {
		this.id = id;
		this.json = json;
	}
	public MongoPersistenceTable(String json) {
	    this(getId(json), json);
	    
	}
    private static MongoPersistenceTableId getId(String json) {
        JsonElement element = new Gson().fromJson (json, JsonElement.class);
        JsonObject jsonObject = element.getAsJsonObject();
	    String strId = jsonObject.get("id").getAsJsonObject().get("internal").getAsString();
	    return new MongoPersistenceTableId(strId);
    }
	
	public String getJson() {
		return json;
	}

	@Override
	public MongoPersistenceTableId id() {
		return id;
	}

}
