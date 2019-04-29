package dk.dtu.compute.se.pisd.monopoly.mini.model.cards;

import dk.dtu.compute.se.pisd.monopoly.mini.controller.GameController;
import dk.dtu.compute.se.pisd.monopoly.mini.model.Card;
import dk.dtu.compute.se.pisd.monopoly.mini.model.Player;
import dk.dtu.compute.se.pisd.monopoly.mini.model.Space;
import dk.dtu.compute.se.pisd.monopoly.mini.model.exceptions.PlayerBrokeException;

/**
 * A card that directs the player to a move to a specific space (location) of the game.
 * 
 * @author Ekkart Kindler, ekki@dtu.dk
 * 
 */
public class CardMove extends Card {
	
	private Space target;

	/** 
	 * Returns the target space to which this card directs the player to go.
	 * 
	 * @return the target of the move
	 */
	public Space getTarget() {
		return target;
	}

	/**
	 * Sets the target space of this card.
	 * 
	 * @param target the new target of the move 
	 */
	public void setTarget(Space target) {
		this.target = target;
	}
	
	@Override
	public void doAction(GameController controller, Player player) throws PlayerBrokeException {
		try {
			controller.moveToSpace(player, target);	
		} finally {
			// Make sure that the card is returned to the deck even when
			// an Exception should occur!
			super.doAction(controller, player);
		}
	}
	

	
}
