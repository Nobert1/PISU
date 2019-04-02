package dk.dtu.compute.se.pisd.monopoly.mini.model.properties;

import dk.dtu.compute.se.pisd.monopoly.mini.model.Property;

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


    public int Computerent(Utility utility) {
        //Noget med at den skal kunne kende forskel.
        int rent;

        Set<Utility> Utilitylist = new HashSet<Utility>();
        Iterator value = utility.getOwner().getOwnedProperties().iterator();

        while (value.hasNext()) {
            if (value instanceof Utility) {
                Utilitylist.add((Utility) value);
            }

            return super.Computerent(utility);
        }
        return super.Computerent(utility);

    }
}
