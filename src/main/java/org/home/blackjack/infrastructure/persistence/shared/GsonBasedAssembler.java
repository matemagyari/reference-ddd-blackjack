package org.home.blackjack.infrastructure.persistence.shared;


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
