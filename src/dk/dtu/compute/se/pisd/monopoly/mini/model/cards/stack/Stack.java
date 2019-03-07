package dk.dtu.compute.se.pisd.monopoly.mini.model.cards.stack;


/**
 * A stack of {@see java.lang.Integer} values. An arbitrary number
 * of values can be added (pushed) to the stack. The values can be obtained
 * (popped) from the stack in a last in first out fashion. Note that we use
 * the class {@see java.lang.Integer} here instead of a data type
 * for two reasons: first, we will extend this class later for defining
 * stacks for other types than integers; second, using the class
 * {@see java.lang.Integer} allows us to return null as a result,
 * in case of errors (note that this will be improved later by using
 * exception handling).
 * 
 * @author Ekkart Kindler, ekki@dtu.dk
 */
public interface Stack {
	
	/**
	 * Removes all elements from the stack. The stack will be empty after the
	 * call returns. 
	 */
	void clear();

	/**
	 * Removes the top element from the stack and returns the value of that
	 * element. If the stack is empty, the stack is not changed, and the
	 * call returns <code>null</code>.
	 * 
	 * @return the value of the top element of the stack
	 */
	Integer pop();
	
	/**
	 * Returns the value of the top element of the stack. The stack is not changed.
	 * If the stack is empty, the call returns <code>null</code>.
	 * 
	 * @return the value of the top element of the stack
	 */
	Integer top();
	
	/**
	 * Adds the specified value as the top element of the stack.
	 * 
	 * @param value the value added to the stack
	 */
	void push(Integer value);
	
	/**
	 * Returns <code>true</code> if the stack does not contain any elements.
	 * 
	 * @return <code>true</code> if the stack contains no elements
	 */
	default boolean isEmpty() {
		// Note that default method definition only works with Java 8 or higher
		return size() == 0;
	}
	
	/**
	 * Returns the number of elements on the stack. This is called the size
	 * of the stack. 
	 * 
	 * @return the current size of the stack
	 */
	int size();

}
