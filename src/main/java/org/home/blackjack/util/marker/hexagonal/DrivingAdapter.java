package org.home.blackjack.util.marker.hexagonal;

/**
 * Marker interface for Driving Adapters. They are attached on Driving Ports. See Hexagonal Architecture.
 * 
 * @author Mate
 *
 */
public interface DrivingAdapter<T extends DrivingPort> extends Adapter {

}
