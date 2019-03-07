package dk.dtu.compute.se.pisd.stack;
import java.lang.System;

import java.lang.reflect.Array;

/**
 * A template for implementing a {@see dk.dtu.compute.se.pisd.stack.Stack}
 * by using arrays. This is supposed to be implemented by
 * the students as a task of assignment 1 of the course. Note that
 * since Java arrays cannot be extended dynamically, the array must
 * be replaced at some points by a larger array.
 * 
 * @author Ekkart Kindler, ekki@dtu.dk
 *
 */
public class ArrayStack implements Stack {

	int size = 10;
	int arr[] = new int[size];
	int top = -1;

	@Override
	public void clear() {
		// TODO Auto-generated method stub

		this.arr = new int[size];
		this.top = -1;

	}

	@Override
	public Integer pop() {
		// TODO Auto-generated method stub

		if (!isEmpty()) {
			int returnedTop = top;
			top--;
			return arr[returnedTop];

		} else {
			return null;
		}
	}

	@Override
	public Integer top() {
		// TODO Auto-generated method stub
		if (top != -1) {
			return arr[top];
		} else {
			return null;
		}
	}

	@Override
	public void push(Integer value) {
		// TODO Auto-generated method stub

		if (top < size-1) {
		    ++top;
			arr[top] = value;
		} else {
			int[] arr2 = new int[size+10];
			System.arraycopy(arr, 0, arr2, 0, arr.length);
			arr = arr2;
			size = size+10;
            ++top;
            arr[top] = value;
		}
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub

		return top+1;
	}

}
