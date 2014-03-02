package org.home.blackjack.core.infrastructure.persistence.shared;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Infrastructure Service.
 * @author Mate
 *
 */
public abstract class GsonProvider {
	
	protected GsonBuilder gsonBuilder = new GsonBuilder();
	
	public Gson gson() {
		return gsonBuilder.create();
	}

}
