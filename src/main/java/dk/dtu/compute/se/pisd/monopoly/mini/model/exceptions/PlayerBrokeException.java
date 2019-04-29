package dk.dtu.compute.se.pisd.monopoly.mini.model.exceptions;

import dk.dtu.compute.se.pisd.monopoly.mini.model.Player;

/**
 * An exception that indicates that a player went broke
 * during the current move.
 * 
 * @author Ekkart Kindler, ekki@dtu.dk
 *
 */
@SuppressWarnings("serial")
public class PlayerBrokeException extends Exception {
	
	private Player player;
	
	/**
	 * Constructor for an exception for a player who went broke.
	 * 
	 * @param player the broke player.
	 */
	public PlayerBrokeException(Player player) {
		super("Player went broke");
		this.player = player;
	}

	/**
	 * Returns the player that went broke.
	 * 
	 * @return the player that went broke
	 */
	Player getPlayer() {
		return player;
	}

}
