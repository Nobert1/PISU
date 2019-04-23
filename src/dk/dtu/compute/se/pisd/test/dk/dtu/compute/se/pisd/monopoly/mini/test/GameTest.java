package dk.dtu.compute.se.pisd.monopoly.mini.test;

import dk.dtu.compute.se.pisd.monopoly.mini.model.Game;
import dk.dtu.compute.se.pisd.monopoly.mini.model.Player;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.Assert.*;

public class GameTest {

    @Test
    public void getSpaces() {
    }

    @Test
    public void setSpaces() {
    }

    @Test
    public void addSpace() {
    }

    @Test
    public void getCardDeck() {
    }

    @Test
    public void drawCardFromDeck() {
    }

    @Test
    public void returnCardToDeck() {
    }

    @Test
    public void setCardDeck() {
    }

    @Test
    public void shuffleCardDeck() {
    }

    @Test
    public void getPlayers() {

        Game spil = new Game();
        Player spiller1 = new Player();
        Player spiller2 = new Player();
        Player spiller3 = new Player();
        spil.addPlayer(spiller1);
        spil.addPlayer(spiller2);
        spil.addPlayer(spiller3);

        ArrayList testarr = new ArrayList();
        testarr.add(spiller1); testarr.add(spiller2); testarr.add(spiller3);

        assertEquals(testarr, spil.getPlayers());
    }

    @Test
    public void setPlayers() {
    }

    @Test
    public void addPlayer() {

        Game spil = new Game();
        Player spiller1 = new Player();
        spil.addPlayer(spiller1);

        assertEquals(spiller1, spil.getPlayers().iterator().next());

    }

    @Test
    public void getCurrentPlayer() {

        Game spil = new Game();
        Player spiller1 = new Player();
        Player spiller2 = new Player();
        Player spiller3 = new Player();
        spil.addPlayer(spiller1);
        spil.addPlayer(spiller2);
        spil.addPlayer(spiller3);

        assertEquals(spiller1, spil.getCurrentPlayer());

        spil.setCurrentPlayer(spiller2);

        assertEquals(spiller2, spil.getCurrentPlayer());

    }

    @Test
    public void setCurrentPlayer() {
    }
}