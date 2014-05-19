//   Copyright 2012,2013 Vaughn Vernon
//
//   Licensed under the Apache License, Version 2.0 (the "License");
//   you may not use this file except in compliance with the License.
//   You may obtain a copy of the License at
//
//       http://www.apache.org/licenses/LICENSE-2.0
//
//   Unless required by applicable law or agreed to in writing, software
//   distributed under the License is distributed on an "AS IS" BASIS,
//   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//   See the License for the specific language governing permissions and
//   limitations under the License.

package org.home.blackjack.util.ddd.pattern.infrastructure.event;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.home.blackjack.util.ddd.pattern.app.event.DomainEventSubscriber;
import org.home.blackjack.util.ddd.pattern.domain.model.DomainEvent;
import org.home.blackjack.util.ddd.pattern.domain.model.DomainEventPublisher;

import com.google.common.collect.Lists;

public class LightweightDomainEventBus implements DomainEventPublisher {

    private final static Executor EXECUTOR = Executors.newFixedThreadPool(100);
    private static Logger LOGGER = Logger.getLogger(LightweightDomainEventBus.class);

    private boolean publishing;

    @Resource
    private List<DomainEventSubscriber> subscribers;

    private List<DomainEvent> bufferedEvents = Lists.newArrayList();

    @Override
    public <T extends DomainEvent> void publish(final T aDomainEvent) {
        if (!this.isPublishing() && this.hasSubscribers()) {

            try {
                this.setPublishing(true);
                bufferedEvents.add(aDomainEvent);
            } finally {
                this.setPublishing(false);
            }
        }
    }

    public void reset() {
        if (!this.isPublishing()) {
            bufferedEvents.clear();
        }
    }

    private boolean isPublishing() {
        return this.publishing;
    }

    private void setPublishing(boolean aFlag) {
        this.publishing = aFlag;
    }

    private boolean hasSubscribers() {
        return this.subscribers() != null;
    }

    private List<DomainEventSubscriber> subscribers() {
        return this.subscribers;
    }

    public void flush(final EventBusManager eventBusManager) {

        List<DomainEventSubscriber> allSubscribers = this.subscribers();

        while (!bufferedEvents.isEmpty()) {
            final DomainEvent nextEvent = bufferedEvents.remove(0);

            for (final DomainEventSubscriber subscriber : allSubscribers) {

                if (subscriber.subscribedTo(nextEvent)) {
                    EXECUTOR.execute(new Runnable() {
                        @Override
                        public void run() {
                        	eventBusManager.initialize();
                            subscriber.handleEvent(nextEvent);
                            eventBusManager.flush();
                        }
                    });
                }
            }
        }
    }
}