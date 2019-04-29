package dk.dtu.compute.se.pisd.monopoly.mini.test;

import dk.dtu.compute.se.pisd.monopoly.mini.model.Player;
import dk.dtu.compute.se.pisd.monopoly.mini.model.Property;

import static org.junit.Assert.*;

public class PlayerTest {

    @org.junit.Test
    public void getName() {

        Player test = new Player();
        test.setName("Per");

        assertEquals("Per", test.getName());
    }

    @org.junit.Test
    public void setName() {

        Player test = new Player();
        test.setName("Per");

        assertEquals("Per", test.getName());

    }

    @org.junit.Test
    public void getColor() {
    }

    @org.junit.Test
    public void setColor() {
    }

    @org.junit.Test
    public void getCurrentPosition() {
    }

    @org.junit.Test
    public void setCurrentPosition() {
    }

    @org.junit.Test
    public void getBalance() {
    }

    @org.junit.Test
    public void setBalance() {
    }

    @org.junit.Test
    public void receiveMoney() {

        Player receiver = new Player();
        receiver.setBalance(0);
        receiver.receiveMoney(1500);

        assertEquals(1500,receiver.getBalance());

    }

    @org.junit.Test
    public void payMoney() {

        Player payer = new Player();
        payer.setBalance(1000);
        payer.payMoney(500);

        assertEquals(500, payer.getBalance());

    }

    @org.junit.Test
    public void getOwnedProperties() {



    }

    @org.junit.Test
    public void setOwnedProperties() {
    }

    @org.junit.Test
    public void addOwnedProperty() {

        Player owner = new Player();
        Property CityHall = new Property();
        owner.addOwnedProperty(CityHall);
        Property owned = owner.getOwnedProperties().iterator().next();

        assertEquals(CityHall, owned);

    }

    @org.junit.Test
    public void removeOwnedProperty() {

        Player owner = new Player();
        Property CityHall = new Property();
        owner.addOwnedProperty(CityHall);
        owner.removeOwnedProperty(CityHall);

        assertEquals(true, owner.getOwnedProperties().isEmpty());

    }

    @org.junit.Test
    public void removeAllProperties() {

        Player owner = new Player();
        Property CityHall = new Property();
        owner.addOwnedProperty(CityHall);
        Property Museum = new Property();
        owner.addOwnedProperty(Museum);
        owner.removeAllProperties();

        assertEquals(true, owner.getOwnedProperties().isEmpty());

    }

    @org.junit.Test
    public void getOwnedCards() {
    }

    @org.junit.Test
    public void setOwnedCards() {
    }

    @org.junit.Test
    public void removeOwnedCard() {
    }

    @org.junit.Test
    public void isBroke() {

        Player broke = new Player();
        broke.setBroke(true);

        assertEquals(true, broke.isBroke());


    }

    @org.junit.Test
    public void setBroke() {
    }

    @org.junit.Test
    public void isInPrison() {

        Player prisoner = new Player();
        prisoner.setInPrison(true);

        assertEquals(true, prisoner.isInPrison());

    }

    @org.junit.Test
    public void setInPrison() {
    }
}