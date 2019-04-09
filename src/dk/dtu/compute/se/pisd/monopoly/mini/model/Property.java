package dk.dtu.compute.se.pisd.monopoly.mini.model;

import dk.dtu.compute.se.pisd.monopoly.mini.controller.GameController;
import dk.dtu.compute.se.pisd.monopoly.mini.model.exceptions.PlayerBrokeException;
import dk.dtu.compute.se.pisd.monopoly.mini.model.properties.RealEstate;
import dk.dtu.compute.se.pisd.monopoly.mini.model.properties.Utility;
import dk.dtu.compute.se.pisd.monopoly.mini.view.View;

import java.awt.*;

/**
 * A property which is a space that can be owned by a player.
 * 
 * @author Ekkart Kindler, ekki@dtu.dk
 *
 */
public class Property extends Space {

    private int cost;
    private int rent;
    private boolean owned = false;
    private Player owner;
    private boolean mortgaged = false;
    private Color color;

    //Made a color attribute since i need it for the Jframe. We can just give colors to utilities as well cause it will look good.

    /**
     * Returns the cost of this property.
     *
     * @return the cost of this property
     */
    public int getCost() {
        return cost;
    }

    public void setOwned(boolean owned) {
        this.owned = owned;
    }

    public boolean isOwned() {
        return owned;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Sets the cost of this property.
     *
     * @param cost the new cost of this property
     */
    public void setCost(int cost) {
        this.cost = cost;
        notifyChange();
    }


    /**
     * Returns the rent to be payed for this property.
     *
     * @return the rent for this property
     */
    public int getRent() {
        return rent;
    }

    /**
     * Sets the rent for this property.
     *
     * @param rent the new rent for this property
     */
    public void setRent(int rent) {
        this.rent = rent;
        notifyChange();
    }

    /**
     * Returns the owner of this property. The value is <code>null</code>,
     * if the property currently does not have an owner.
     *
     * @return the owner of the property or <code>null</code>
     */
    public Player getOwner() {
        return owner;
    }

    /**
     * Sets the owner of this property  to the given owner (which can be
     * <code>null</code>).
     *
     * @param player the new owner of the property
     */
    public void setOwner(Player player) {
        this.owner = player;
        notifyChange();
    }

    @Override
    public void doAction(GameController controller, Player player) throws PlayerBrokeException {
        if (owner == null) {
            controller.offerToBuy(this, player);
        } else if (!owner.equals(player) && !this.isMortgaged()) {
            int rent = 0;
            if (this instanceof RealEstate) {
               rent = ((RealEstate) this).Computerent(this);
            } else {
                rent = ((Utility) this).Computerent(this);
            }
            // TODO also check whether the property is mortgaged
            // TODO the computation of the actual rent could be delegated
            //      the subclasses of Property, which can take the specific
            //      individual conditions into account. Note that the
            //      groups of properties (which are not part of the model
            //      yet also need to be taken into account).
            controller.payment(player, rent, this.getOwner());
        }
            
        }



    public void setMortgaged(boolean mortgaged) {
        this.mortgaged = mortgaged;
        if (mortgaged)
            setRent(0);
        }

    public boolean isMortgaged() {
        return mortgaged;
    }

    public int Computerent(Property property) {

        return 1;
    }
}
