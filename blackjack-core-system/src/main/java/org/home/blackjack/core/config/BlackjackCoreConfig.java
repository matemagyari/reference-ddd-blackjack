package org.home.blackjack.core.config;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.spring.javaconfig.CamelConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan("org.home.blackjack.core")
@Import(BlackjackCoreAppLevelConfig.class)
public class BlackjackCoreConfig extends CamelConfiguration {

    @Bean(name = "template")
    public ProducerTemplate createProducerTemplate(CamelContext camelContext) {
        return camelContext.createProducerTemplate();
    }
}
