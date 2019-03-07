package dk.dtu.compute.se.pisd.monopoly.mini.model.cards.stack;
import java.lang.System;

import java.lang.reflect.Array;

/**
 * A template for implementing a {@see dk.dtu.compute.se.pisd.monopoly.mini.model.cards.stack.Stack}
 * by using arrays. This is supposed to be implemented by
 * the students as a task of assignment 1 of the course. Note that
 * since Java arrays cannot be extended dynamically, the array must
 * be replaced at some points by a larger array.
 * 
 * @author Ekkart Kindler, ekki@dtu.dk
 *
 */
public class ArrayStack implements Stack {

	//opretter et nyt array med størrelsen 10 og top = -1.
	int size = 10;
	int arr[] = new int[size];
	int top = -1;

	@Override
	public void clear() {
		//for at slette opretter vi et nyt array og sætter top = -1 igen.

		this.arr = new int[size];
		this.top = -1;

	}

	@Override
	public Integer pop() {
		//For at poppe opretter vi en temp top, flytter toppen nedad og returnerer temp værdien.
		//Der poppes kun hvis stakken ikke er tom.

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
		//For at se toppen retunerer vi denne værdi med mindre top = -1 (tom stak).

		if (top != -1) {
			return arr[top];
		} else {
			return null;
		}
	}

	@Override
	public void push(Integer value) {
		//For at pushe flytter vi toppen en op og sætter denne array plads lig med value.
		//Hvis toppen er én mindre end arrayets størrelse oprettes
		// et nyt array, data kopieres over og slutteligt ligges 'value' ind i det nye array.

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
		//Stakken (IKKE ARRAYETS) størrelse er top + 1 (da vi startede med top = -1).

		return top+1;
	}

}
