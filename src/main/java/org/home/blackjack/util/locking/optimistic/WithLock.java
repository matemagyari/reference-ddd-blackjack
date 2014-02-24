package org.home.blackjack.util.locking.optimistic;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface WithLock {
    
    Class<? extends Exception>[] on();

    int times() default 1;
    
    String repository();
    String key();
    
}