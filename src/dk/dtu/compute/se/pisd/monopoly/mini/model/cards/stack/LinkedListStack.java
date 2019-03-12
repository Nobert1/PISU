package dk.dtu.compute.se.pisd.monopoly.mini.model.cards.stack;

/**
 * A template for implementing a {@see dk.dtu.compute.se.pisd.monopoly.mini.model.cards.stack.Stack}
 * as a (singly) linked list. This is supposed to be implemented by
 * the students as the task of assignment 1 of the course. 
 * 
 * @author Ekkart Kindler, ekki@dtu.dk
 *
 */
public class LinkedListStack implements Stack {

    StackElement top = null;
    int size = 0;

	@Override
	public void clear() {

		// TODO Auto-generated method stub

        top = null;

	}

	@Override
	public Integer pop() {

		// TODO Auto-generated method stub

        if (top != null) {
            int data = top.data;
            top = top.next;
            size--;
            return data;
        } else {
            return null;
        }


	}

	@Override
	public Integer top() {

		// TODO Auto-generated method stub

        if (top == null) {
            return null;
        } else {
            return top.data;
        }
	}

	@Override
	public void push(Integer value) {

		// TODO Auto-generated method stub

        StackElement element = new StackElement();

        element.data = value;

        element.next = top;

        top = element;

        size++;

	}

	@Override
	public int size() {

		// TODO Auto-generated method stub

		return size;

	}

}
