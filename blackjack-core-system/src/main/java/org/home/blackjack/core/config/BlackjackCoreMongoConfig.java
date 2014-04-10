package org.home.blackjack.core.config;

import com.mongodb.Mongo;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.spring.javaconfig.CamelConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;

@Configuration
public class BlackjackCoreMongoConfig {

    @Bean
    public Mongo mongo() throws Exception {
        return new Mongo("localhost", 27017);
    }
}
