package org.home.blackjack.core.infrastructure.persistence.shared;


/**
 * Infrastructure Service. Transforms any objects to json and back.
 * @author Mate
 *
 */
public abstract class GsonBasedAssembler {

	private final GsonProvider gsonProvider;
	
	public GsonBasedAssembler( GsonProvider gsonProvider) {
		this.gsonProvider = gsonProvider;
	}

	protected String toJson(Object obj) {
		return gsonProvider.gson().toJson(obj);
	}
	
	protected <T> T fromJson(String json, Class<T> classOfT) {
		return gsonProvider.gson().fromJson(json, classOfT);
	}
}
