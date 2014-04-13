package org.home.blackjack.core.infrastructure.persistence.table.store.hazelcast;

import org.home.blackjack.core.domain.table.Table;
import org.home.blackjack.core.infrastructure.persistence.shared.core.PersistenceObject;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class HZPersistenceTable implements PersistenceObject<Table> {
	
	private final HZPersistenceTableId id;
	private final String json;
	
	public HZPersistenceTable(HZPersistenceTableId id, String json) {
		this.id = id;
		this.json = json;
	}
	public HZPersistenceTable(String json) {
	    this(getId(json), json);
	    
	}
    private static HZPersistenceTableId getId(String json) {
        JsonObject jsonObject = (JsonObject) new Gson().toJsonTree(json);
	    String strId = jsonObject.get("id").toString();
	    return new HZPersistenceTableId(strId);
    }
	
	public String getJson() {
		return json;
	}

	@Override
	public HZPersistenceTableId id() {
		return id;
	}

}
