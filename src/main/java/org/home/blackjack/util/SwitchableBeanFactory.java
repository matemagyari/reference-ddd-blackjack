package org.home.blackjack.util;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SwitchableBeanFactory<T> implements ApplicationContextAware, FactoryBean<T> {

    private static final Logger log = Logger.getLogger(SwitchableBeanFactory.class);

    private Map<String, String> mappings;

    private String useBean;

    private T bean;

    public T getBean() {
        return bean;
    }

    @Override
    public T getObject() throws Exception {
        log.info(String.format("using bean %s from mapping %s", useBean, mappings));
        return bean;
    }

    @Override
    public Class<?> getObjectType() {
        return null;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    public void setMappings(Map<String, String> mappings) {
        this.mappings = mappings;
    }

    public void setUseBean(String useBean) {
        this.useBean = useBean;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        bean = (T) applicationContext.getBean(mappings.get(useBean));
    }

}
