package dk.dtu.compute.se.pisd.monopoly.mini;

import dk.dtu.compute.se.pisd.monopoly.mini.controller.GameController;
import dk.dtu.compute.se.pisd.monopoly.mini.database.dal.DALException;
import dk.dtu.compute.se.pisd.monopoly.mini.model.*;
import dk.dtu.compute.se.pisd.monopoly.mini.model.cards.CardMove;
import dk.dtu.compute.se.pisd.monopoly.mini.model.cards.CardReceiveMoneyFromBank;
import dk.dtu.compute.se.pisd.monopoly.mini.model.cards.PayTax;
import dk.dtu.compute.se.pisd.monopoly.mini.model.properties.Colors;
import dk.dtu.compute.se.pisd.monopoly.mini.model.properties.RealEstate;
import dk.dtu.compute.se.pisd.monopoly.mini.model.properties.Utility;

import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Main class for setting up and running a (Mini-)Monoploy game.
 * 
 * @author Ekkart Kindler, ekki@dtu.dk
 *
 */
public class MiniMonopoly {
	
	/**
	 * Creates the initial static situation of a Monopoly game. Note
	 * that the players are not created here, and the chance cards
	 * are not shuffled here.
	 *
	 * @return the initial game board and (not shuffled) deck of chance cards 
	 */
    @SuppressWarnings("Duplicates")
    public static Game createGame() {

        // Create the initial Game set up (note that, in this simple
        // setup, we use only 11 spaces). Note also that this setup
        // could actually be loaded from a file or database instead
        // of creating it programmatically. This will be discussed
        // later in this course.
        Game game = new Game();
        return game;
    }
		public static void createSpaces(Game game) {

//TODO: input mortgage values
		int i = 0;
		int j = 0;

		Space go = new Space();
		go.setName("Go");
		game.addSpace(go);
		
		RealEstate p = new RealEstate();
		p.setName("Rødovrevej");
		p.setCost(1200);
		p.setRent(50);
		p.setMortgageValue(750);
        p.setColor(Colors.getcolor(Colors.LIGHTBLUE));
        RealEstate.insertintoColorMap(p);
        p.setPropertid(i++);
        game.addSpace(p);

		Chance chance = new Chance();
		chance.setName("Chance");
		game.addSpace(chance);
		
		p = new RealEstate();
		p.setName("Hvidovrevej");
		p.setCost(1200);
		p.setRent(50);
		p.setMortgageValue(75);
        p.setColor(Colors.getcolor(Colors.LIGHTBLUE));
        RealEstate.insertintoColorMap(p);
        p.setPropertid(i++);
		game.addSpace(p);
		
		Tax t = new Tax();
		t.setName("Pay tax (10% on Cash)");
		game.addSpace(t);

		Utility s = new Utility();
		s.setName("Øresund");
		s.setCost(4000);
		s.setRent(500);
        s.setColor(Colors.getcolor(Colors.SHIPPINGWHITE));
        Utility.getShippingLine().add(s);
        s.setPropertyid(j++);
		game.addSpace(s);


		p = new RealEstate();
		p.setName("Roskildevej");
		p.setCost(2000);
		p.setRent(100);
        p.setColor(Colors.getcolor(Colors.LIGHTRED));
        RealEstate.insertintoColorMap(p);
        p.setPropertid(i++);
        game.addSpace(p);
		
		chance = new Chance();
		chance.setName("Chance");
		game.addSpace(chance);
		
		p = new RealEstate();
		p.setName("Valby Langgade");
		p.setCost(2000);
		p.setRent(100);
        p.setColor(Colors.getcolor(Colors.LIGHTRED));
        RealEstate.insertintoColorMap(p);
        p.setPropertid(i++);
		game.addSpace(p);
		
		p = new RealEstate();
		p.setName("Allégade");
		p.setCost(2400);
        p.setRent(150);
        p.setColor(Colors.getcolor(Colors.LIGHTRED));
        RealEstate.insertintoColorMap(p);
        p.setPropertid(i++);
		game.addSpace(p);
		
		Space prison = new Space();
		prison.setName("Prison");
		game.addSpace(prison);
		
		p = new RealEstate();
		p.setName("Frederiksberg Allé");
		p.setCost(2800);
		p.setRent(200);
        p.setColor(Colors.getcolor(Colors.GREEN));
        RealEstate.insertintoColorMap(p);
        p.setPropertid(i++);
        game.addSpace(p);
		
		s = new Utility();
		s.setName("Coca-Cola Tapperi");
		s.setCost(3000);
		s.setRent(300);
        s.setColor(Colors.getcolor(Colors.DARKGREEN));
        Utility.getBreweries().add(s);
        s.setPropertyid(j++);
        game.addSpace(s);
		
		p = new RealEstate();
		p.setName("Bülowsvej");
		p.setCost(2800);
		p.setRent(200);
        p.setColor(Colors.getcolor(Colors.GREEN));
        RealEstate.insertintoColorMap(p);
        p.setPropertid(i++);
		game.addSpace(p);
		
		p = new RealEstate();
		p.setName("Gl. Kongevej");
		p.setCost(3200);
		p.setRent(250);
        p.setColor(Colors.getcolor(Colors.GREEN));
        RealEstate.insertintoColorMap(p);
        p.setPropertid(i++);
		game.addSpace(p);

        s = new Utility();
        s.setName("D.F.D.S");
        s.setCost(2000);
        s.setRent(250);
        s.setColor(Colors.getcolor(Colors.SHIPPINGWHITE));
        Utility.getShippingLine().add(s);
        s.setPropertyid(j++);
        game.addSpace(s);

        p = new RealEstate();
        p.setName("Bernstorffsvej");
        p.setCost(3800);
        p.setRent(300);
        p.setColor(Colors.getcolor(Colors.GREY));
        RealEstate.insertintoColorMap(p);
        p.setPropertid(i++);
        game.addSpace(p);

        chance = new Chance();
        chance.setName("Chance");
        game.addSpace(chance);

        p = new RealEstate();
        p.setName("Hellerupvej");
        p.setCost(3800);
        p.setRent(300);
        p.setColor(Colors.getcolor(Colors.GREY));
        RealEstate.insertintoColorMap(p);
        p.setPropertid(i++);
        game.addSpace(p);

        p = new RealEstate();
        p.setName("Strandvejen");
        p.setCost(3800);
        p.setRent(300);
        p.setColor(Colors.getcolor(Colors.GREY));
        RealEstate.insertintoColorMap(p);
        p.setPropertid(i++);
        game.addSpace(p);

        Space helle = new Space();
        helle.setName("helle");
        game.addSpace(helle);

        p = new RealEstate();
        p.setName("Trianglen");
        p.setCost(4000);
        p.setRent(350);
        p.setColor(Colors.getcolor(Colors.RED));
        RealEstate.insertintoColorMap(p);
        p.setPropertid(i++);
        game.addSpace(p);

        chance = new Chance();
        chance.setName("Chance");
        game.addSpace(chance);

        p = new RealEstate();
        p.setName("Østerbrogade");
        p.setCost(4000);
        p.setRent(350);
        p.setColor(Colors.getcolor(Colors.RED));
        RealEstate.insertintoColorMap(p);
        p.setPropertid(i++);
        game.addSpace(p);

        p = new RealEstate();
        p.setName("Grønningen");
        p.setCost(4000);
        p.setRent(350);
        p.setColor(Colors.getcolor(Colors.RED));
        RealEstate.insertintoColorMap(p);
        p.setPropertid(i++);
        game.addSpace(p);

        s = new Utility();
        s.setName("ship");
        s.setCost(2000);
        s.setRent(250);
        s.setColor(Colors.getcolor(Colors.SHIPPINGWHITE));
        s.setPropertyid(j++);
        Utility.getShippingLine().add(s);

        game.addSpace(s);

        p = new RealEstate();
        p.setName("Bredgade");
        p.setCost(4500);
        p.setRent(400);
        p.setColor(Colors.getcolor(Colors.WHITE));
        RealEstate.insertintoColorMap(p);
        p.setPropertid(i++);
        game.addSpace(p);

        p = new RealEstate();
        p.setName("Kgs. Nytrov");
        p.setCost(4500);
        p.setRent(400);
        p.setColor(Colors.getcolor(Colors.WHITE));
        RealEstate.insertintoColorMap(p);
        p.setPropertid(i++);
        game.addSpace(p);

        s = new Utility();
        s.setName("Carlsberg");
        s.setCost(2000);
        s.setRent(250);
        s.setColor(Colors.getcolor(Colors.DARKGREEN));
        Utility.getBreweries().add(s);
        s.setPropertyid(j++);
        game.addSpace(s);

        p = new RealEstate();
        p.setName("Østergade");
        p.setCost(4500);
        p.setRent(400);
        p.setColor(Colors.getcolor(Colors.WHITE));
        RealEstate.insertintoColorMap(p);
        p.setPropertid(i++);
        game.addSpace(p);

        Space gotoprison = new Space();
        gotoprison.setName("Go to prison");
        game.addSpace(gotoprison);

        p = new RealEstate();
        p.setName("Amagertorv");
        p.setCost(4800);
        p.setRent(420);
        p.setColor(Colors.getcolor(Colors.YELLOW));
        RealEstate.insertintoColorMap(p);
        p.setPropertid(i++);
        game.addSpace(p);

        p = new RealEstate();
        p.setName("Vimmelskaftet");
        p.setCost(4800);
        p.setRent(420);
        p.setColor(Colors.getcolor(Colors.YELLOW));
        RealEstate.insertintoColorMap(p);
        p.setPropertid(i++);
        game.addSpace(p);

        chance = new Chance();
        chance.setName("Chance");
        game.addSpace(chance);

        p = new RealEstate();
        p.setName("Nygade");
        p.setCost(4800);
        p.setRent(420);
        p.setColor(Colors.getcolor(Colors.YELLOW));
        RealEstate.insertintoColorMap(p);
        p.setPropertid(i++);
        game.addSpace(p);

        s = new Utility();
        s.setName("Bornholm færgen");
        s.setCost(2000);
        s.setRent(250);
        s.setColor(Colors.getcolor(Colors.SHIPPINGWHITE));
        Utility.getShippingLine().add(s);
        s.setPropertyid(j++);
        game.addSpace(s);

        chance = new Chance();
        chance.setName("Chance");
        game.addSpace(chance);

        p = new RealEstate();
        p.setName("Frederiksberggade");
        p.setCost(5200);
        p.setRent(450);
        p.setColor(Colors.getcolor(Colors.PURPLE));
        RealEstate.insertintoColorMap(p);
        p.setPropertid(i++);
        game.addSpace(p);

        t = new Tax();
        t.setName("extraordinær skat, betal 1000");
        game.addSpace(t);

        p = new RealEstate();
        p.setName("Rådhuspladsen");
        p.setCost(5500);
        p.setRent(500);
        p.setColor(Colors.getcolor(Colors.PURPLE));
        RealEstate.insertintoColorMap(p);
        p.setPropertid(i++);
        game.addSpace(p);



        List<Card> cards = new ArrayList<Card>();
		
		CardMove move = new CardMove();
		move.setTarget(game.getSpaces().get(9));
		move.setText("Move to Allégade!");
		cards.add(move);
		
		PayTax tax = new PayTax();
		tax.setText("Pay 10% income tax!");
		cards.add(tax);
		
		CardReceiveMoneyFromBank b = new CardReceiveMoneyFromBank();
		b.setText("You receive 100$ from the bank.");
		b.setAmount(100);
		cards.add(b);
		game.setCardDeck(cards);

        }


