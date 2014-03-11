package org.home.blackjack.core.infrastructure.integration.camel;

import javax.annotation.Resource;
import javax.inject.Named;

import org.apache.camel.builder.RouteBuilder;

@Named
public class CoreRouteBuilder extends RouteBuilder {

	@Resource
	private Echo echo;
	
	public void configure() {

		from("cometd://0.0.0.0:9099/inchannel")
			.bean(echo, "echo")
			.to("cometd://0.0.0.0:9099/outchannel");
	}

}