package org.home.blackjack.core.config;

import org.apache.camel.ProducerTemplate;
import org.apache.camel.spring.javaconfig.CamelConfiguration;
import org.home.blackjack.core.app.events.external.ExternalEventPublisher;
import org.home.blackjack.core.infrastructure.integration.camel.CometDExternalEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mongodb.Mongo;

@Configuration
public class BlackjackCoreInfLevelConfig extends CamelConfiguration {

    @Bean
    public ExternalEventPublisher externalEventPublisher(){
        return new CometDExternalEventPublisher();
    }

    @Bean
    public ProducerTemplate producerTemplate() throws Exception{
        return camelContext().createProducerTemplate();
    }

    @Bean
    public Mongo mongo() throws Exception {
        return new Mongo("localhost", 27017);
    }
}
