package dk.dtu.compute.se.pisd.monopoly.mini.model.properties;

import dk.dtu.compute.se.pisd.monopoly.mini.model.Game;
import dk.dtu.compute.se.pisd.monopoly.mini.model.Property;

import java.awt.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * A specific property, which represents a utility which can
 * not be developed with houses or hotels.
 * 
 * @author Ekkart Kindler, ekki@dtu.dk
 *
 */
public class Utility extends Property {


    // TODO to be implemented

    private static Set<Utility> ShippingLine = new HashSet<Utility>();
    private static Set<Utility> Breweries = new HashSet<Utility>();
    private java.awt.Color color;
    private int propertyid;

    public int getPropertyid() {
        return propertyid;
    }

    public void setPropertyid(int propertyid) {
        this.propertyid = propertyid;
    }

    public int Computerent(Utility utility) {
        //Noget med at den skal kunne kende forskel. Den her løsning er nok ikke 10/10
        //Burde nok i virkeligheden bare være objekter af to forskellige typer.

        int rent = 250;
        if (utility.getCost() > 1999) {

            for (Utility ship : ShippingLine) {
                if (ship.getOwner() == utility.getOwner() && !ship.getName().equals(utility.getName())) {
                    rent = rent * 2;
                }
            } } else if (utility.getCost() < 1999) {
            rent = 4;
                for (Utility brewery : Breweries) {
                    if (brewery.getOwner() == utility.getOwner() && !brewery.getName().equals(utility.getName())) {
                        rent = 10;
                    }
                }
                //Hvordan får vi fat i terningernes værdi uden at bryde model view controller?
                rent = rent;
            }
            return rent;


    }

    public static Set<Utility> getBreweries() {
        return Breweries;
    }

    public static Set<Utility> getShippingLine() {
        return ShippingLine;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}
