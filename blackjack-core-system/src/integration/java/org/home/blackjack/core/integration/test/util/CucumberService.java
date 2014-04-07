package org.home.blackjack.core.integration.test.util;

import java.util.Map;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.google.common.collect.Maps;

public abstract class CucumberService {

    private static final Map<Class<?>, AnnotationConfigApplicationContext> contexts = Maps.newHashMap();
    
    private final Class<?> appContextConfig;

    public CucumberService(Class<?> appContextConfig) {
        this.appContextConfig = appContextConfig;
        
        //reuse started-up context
        if ( context() == null) {
            AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(appContextConfig);
            contexts.put(appContextConfig, ctx);
        }
    }

    public <T> T getBean(Class<T> clazz) {
        return  context().getBean(clazz);
    }

    public Object getBean(String name) {
        return  context().getBean(name);
    }
    
    private AnnotationConfigApplicationContext context() {
        return contexts.get(appContextConfig);
    }

}
