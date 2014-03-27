package org.home.blackjack.core.infrastructure.integration.rest;

import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.Client;

public class RestClient {
	//TODO from properties file
	private static final String REGISTRATION_REST_URL = "http://0.0.0.0:9090/blackjack/register/";
	private final Client client;
	
	public RestClient() {
		client = Client.create();
	}

	public String register(String playerName) {
		String url = REGISTRATION_REST_URL + playerName;
		return client.resource(url).accept(MediaType.APPLICATION_JSON,MediaType.TEXT_PLAIN).put(String.class);
	}
	
	public static void main(String...strings) {
		String response = Client.create().resource(REGISTRATION_REST_URL+"echo/Bela").get(String.class);
		System.err.println(response);
	}
}
