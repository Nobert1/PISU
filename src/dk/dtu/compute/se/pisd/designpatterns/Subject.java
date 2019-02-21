package dk.dtu.compute.se.pisd.designpatterns;

import java.util.HashSet;
import java.util.Set;

/**
 * This is the subject of the observer design pattern roughly following
 * the definition of the GoF.
 * 
 * @author Ekkart Kindler, ekki@dtu.dk
 *
 */
public abstract class Subject {
	
	private Set<Observer> observers = new HashSet<Observer>();
	
	/**
	 * This methods allows an observer to register with the subject
	 * for update notifications when the subject changes.
	 * 
	 * @param observer the observer who registers
	 */
	final public void attach(Observer observer) {
		observers.add(observer);
	}
	
	/**
	 * This methods allows an observer to unregister from the subject
	 * again.
	 * 
	 * @param observer the observer who unregisters
	 */
	final public void detach(Observer observer) {
		observers.remove(observer);
	}
	
	/**
	 * This method must be called from methods of concrete subclasses
	 * of this subject class whenever its state is changed (in a way
	 * relevant for the observer).
	 */
	final protected void notifyChange() {
		for (Observer observer: observers) {
			observer.update(this);
		}
	}

}
