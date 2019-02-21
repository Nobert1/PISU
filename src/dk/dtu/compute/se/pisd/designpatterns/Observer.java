package dk.dtu.compute.se.pisd.designpatterns;

/**
 * This is the observer of the observer design pattern roughly following
 * the definition of the GoF.
 * 
 * @author Ekkart Kindler, ekki@dtu.dk
 *
 */
public interface Observer {
	
	/**
	 * The observer's update method, which is called, when the subject
	 * changes. The subject is provided as a parameter in order to
	 * use the same observer object as an observer for many different
	 * subjects.
	 * 
	 * @param subject the subject which changed
	 */
	public void update(Subject subject);

}
