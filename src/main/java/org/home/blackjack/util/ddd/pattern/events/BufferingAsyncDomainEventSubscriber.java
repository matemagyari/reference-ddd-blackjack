package org.home.blackjack.util.ddd.pattern.events;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public abstract class BufferingAsyncDomainEventSubscriber<T extends DomainEvent> implements DomainEventSubscriber<T> {
	
	private final static Executor EXECUTOR = Executors.newFixedThreadPool(100);
	private final List<T> bufferedEvents =  Collections.synchronizedList(new ArrayList<T>());
	
    @Override
    public void handleEvent(T event) {
    	System.err.println(this  +" got event " + event);
    	bufferedEvents.add(event);
    }
    
    public synchronized void flush() {
    	
    	System.err.println(this  +" is flushed " + bufferedEvents);
    	while(!bufferedEvents.isEmpty()) {
    		final T nextEvent = bufferedEvents.remove(0);
    		EXECUTOR.execute(new Runnable() {
				
				@Override
				public void run() {
					processAsync(nextEvent);
				}
			});
    		
    	}
    }

	protected abstract void processAsync(T nextEvent);

}
