package dk.dtu.compute.se.pisd.monopoly.mini.model;

import dk.dtu.compute.se.pisd.designpatterns.Subject;
import dk.dtu.compute.se.pisd.monopoly.mini.controller.GameController;
import dk.dtu.compute.se.pisd.monopoly.mini.model.exceptions.PlayerBrokeException;

/**
 * Represents a space (field) of the Monopoly game. The method
 * {@link #doAction(GameController, Player)} implements the
 * action taken when a player arrives on the space. In order
 * to use this part of the model with the MVC-pattern, this
 * class extends the
 * {@link dk.dtu.compute.se.pisd.designpatterns.Subject} of
 * the observer design pattern.
 * 
 * @author Ekkart Kindler, ekki@dtu.dk
 *
 */
public class Space extends Subject {
	
	private String name;
	
	private int index;
	
	/**
	 * Returns the space's name.
	 * 
	 * @return the space's name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the space's name.
	 * 
	 * @param name the name
	 */
	public void setName(String name) {
		this.name = name;
		notifyChange();
	}

	/**
	 * Returns the index of this space in the game.
	 * 
	 * @return the spaces positing in the game
	 */
	public int getIndex() {
		return index;
	}
	
	/**
	 * Sets the index of the space in the game.
	 * 
	 * @param index the index
	 */
	public void setIndex(int index) {
		this.index = index;
		notifyChange();
	}
	
	/**
	 * The action taken when a player arrives on that fields.
	 * 
	 * @param controller the controller in charge of the game
	 * @param player the involved player
	 * @throws PlayerBrokeException when the action results in the player going bankrupt
	 */
	public void doAction(GameController controller, Player player) throws PlayerBrokeException {
		// per default there is no action for a space
	}

}
