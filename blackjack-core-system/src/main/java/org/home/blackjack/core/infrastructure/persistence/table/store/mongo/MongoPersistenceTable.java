package org.home.blackjack.core.infrastructure.persistence.table.store.mongo;

import org.home.blackjack.core.domain.table.Table;
import org.home.blackjack.core.infrastructure.persistence.shared.PersistenceObject;

import com.google.gson.Gson;
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
        JsonObject jsonObject = (JsonObject) new Gson().toJsonTree(json);
	    String strId = jsonObject.get("id").toString();
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