	/**
	 * This method will be called before the game is started to create
	 * the participating players.
	 */
	public static void createPlayers(Game game) {
		// TODO the players should eventually be created interactively or be loaded from a database
        int i = 1;
		Player p = new Player();
		p.setName("Player 1");
		p.setCurrentPosition(game.getSpaces().get(0));
		p.setColor(Color.RED);
		p.setPlayerID(i++);
		game.addPlayer(p);


		p = new Player();
		p.setName("Player 2");
		p.setCurrentPosition(game.getSpaces().get(0));
		p.setColor(Color.YELLOW);
        p.setPlayerID(i++);
		game.addPlayer(p);

		p = new Player();
		p.setName("Player 3");
		p.setCurrentPosition(game.getSpaces().get(0));
		p.setColor(Color.GREEN);
        p.setPlayerID(i++);
		game.addPlayer(p);
	}

	/**
	 * The main method which creates a game, shuffles the chance
	 * cards, creates players, and then starts the game. Note
	 * that, eventually, the game could be loaded from a database.
	 * 
	 * @param args not used
	 */
	public static void main(String[] args) {
		Game game = createGame();
		GameController controller = new GameController(game);
		controller.initializeGUI();
		try {
            createSpaces(game);
		    controller.databaseinteraction();
            game.shuffleCardDeck();
            //createPlayers(game);
            controller.play();
        } catch (DALException e) {
		    e.getMessage();
        }
	}
}
