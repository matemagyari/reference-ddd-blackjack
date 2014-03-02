package org.home.blackjack.integration.test.util;

import java.util.Map;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.google.common.collect.Maps;

public abstract class CucumberService {

    private static final Map<String, ClassPathXmlApplicationContext> contexts = Maps.newHashMap();
    
    private final String appContextXmlPath;

    public CucumberService(String appContextXmlPath) {
        this.appContextXmlPath = appContextXmlPath;
        
        //reuse started-up context
        if ( context() == null) {
            String[] contextPaths = new String[] { "classpath:" + appContextXmlPath };
            ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(contextPaths, true);
            contexts.put(appContextXmlPath, ctx);
        }
    }
/*
    public <T> T getBean(Class<T> clazz) {
        return  context().getBean(clazz);
    }
*/
    public Object getBean(String name) {
        return  context().getBean(name);
    }
    
    private ClassPathXmlApplicationContext context() {
        return contexts.get(appContextXmlPath);
    }

}
