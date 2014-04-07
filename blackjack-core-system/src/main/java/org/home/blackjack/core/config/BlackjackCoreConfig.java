package org.home.blackjack.core.config;

import org.apache.camel.CamelContext;
import org.apache.camel.CamelContextAware;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.spring.javaconfig.CamelConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;

import java.util.List;

@Configuration
@ComponentScan("org.home.blackjack.core")
@Import({CamelConfiguration.class, BlackjackCoreAppLevelConfig.class})
public class BlackjackCoreConfig extends CamelConfiguration{

    @Autowired private CamelContext camelContext;

    @Bean(name = "template")
    public ProducerTemplate createProducerTemplate() {
        return camelContext.createProducerTemplate();
    }
}
