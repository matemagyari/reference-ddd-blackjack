package org.home.blackjack.core.config;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.spring.javaconfig.CamelConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan("org.home.blackjack.core")
@Import({CamelConfiguration.class, BlackjackCoreAppLevelConfig.class, BlackjackCoreMongoConfig.class})
public class BlackjackCoreConfig {

    @Autowired
    private ApplicationContext applicationContext;

    @Bean
    public ProducerTemplate template() throws Exception{
        return applicationContext.getBean(CamelContext.class).createProducerTemplate();
    }
}
