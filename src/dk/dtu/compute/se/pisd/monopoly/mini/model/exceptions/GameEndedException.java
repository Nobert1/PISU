package dk.dtu.compute.se.pisd.monopoly.mini.model.exceptions;

import dk.dtu.compute.se.pisd.monopoly.mini.model.Player;

/**
 * An exception that indicates that the last player before the winner
 * has gone broke which means the game is over.
 *
 */


public class GameEndedException extends Exception {



    public GameEndedException() {
//Is the guin message here or in the catch clause?
        super("Everyone except 1 is broke");

    }


}
