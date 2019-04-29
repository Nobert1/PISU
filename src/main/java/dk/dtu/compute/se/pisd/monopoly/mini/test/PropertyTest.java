package dk.dtu.compute.se.pisd.monopoly.mini.test;

import com.sun.xml.internal.bind.v2.TODO;
import dk.dtu.compute.se.pisd.monopoly.mini.model.Player;
import dk.dtu.compute.se.pisd.monopoly.mini.model.Property;
import org.junit.Test;
import static org.junit.Assert.*;

public class PropertyTest {

    @Test
    public void getCost() {

        Property test = new Property();
        test.setCost(500);

        assertEquals(500, test.getCost());

    }

    @Test
    public void setCost() {
    }

    @Test
    public void getRent() {
    }

    @Test
    public void setRent() {
    }

    @Test
    public void getOwner() {

        Property test = new Property();
        test.setCost(500);
        Player owner = new Player();
        test.setOwner(owner);

        assertEquals(owner, test.getOwner());

    }

    @Test
    public void setOwner() {
    }

    @Test
    public void doAction() {

        //TODO To be implemented

    }
}