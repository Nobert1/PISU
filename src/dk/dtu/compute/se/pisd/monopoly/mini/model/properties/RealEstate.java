package dk.dtu.compute.se.pisd.monopoly.mini.model.properties;

import dk.dtu.compute.se.pisd.monopoly.mini.model.Property;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * A specific property, which represents real estate on which houses
 * and hotels can be built. Note that this class does not have details
 * yet and needs to be implemented.
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 *
 */


/**
 * Method author s185031 - Gustav Emil Nobert
 * Udvid klassen for grund (RealEstate) med private attributer og getter- og setter-metoder som
 * tilgår og ændrer antal byggede huse på en grund. Sørg for at metoden notifyChange() bliver kaldt,
 * så snart der er en relevant ændring i grundens attributer.
 */
public class RealEstate extends Property {
    private int houses;
    private int houseprice;
    private int RealestateRent;
    private boolean hotel = false;
    public int rent;
    private Color color;
    private int houserent;


    // TODO to be implemented


    public void setHouses(int houses) {
        this.houses = houses;
        notifyChange();
    }

    public int getHotelRent(int hotelrent) {
        return hotelrent;
    }

    public int getHouses() {
        return houses;
    }

    public int getHouserent() {
        return houserent;
    }

    public void setHouserent(int houserent) {
        this.houserent = houserent;
    }

    @Override
    public void setRent(int rent) {
        this.rent = rent;
    }

    @Override
    public int getRent() {
        return rent;
    }

    public int getHouseprice() {
        return houseprice;
    }

    public void setHouseprice(int houseprice) {
        this.houseprice = houseprice;
        notifyChange();
    }

    public int getRealestateRent() {
        return RealestateRent;
    }

    public void setRealestateRent(int realestateRent) {
        this.RealestateRent = realestateRent;
        notifyChange();
    }

    public void setHotel(boolean hotel) {
        this.hotel = hotel;
        notifyChange();
    }

    public boolean isHotel() {
        return hotel;
    }

    @Override
    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }



    /**
     * Hvordan huslejen skal skrues sammen ved jeg ikke helt endnu, jeg vil gerne
     * have noget dynamisk husleje uden at den skal være hardcoded.
     * Alle tal her er vejledende
     * - s185031 Gustav Emil Nobert
     * @param realEstate
     * @return
     */

    public int Computerent(RealEstate realEstate) {

        //Udregner dobbelt husleje, kræver dog at de får sat en farve men det gøres i konstruktøren.
        if (realEstate.getHouses() == 0) {
            Set<RealEstate> realestatelist = new HashSet<RealEstate>();
            Iterator value = realEstate.getOwner().getOwnedProperties().iterator();

            while (value.hasNext()) {
                if (value.getClass().equals(RealEstate.class))
                    realestatelist.add((RealEstate) value);
            }
            int counter = 0;
            while (realestatelist.iterator().hasNext())
                if (realestatelist.iterator().next().getColor().equals(realEstate.getColor()))
                    counter++;
            if (counter == 3)
                return realEstate.getRent() * 2;
            else
                return realEstate.getRent();
        }
        if (realEstate.isHotel()) {
            return realEstate.getRent() * 10;
        }
        notify();
        return realEstate.getHouses() * realEstate.getHouserent();
    }
}