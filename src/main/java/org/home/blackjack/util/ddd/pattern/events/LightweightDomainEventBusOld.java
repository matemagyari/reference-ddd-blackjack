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

package org.home.blackjack.util.ddd.pattern.events;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class LightweightDomainEventBusOld implements DomainEventPublisher, SubscribableEventBus {

	private static final ThreadLocal<LightweightDomainEventBusOld> instance = new ThreadLocal<LightweightDomainEventBusOld>() {
		protected LightweightDomainEventBusOld initialValue() {
			return new LightweightDomainEventBusOld();
		}
	};

	private boolean publishing;

	@SuppressWarnings("rawtypes")
	private List subscribers;

	public static LightweightDomainEventBusOld domainEventPublisherInstance() {
		return instance.get();
	}

	public static LightweightDomainEventBusOld subscribableEventBusInstance() {
		return instance.get();
	}

	public <T extends DomainEvent> void publish(final T aDomainEvent) {
		if (!this.isPublishing() && this.hasSubscribers()) {

			try {
				this.setPublishing(true);
				process(aDomainEvent);
			} finally {
				this.setPublishing(false);
			}
		}
	}

	public void publishAll(Collection<DomainEvent> aDomainEvents) {
		for (DomainEvent domainEvent : aDomainEvents) {
			this.publish(domainEvent);
		}
	}

	public void reset() {
		if (!this.isPublishing()) {
			this.setSubscribers(null);
		}
	}

	@SuppressWarnings("unchecked")
	public <T extends DomainEvent> void register(DomainEventSubscriber<T> aSubscriber) {
		if (!this.isPublishing()) {
			this.ensureSubscribersList();

			this.subscribers().add(aSubscriber);
		}
	}

	private LightweightDomainEventBusOld() {
		super();

		this.setPublishing(false);
		this.ensureSubscribersList();
	}

	@SuppressWarnings("rawtypes")
	private void ensureSubscribersList() {
		if (!this.hasSubscribers()) {
			this.setSubscribers(new ArrayList());
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

	@SuppressWarnings("rawtypes")
	private List subscribers() {
		return this.subscribers;
	}

	@SuppressWarnings("rawtypes")
	private void setSubscribers(List aSubscriberList) {
		this.subscribers = aSubscriberList;
	}

	private void process(final DomainEvent nextEvent) {

		@SuppressWarnings("unchecked")
		List<DomainEventSubscriber> allSubscribers = this.subscribers();

		for (final DomainEventSubscriber subscriber : allSubscribers) {
			if (subscriber.subscribedTo(nextEvent)) {
				subscriber.handleEvent(nextEvent);
			}
		}
	}

	@Override
	public void flush() {
		// TODO Auto-generated method stub
		
	}
}
