package org.home.blackjack.util.ddd.pattern.domain.model;

import org.home.blackjack.util.marker.hexagonal.DrivingPort;

public interface Repository<K extends ID, V extends AggregateRoot<K>> extends DrivingPort{

	
}
 