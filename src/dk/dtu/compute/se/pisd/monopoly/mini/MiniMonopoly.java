package dk.dtu.compute.se.pisd.monopoly.mini;

import dk.dtu.compute.se.pisd.monopoly.mini.controller.GameController;
import dk.dtu.compute.se.pisd.monopoly.mini.model.*;
import dk.dtu.compute.se.pisd.monopoly.mini.model.cards.CardMove;
import dk.dtu.compute.se.pisd.monopoly.mini.model.cards.CardReceiveMoneyFromBank;
import dk.dtu.compute.se.pisd.monopoly.mini.model.cards.PayTax;
import dk.dtu.compute.se.pisd.monopoly.mini.model.exceptions.GameEndedException;
import dk.dtu.compute.se.pisd.monopoly.mini.model.properties.RealEstate;
import dk.dtu.compute.se.pisd.monopoly.mini.model.properties.Utility;

import java.awt.*;
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
	public static Game createGame() {

		// Create the initial Game set up (note that, in this simple
		// setup, we use only 11 spaces). Note also that this setup
		// could actually be loaded from a file or database instead
		// of creating it programmatically. This will be discussed
		// later in this course.
		Game game = new Game();
		
		Space go = new Space();
		go.setName("Go");
		game.addSpace(go);
		
		Property p = new RealEstate();
		p.setName("Rødovrevej");
		p.setCost(1200);
		p.setRent(50);
		game.addSpace(p);
		
		Chance chance = new Chance();
		chance.setName("Chance");
		game.addSpace(chance);
		
		p = new RealEstate();
		p.setName("Hvidovrevej");
		p.setCost(1200);
		p.setRent(50);
		game.addSpace(p);
		
		Tax t = new Tax();
		t.setName("Pay tax (10% on Cash)");
		game.addSpace(t);

		Utility s = new Utility();
		s.setName("Øresund");
		s.setCost(4000);
		s.setRent(500);
		game.addSpace(s);

		p = new RealEstate();
		p.setName("Roskildevej");
		p.setCost(2000);
		p.setRent(100);
		game.addSpace(p);
		
		chance = new Chance();
		chance.setName("Chance");
		game.addSpace(chance);
		
		p = new RealEstate();
		p.setName("Valby Langgade");
		p.setCost(2000);
		p.setRent(100);
		game.addSpace(p);
		
		p = new RealEstate();
		p.setName("Allégade");
		p.setCost(2400);
		p.setRent(150);
		game.addSpace(p);
		
		Space prison = new Space();
		prison.setName("Prison");
		game.addSpace(prison);
		
		p = new RealEstate();
		p.setName("Frederiksberg Allé");
		p.setCost(2800);
		p.setRent(200);
		game.addSpace(p);
		
		p = new RealEstate();
		p.setName("Coca-Cola Tapperi");
		p.setCost(3000);
		p.setRent(300);
		game.addSpace(p);
		
		p = new RealEstate();
		p.setName("Bülowsvej");
		p.setCost(2800);
		p.setRent(200);
		game.addSpace(p);
		
		p = new RealEstate();
		p.setName("Gl. Kongevej");
		p.setCost(3200);
		p.setRent(250);
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

		return game;
	}

	/**
	 * This method will be called before the game is started to create
	 * the participating players.
	 */
	public static void createPlayers(Game game) {
		// TODO the players should eventually be created interactively or be loaded from a database
		Player p = new Player();
		p.setName("Player 1");
		p.setCurrentPosition(game.getSpaces().get(0));
		p.setColor(Color.RED);
		game.addPlayer(p);

		p = new Player();
		p.setName("Player 2");
		p.setCurrentPosition(game.getSpaces().get(0));
		p.setColor(Color.YELLOW);
		game.addPlayer(p);

		p = new Player();
		p.setName("Player 3");
		p.setCurrentPosition(game.getSpaces().get(0));
		p.setColor(Color.GREEN);
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
		game.shuffleCardDeck();

		createPlayers(game);

		GameController controller = new GameController(game);
		controller.initializeGUI();
	try {
		controller.play();
	} catch (GameEndedException e){
		//Should there be made a GetGUI in controller so there can be shown a message here or in the exception class?

	}
	}

}
