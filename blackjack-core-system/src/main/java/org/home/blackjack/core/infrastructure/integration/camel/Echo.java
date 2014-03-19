package org.home.blackjack.core.infrastructure.integration.camel;

import javax.annotation.Resource;
import javax.inject.Named;

import org.apache.camel.ProducerTemplate;

@Named
public class Echo {

    @Resource
    private ProducerTemplate producerTemplate;

    public void echo(String input) throws InterruptedException {
        System.err.println("input arrived " + input);
        producerTemplate.asyncSendBody("cometd://0.0.0.0:9099/outchannel", input + "_" + input);
    }
}
