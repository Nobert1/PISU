package dk.dtu.compute.se.pisd.monopoly.mini.model.properties;

import dk.dtu.compute.se.pisd.monopoly.mini.model.Property;
import javafx.scene.effect.Light;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import dk.dtu.compute.se.pisd.monopoly.mini.model.properties.Colors;

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
    private int houses = 0;
    private int houseprice = 1000;
    private int RealestateRent;
    private boolean hotel = false;
    private boolean buildable = false;
    public int rent;
    private Color color;
    private int houserent;

    private static Set<RealEstate> Greyproperties = new HashSet<>();
    private static Set<RealEstate> Redproperties = new HashSet<>();
    private static Set<RealEstate> Greenproperties = new HashSet<>();
    private static Set<RealEstate> Purpleproperties = new HashSet<>();
    private static Set<RealEstate> Lightblueproperties = new HashSet<>();
    private static Set<RealEstate> MagentaProerties = new HashSet<>();
    private static Set<RealEstate> LightredProperties = new HashSet<>();
    private static Set<RealEstate> YellowProperties = new HashSet<>();
    private static Set<RealEstate> WhiteProperties = new HashSet<>();




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

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setBuildable(boolean buildable) {
        this.buildable = buildable;
    }

    public boolean isBuildable() {
        return buildable;
    }

    /**
     * Denne løsning knytter sig til klassen Color og er bare en måde at finde ud af hvad folk ejer i stedet for at iterere til højre og venstre.
     * Kan godt være den mangler arbejde, jegh ar ikke erfaring med Enums og det er derfor ikke helt til at sige om det virker som det skal.
     * @Author Gustav
     * @param estate
     * @return
     */

    public static Set<RealEstate> getcolormap(RealEstate estate) {

            if (estate.getColor().equals(Colors.getcolor(Colors.RED)) ) {
                return Redproperties;
            } else if (estate.getColor().equals(Colors.getcolor(Colors.GREEN))) {
                return Greenproperties;
            } else if (estate.getColor().equals(Colors.getcolor(Colors.LIGHTBLUE))) {
                return Lightblueproperties;
            } else if (estate.getColor().equals(Colors.getcolor(Colors.GREY))) {
                return Greyproperties;
            } else if (estate.getColor().equals(Colors.getcolor(Colors.PURPLE))) {
                return Purpleproperties;
            } else if (estate.getColor().equals(Colors.getcolor(Colors.YELLOW))) {
                return YellowProperties;
            } else if (estate.getColor().equals(Colors.getcolor(Colors.LIGHTRED))) {
                return LightredProperties;
            } else if (estate.getColor().equals(Colors.getcolor(Colors.MAGENTA))) {
                return MagentaProerties;
            } else if (estate.getColor().equals(Colors.getcolor(Colors.WHITE))) {
                return WhiteProperties;
            }
            return null;
        }



    /**
     * Hvordan huslejen skal skrues sammen ved jeg ikke helt endnu, jeg vil gerne
     * have noget dynamisk husleje uden at den skal være hardcoded.
     * Alle tal her er vejledende
     * - s185031 Gustav Emil Nobert
     *
     * @param realEstate
     * @return
     */

    public int Computerent(RealEstate realEstate) {
        //TODO Tilegn den her nogle værdier som faktisk er blevet undersøgt lidt
        checkforbuildable(realEstate);
        if (realEstate.isBuildable() && realEstate.isHotel()) {
            notify();
            return realEstate.getRent() * 20;
        } else if (realEstate.isBuildable() && realEstate.getHouses() != 0) {
            notify();
            return realEstate.getRent() * 4 * realEstate.getHouses();
        } else if (realEstate.isBuildable()){
            notify();
            return realEstate.getRent() * 2;
        }
        notify();
        return realEstate.getRent();
    }



    public static void insertintoColorMap(RealEstate estate) {
            if (estate.getColor().equals(Colors.getcolor(Colors.RED))) {
                Redproperties.add(estate);
            } else if (estate.getColor().equals(Colors.getcolor(Colors.GREEN))) {
                Greenproperties.add(estate);
            } else if (estate.getColor().equals(Colors.getcolor(Colors.LIGHTBLUE))) {
                Lightblueproperties.add(estate);
            } else if (estate.getColor().equals(Colors.getcolor(Colors.GREY))) {
                Greyproperties.add(estate);
            } else if (estate.getColor().equals(Colors.getcolor(Colors.PURPLE))) {
                Purpleproperties.add(estate);
            } else if (estate.getColor().equals(Colors.getcolor(Colors.YELLOW))) {
                YellowProperties.add(estate);
            } else if (estate.getColor().equals(Colors.getcolor(Colors.LIGHTRED))) {
                LightredProperties.add(estate);
            } else if (estate.getColor().equals(Colors.getcolor(Colors.MAGENTA))) {
                MagentaProerties.add(estate);
            } else if (estate.getColor().equals(Colors.getcolor(Colors.WHITE))) {
                WhiteProperties.add(estate);
            }
        }

    public static Set<RealEstate> getGreyproperties() { return Greyproperties; }

    public static Set<RealEstate> getYellowProperties() { return YellowProperties; }

    public static Set<RealEstate> getRedproperties() { return Redproperties; }

    public static Set<RealEstate> getWhiteProperties() { return WhiteProperties; }

    public static Set<RealEstate> getPurpleproperties() { return Purpleproperties; }

    public static Set<RealEstate> getLightblueproperties() { return Lightblueproperties; }

    public static Set<RealEstate> getGreenproperties() { return Greenproperties; }

    public static Set<RealEstate> getLightredProperties() { return LightredProperties; }

    public static void checkforbuildable(RealEstate estate) {
        Set<RealEstate> estateSet = getcolormap(estate);
        int counter = 0;
        for (RealEstate realEstate : estateSet) {
            if (realEstate.getOwner() == estate.getOwner()) {
                counter++;
            }
        }
        if (counter == estateSet.size()) {
            estate.setBuildable(true);
        } else {
            estate.setBuildable(false);
        }
    }
}