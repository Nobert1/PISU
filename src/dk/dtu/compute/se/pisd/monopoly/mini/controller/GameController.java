package dk.dtu.compute.se.pisd.monopoly.mini.controller;

import dk.dtu.compute.se.pisd.monopoly.mini.database.dal.DALException;
import dk.dtu.compute.se.pisd.monopoly.mini.database.dal.GameDAO;
import dk.dtu.compute.se.pisd.monopoly.mini.model.*;
import dk.dtu.compute.se.pisd.monopoly.mini.model.exceptions.PlayerBrokeException;
import dk.dtu.compute.se.pisd.monopoly.mini.model.properties.Colors;
import dk.dtu.compute.se.pisd.monopoly.mini.model.properties.RealEstate;
import dk.dtu.compute.se.pisd.monopoly.mini.view.PlayerPanel;
import dk.dtu.compute.se.pisd.monopoly.mini.view.View;
import gui_main.GUI;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The overall controller of a Monopoly game. It provides access
 * to all basic actions and activities for the game. All other
 * activities of the game, should be implemented by referring
 * to the basic actions and activities in this class.
 * 
 * Note that this controller is far from being finished and many
 * things could be done in a much nicer and cleaner way! But, it
 * shows the general idea of how the model, view (GUI), and the
 * controller could work with each other, and how different parts
 * of the game's activities can be separated from each other, so
 * that different parts can be added and extended independently
 * from each other.
 * 
 * For fully implementing the game, it will probably be necessary
 * to add more of these basic actions in this class.
 * 
 * The <code>doAction()</code> methods of the
 * {@link dk.dtu.compute.se.pisd.monopoly.mini.model.Space} and
 * the {@link dk.dtu.compute.se.pisd.monopoly.mini.model.Card}
 * can be implemented based on the basic actions and activities
 * of this game controller.
 * 
 * @author Ekkart Kindler, ekki@dtu.dk
 *
 */
public class GameController {

	private Game game;

	private GUI gui;

	private View view;

	private boolean disposed = false;

	private PlayerPanel playerpanel;

	private int Diecount;

	private GameDAO database;

	/**
	 * General TODO - find ud af hvorfor fanden hustingen ikke virker som den skal, optimer panels så den ikke laver et panel for hver ejendom.
	 * Kig på database når Alex pusher, snak med nogen om den smarteste måde at få fat i terningernes værdi på, det er vidst nok imod vmc at hente terningens værdi fra gamecontroller.
	 * Chancekort skal udvides. Payment from player skal også gennemtestest. Der skal skrives test cases.
	 */

	/**
	 * Constructor for a controller of a game.
	 *
	 * @param game the game
	 */
	public GameController(Game game) {
		super();
		this.game = game;
		gui = new GUI();
		database = new GameDAO(game);
	}

	/**
	 * This method will initialize the GUI. It should be called after
	 * the players of the game are created. As of now, the initialization
	 * assumes that the spaces of the game fit to the fields of the GUI;
	 * this could eventually be changed, by creating the GUI fields
	 * based on the underlying game's spaces (fields).
	 */
	public void initializeGUI() {
		this.view = new View(game, gui, playerpanel);
	}

	/**
	 * The main method to start the game. The game is started with the
	 * current player of the game; this makes it possible to resume a
	 * game at any point.
	 */

	public void databaseinteraction () {
	    String selection = gui.getUserSelection("What you wanna do ","load game", "create game");
	    if (selection.equals("load game")) {
	        int gameId = Integer.valueOf(gui.getUserButtonPressed("what game would you like to load", database.generategameIDs()));
            try {
                database.getGame(gameId);
                //view.loadplayers();
                play();
            } catch (DALException e) {
                e.printStackTrace();
            }
        }
    }

	public void play() throws DALException {
		List<Player> players = game.getPlayers();
		Player c = game.getCurrentPlayer();

		int current = 0;
		for (int i = 0; i < players.size(); i++) {
			Player p = players.get(i);
			if (c.equals(p)) {
				current = i;
			}
		}

		boolean terminated = false;
		while (!terminated) {
			Player player = players.get(current);
			if (!player.isBroke()) {
				try {
					this.makeMove(player);
				} catch (PlayerBrokeException e) {
					// We could react to the player having gone broke
				}
			}

			// Check whether we have a winner
			Player winner = null;
			int countActive = 0;
			for (Player p : players) {
				if (!p.isBroke()) {
					countActive++;
					winner = p;
				}
			}
			if (countActive == 1) {
				gui.showMessage(
						"Player " + winner.getName() +
								" has won with " + winner.getBalance() + "$.");
				break;
			} else if (countActive < 1) {
				// This can actually happen in very rare conditions and only
				// if the last player makes a stupid mistake (like buying something
				// in an auction in the same round when the last but one player went
				// bankrupt)
				gui.showMessage(
						"All players are broke.");
				break;

			}

			Offerhouses(players);


			// TODO offer all players the options to trade etc.

			current = (current + 1) % players.size();
			game.setCurrentPlayer(players.get(current));
			if (current == 0) {
				String selection = gui.getUserSelection(
						"A round is finished. Do you want to continue the game?",
						"yes",
						"no");
				if (selection.equals("no")) {
					database.savegame();
					gui.showMessage("game saved");
					terminated = true;
				}
			}

		}
		dispose();
	}


	/**
	 * This method implements a activity of a single move of the given player.
	 * It throws a {@link dk.dtu.compute.se.pisd.monopoly.mini.model.exceptions.PlayerBrokeException}
	 * if the player goes broke in this move. Note that this is still a very
	 * basic implementation of the move of a player; many aspects are still
	 * missing.
	 *
	 * @param player the player making the move
	 * @throws PlayerBrokeException if the player goes broke during the move
	 */
	public void makeMove(Player player) throws PlayerBrokeException {
		boolean castDouble;
		int doublesCount = 0;
		do {
			// TODO right now the dice are limited to the numbers 1, 2 and 3
			// for making the game faster. Eventually, this should be set
			// to 1 - 6 again (to this end, the constants 3.0 below should
			// be set to 6.0 again.
			int die1 = (int) (1 + 6 * Math.random());
			int die2 = (int) (1 + 6 * Math.random());
			setDiecount(die1, die2);
			castDouble = (die1 == die2);
			gui.setDice(die1, die2);

			if (player.isInPrison()) {
				String choice = gui.getUserSelection("Would you like to pay your way out of prison?", "yes", "no");
				if (choice.equals("yes")) {
					player.setInPrison(false);
					player.setBalance(player.getBalance() - 500);
				}
				//Needs testing, does it cost 500? - Gustav

			}
			if (player.isInPrison() && castDouble) {
				player.setInPrison(false);
				gui.showMessage("Player " + player.getName() + " leaves prison now since he cast a double!");
			} else if (player.isInPrison()) {
				gui.showMessage("Player " + player.getName() + " stays in prison since he did not cast a double!");
			}
			// TODO note that the player could also pay to get out of prison,
			//      which is not yet implemented
			if (castDouble) {
				doublesCount++;
				if (doublesCount > 2) {
					gui.showMessage("Player " + player.getName() + " has cast the third double and goes to jail!");
					gotoJail(player);
					return;
				}
			}
			if (!player.isInPrison()) {
				// make the actual move by computing the new position and then
				// executing the action moving the player to that space
				int pos = player.getCurrentPosition().getIndex();
				List<Space> spaces = game.getSpaces();
				int newPos = (pos + die1 + die2) % spaces.size();
				Space space = spaces.get(newPos);
				moveToSpace(player, space);
				if (castDouble) {
					gui.showMessage("Player " + player.getName() + " cast a double and makes another move.");
				}
			}
		} while (castDouble);
	}

	/**
	 * This method implements the activity of moving the player to the new position,
	 * including all actions associated with moving the player to the new position.
	 *
	 * @param player the moved player
	 * @param space  the space to which the player moves
	 * @throws PlayerBrokeException when the player goes broke doing the action on that space
	 */
	public void moveToSpace(Player player, Space space) throws PlayerBrokeException {
		int posOld = player.getCurrentPosition().getIndex();
		player.setCurrentPosition(space);

		if (posOld > player.getCurrentPosition().getIndex()) {
			// Note that this assumes that the game has more than 12 spaces here!
			// TODO: the amount of 2000$ should not be a fixed constant here (could also
			//       be configured in the Game class.
			gui.showMessage("Player " + player.getName() + " receives " + game.getPassstartbonus() + " for passing Go!");
			this.paymentFromBank(player, game.getPassstartbonus());
		}
		gui.showMessage("Player " + player.getName() + " arrives at " + space.getIndex() + ": " + space.getName() + ".");

		// Execute the action associated with the respective space. Note
		// that this is delegated to the field, which implements this action
		space.doAction(this, player);
	}

	/**
	 * The method implements the action of a player going directly to jail.
	 *
	 * @param player the player going to jail
	 */
	public void gotoJail(Player player) {
		// Field #10 is in the default game board of Monopoly the field
		// representing the prison.
		// TODO the 10 should not be hard coded
		player.setCurrentPosition(game.getSpaces().get(10));
		player.setInPrison(true);
	}

	/**
	 * The method implementing the activity of taking a chance card.
	 *
	 * @param player the player taking a chance card
	 * @throws PlayerBrokeException if the player goes broke by this activity
	 */
	public void takeChanceCard(Player player) throws PlayerBrokeException {
		Card card = game.drawCardFromDeck();
		gui.displayChanceCard(card.getText());
		gui.showMessage("Player " + player.getName() + " draws a chance card.");

		try {
			card.doAction(this, player);
		} finally {
			gui.displayChanceCard("done");
		}
	}

	/**
	 * This method implements the action returning a drawn card or a card keep with
	 * the player for some time back to the bottom of the card deck.
	 *
	 * @param card returned card
	 */
	public void returnChanceCardToDeck(Card card) {
		game.returnCardToDeck(card);
	}

	/**
	 * This method implements the activity where a player can obtain
	 * cash by selling houses back to the bank, by mortgaging own properties,
	 * or by selling properties to other players. This method is called, whenever
	 * the player does not have enough cash available for an action. If
	 * the player does not manage to free at least the given amount of money,
	 * the player will be broke; this is to help the player make the right
	 * choices for freeing enough money.
	 * <p>
	 *     Det her er lidt noget lort TODO få det her reviewet.
	 * - method author s185031 - Gustav Emil Nobert
	 *
	 * @param player the player, @param amount the amount the player should have available after the act
	 */

	public void offertosellhouses(Player player) {

		String selection = gui.getUserSelection(player.getName() + "Do you want to sell any houses? The property can't be sold if the strip contains houses", "yes", "no");
		if (selection.equals("yes")) {
			ArrayList<RealEstate> estatelist = new ArrayList<>();
			for (Property property : player.getOwnedProperties()) {
				if (property instanceof RealEstate) {
					if (((RealEstate) property).getHouses() > 0 || ((RealEstate) property).isHotel() == true) {
						estatelist.add((RealEstate) property);
					} }
					String[] estatebuttons = new String[estatelist.size() + 1];
					estatebuttons[estatebuttons.length - 1] = "back";
					for (int i = 0; i < estatelist.size(); i++) {
						estatebuttons[i] = estatelist.get(i).getName();
					}
					do {
						//Må være en smartere løsning, har jo allerede fundet det objekt der skal findes
						RealEstate houseestate = null;
						selection = gui.getUserButtonPressed(player.getName() + " Where do you wanna sell", estatebuttons);
						for (RealEstate estate : estatelist) {
							if (selection.equals(estate.getName()))
								houseestate = estate;
						}
						int housecount;
						try {
							if (houseestate.isHotel()) {
								housecount = 4;
							} else {
								housecount = houseestate.getHouses();
							}
						} catch (NullPointerException e) {
							gui.showMessage("the estate you clicked didn't have any houses");
							break;
						}
						//input validering
						Pattern housepattern = Pattern.compile("\b(0|" + housecount + ")\b]");
						boolean isnum = false;
						int houseselection;
						do {
							houseselection = gui.getUserInteger(player.getName() + " How many would you like to sell? Type 4 to sell your hotel");
							Matcher matcher = housepattern.matcher(String.valueOf(houseselection));
							if (matcher.find()) {
								isnum = true;
							} else {
								gui.showMessage("invalid input, try agian");
							}
						} while (!isnum);

						paymentFromBank(player, 500 * housecount);
						if (houseestate.isHotel()) {
							houseestate.setHotel(false);
							houseestate.setHouses(houseestate.getHouses() - housecount + 1);
						} else {
							houseestate.setHouses(houseestate.getHouses() - housecount);
						}

					} while (!selection.equals("back"));
				}
			}
		}

	/**
	 * I need a method here, just not sure yet how to do it. Could also be a boolean status, probably easier to work with
	 * Gustav Rmil Nobert
	 *
	 * @param property
	 */
	public void mortgageproperty(Property property) {
		paymentFromBank(property.getOwner(), property.getCost() / 2);
		property.setMortgaged(true);
	}

	public void mortgage(Player player, String[] buttons) {
			//Igen jeg har jo fundet navnet, burde være lige til bare at finde den sidste.
			String button = gui.getUserButtonPressed(player.getName() + " What would you like to mortgage?", buttons);

			for (Property property : player.getOwnedProperties()) {
				if (property.getName().equals(button)) {
					mortgageproperty(property);
					break;
				}
			}
	}

	/**
	 * Gustav Emil Nobert
	 * i have made a few methods to back this up, still needs work.
	 *
	 * @param player
	 * @param amount
	 * @throws PlayerBrokeException
	 */


	public void obtainCash(Player player, int amount) throws PlayerBrokeException {
		// TODO implement
		String button = "";
		for (Player bidder : game.getPlayers()) {
			if (bidder != player) {
				//Er dette det rigtige sted at tilbyde at sælge?
				// Kommer exit til at stå det rigtige sted her?
				offertosellhouses(player);
				Set<Property> ownedProperties = player.getOwnedProperties();
				for (Property property : ownedProperties) {
					if (property instanceof RealEstate) {
						if (((RealEstate) property).getHouses() != 0 || ((RealEstate) property).isHotel()) {
							ownedProperties.remove(property);
						}
					}
				}
				String[] buttons = new String[ownedProperties.size() + 1];
				int increm = 0;
				for (Property property : ownedProperties) {
					buttons[increm] = property.getName();
					increm++;
				}
				buttons[buttons.length - 1] = "no";
                String selection = gui.getUserSelection("Would you like to mortgage properties?", "yes", "no");
                if (selection.equals("yes"))
					mortgage(player, buttons);

				gui.showMessage("then we go to the bidding round!");
				do {
					//Kan det her være en parameter der bliver passeret i stedet?
					button = gui.getUserButtonPressed(bidder.getName() + " What would you like to bid on?", buttons);
					int bid = gui.getUserInteger("How much would you like to bid?");
					String selection1 = gui.getUserSelection(player.getName() + " Do you accept this bid?", "yes", "no");

					if (selection1.equals("yes")) {
						payment(bidder, bid, player);
						for (Property property : player.getOwnedProperties()) {
							if (property.getName().equals(button)) {
								property.setOwner(bidder);
								bidder.getOwnedProperties().add(property);
								player.getOwnedProperties().remove(property);
							}
						}
					}
				} while (!button.equals("no"));
				}
			if (player.getBalance() < amount && player.getOwnedProperties().size() == 0) {
				throw new PlayerBrokeException(player);
			}
		}
	}

	/**
	 * This method implements the activity of offering a player to buy
	 * a property. This is typically triggered by a player arriving on
	 * an property that is not sold yet. If the player chooses not to
	 * buy, the property will be set for auction.
	 *
	 * @param property the property to be sold
	 * @param player   the player the property is offered to
	 * @throws PlayerBrokeException when the player chooses to buy but could not afford it
	 */
	public void offerToBuy(Property property, Player player) throws PlayerBrokeException {
		// TODO We might also allow the player to obtainCash before
		// the actual offer, to see whether he can free enough cash
		// for the sale.
		if (player.getBalance() > property.getCost()) {
			String choice = gui.getUserSelection(
					"Player " + player.getName() +
							": Do you want to buy " + property.getName() +
							" for " + property.getCost() + "$?",
					"yes",
					"no");

			if (choice.equals("yes")) {
				try {
					paymentToBank(player, property.getCost());
				} catch (PlayerBrokeException e) {
					// if the payment fails due to the player being broke,
					// an auction (among the other players is started
					auction(property);
					// then the current move is aborted by casting the
					// PlayerBrokeException again
					throw e;
				}
				player.addOwnedProperty(property);
				property.setOwner(player);
				return;
			}
		}
		// In case the player does not buy the property,
		// an auction is started
		auction(property);
	}


	/**
	 * This method implements a payment activity to another player,
	 * which involves the player to obtain some cash on the way, in case he does
	 * not have enough cash available to pay right away. If he cannot free
	 * enough money in the process, the player will go bankrupt.
	 *
	 * @param payer    the player making the payment
	 * @param amount   the payed amount
	 * @param receiver the beneficiary of the payment
	 * @throws PlayerBrokeException when the payer goes broke by this payment
	 */
	public void payment(Player payer, int amount, Player receiver) throws PlayerBrokeException {
		if (payer.getBalance() < amount) {
			obtainCash(payer, amount);
			if (payer.getBalance() < amount) {
				playerBrokeTo(payer, receiver);
				throw new PlayerBrokeException(payer);
			}
		}
		gui.showMessage("Player " + payer.getName() + " pays " + amount + "$ to player " + receiver.getName() + ".");
		payer.payMoney(amount);
		receiver.receiveMoney(amount);
	}

	/**
	 * This method implements the action of a player receiving money from
	 * the bank.
	 *
	 * @param player the player receiving the money
	 * @param amount the amount
	 */
	public void paymentFromBank(Player player, int amount) {
		player.receiveMoney(amount);
	}

	/**
	 * This method implements the activity of a player making a payment to
	 * the bank. Note that this might involve the player to obtain some
	 * cash; in case he cannot free enough cash, he will go bankrupt
	 * to the bank.
	 *
	 * @param player the player making the payment
	 * @param amount the amount
	 * @throws PlayerBrokeException when the player goes broke by the payment
	 */
	public void paymentToBank(Player player, int amount) throws PlayerBrokeException {
		if (amount > player.getBalance()) {
			obtainCash(player, amount);
			if (amount > player.getBalance()) {
				playerBrokeToBank(player);
				throw new PlayerBrokeException(player);
			}

		}
		gui.showMessage("Player " + player.getName() + " pays " + amount + "$ to the bank.");
		player.payMoney(amount);
	}

	/**
	 * This method implements the activity of auctioning a property.
	 *
	 * @param property the property which is for auction
	 *                 The max and min amount of bid is currently not working when i have 'highest bid' instead of a raw number ex:1,5,100
	 *                 It works when using mouse on screen it wont allow player to bid if it is out of range. But it is possible to press enter
	 *                 even though the 'ok' button is read
	 * @author s175124
	 */
	public void auction(Property property) {
		// TODO auction needs to be implemented
		int currentBid = 0;
		int highestBid = 0;
		int playerAmount = game.getPlayers().size();
		int currentPlayerNR = 0;

		//Finds which number the current player has in the player array
		for (int i = 0; game.getPlayers().size() > i; i++) {
			if (game.getCurrentPlayer() == game.getPlayers().get(i)) {
				currentPlayerNR = i;
			}
		}

		//Creates a new player arraylist so the person that landed on the property starts the bidding
		int moveAmount = game.getPlayers().size() - currentPlayerNR;
		ArrayList<Player> bidList = new ArrayList<>();
		for (int i = 0; playerAmount > i; i++) {
			bidList.add(game.getPlayers().get(i));
		}

		for (int i = 0; game.getPlayers().size() > i; i++) {
			if (i == currentPlayerNR) {
				bidList.remove(0);
				bidList.add(0, game.getCurrentPlayer());
			} else if (i == 0) {
				bidList.remove(moveAmount);
				bidList.add(moveAmount, game.getPlayers().get(i));

			} else {
				bidList.remove((i + moveAmount) % game.getPlayers().size());
				bidList.add((i + moveAmount) % game.getPlayers().size(), game.getPlayers().get(i));
			}
		}

		//Actual bidding method

		Player highestBidder = new Player();
		int counter = 0;
		while (counter < bidList.size() - 1) {
			for (int i = 0; bidList.size() > i; i++) {
				if (bidList.get(i).getBalance() <= highestBid) {
					gui.showMessage("You do not have sufficient funds to participate in the auction");
					bidList.remove(i);
				} else {
					String option = gui.getUserButtonPressed("The highest bid is " + highestBid + " by " + highestBidder.getName() + ".\n"
							+ bidList.get(i).getName() + " Do you want to bid? ", "yes", "no");
					if (option.equals("yes")) {
						currentBid = gui.getUserInteger("The highest bid is " + highestBid + " by " + highestBidder.getName() + ".\n" +
										bidList.get(i).getName() + ", how much would you like to bid? Must be between " + highestBid + " and " + bidList.get(i).getBalance()
								, highestBid, bidList.get(i).getBalance());
						highestBid = currentBid;
						highestBidder = bidList.get(i);
						counter = 0;
					} else if (option.equals("no")) {
						counter++;
					}
				}
			}
		}
		if (highestBidder != null) {
		gui.showMessage("Congratulations " + highestBidder.getName() + " you win " + property.getName() + " for " + highestBid + "dollars!");
		highestBidder.payMoney(highestBid);
		highestBidder.addOwnedProperty(property);
		property.setOwner(highestBidder);
		} else {
			gui.showMessage("there were no bidders so it remains unowned!");
		}
	}


	/**
	 * Action handling the situation when one player is broke to another
	 * player. All money and properties are transferred to the other player.
	 *
	 * @param brokePlayer the broke player
	 * @param benificiary the player who receives the money and assets
	 */
	public void playerBrokeTo(Player brokePlayer, Player benificiary) {
		int amount = brokePlayer.getBalance();
		benificiary.receiveMoney(amount);
		brokePlayer.setBalance(0);
		brokePlayer.setBroke(true);

		// TODO We assume here, that the broke player has already sold all his houses! But, if
		// not, we could make sure at this point that all houses are removed from
		// properties (properties with houses on are not supposed to be transferred, neither
		// in a trade between players, nor when  player goes broke to another player)
		for (Property property : brokePlayer.getOwnedProperties()) {
			property.setOwner(benificiary);
			benificiary.addOwnedProperty(property);
		}
		brokePlayer.removeAllProperties();

		while (!brokePlayer.getOwnedCards().isEmpty()) {
			game.returnCardToDeck(brokePlayer.getOwnedCards().get(0));
		}

		gui.showMessage("Player " + brokePlayer.getName() + "went broke and transfered all"
				+ "assets to " + benificiary.getName());
	}

	/**
	 * Action handling the situation when a player is broke to the bank.
	 *
	 * @param player the broke player
	 */
	public void playerBrokeToBank(Player player) {

		player.setBalance(0);
		player.setBroke(true);

		// TODO we also need to remove the houses and the mortgage from the properties

		for (Property property : player.getOwnedProperties()) {
			property.setOwner(null);
		}
		player.removeAllProperties();

		gui.showMessage("Player " + player.getName() + " went broke");

		while (!player.getOwnedCards().isEmpty()) {
			game.returnCardToDeck(player.getOwnedCards().get(0));
		}
	}

	/**
	 * Method for disposing of this controller and cleaning up its resources.
	 */
	public void dispose() {
		if (!disposed && view != null) {
			disposed = true;
			if (view != null) {
				view.dispose();
				view = null;
			}
			// TODO we should also dispose of the GUI here. But this works only
			//      for my private version of the GUI and not for the GUI currently
			//      deployed via Maven (or other official versions);
		}
	}

	public void checkforbuildable(RealEstate estate) {
		Set<RealEstate> estateSet = RealEstate.getcolormap(estate);
		int counter = 0;
		for (RealEstate realEstate : estateSet) {
			if (realEstate.getOwner() == estate.getOwner()) {
				counter++;
			}
		}
		if (counter == estateSet.size())
			estate.setBuildable(true);
	}

	public void Offerhouses(List<Player> players) {

		for (Player p : players) {
			ArrayList<RealEstate> realEstates = new ArrayList<>();
			ArrayList<RealEstate> buildablelist = new ArrayList<>();
			for (Property property : p.getOwnedProperties()) {
				if (property instanceof RealEstate) {
					realEstates.add((RealEstate) property);
				}
			}
			for (RealEstate realEstate : realEstates) {
				checkforbuildable(realEstate);
			}
			//Tjekker om der overhovedet er nogle real estates der kan bygges på. TODO brug de nye color hashsets for optimering.
			for (RealEstate realEstate : realEstates) {
				if (realEstate.isBuildable() == true) {
					buildablelist.add(realEstate);
				}
			}
				if (buildablelist.size() > 0) {
					String userbutton = gui.getUserSelection(p.getName() + " Do you want to build anything on your real estates?", "yes", "no");

					if (userbutton.equals("yes")) {
						//Makes the list of real estates that can be build on.

						String[] buttons = new String[buildablelist.size() + 1];
						int incrementer = 0;
							for (RealEstate estate : buildablelist) {
								buttons[incrementer] = estate.getName();
								incrementer++;
							}
						buttons[buttons.length - 1] = "exit";
						while (true) {
							String button = gui.getUserButtonPressed("Where would you like to build?", buttons);

							if (button.equals("exit")) {
								break;
							}

							//Jeg ved jo allerede hvad det er for en ejendom, er der en anden måde end at iterere igen?
							String[] houses = {"1", "2", "3", "hotel"};
							String button2 = gui.getUserButtonPressed("What would you like to have on your RealEstate", houses);
							for (RealEstate estate : buildablelist) {
								if (button.equals(estate.getName())) {
									if (button2.equals("hotel")) {
										if (estate.isHotel()) {
											gui.showMessage("You already have a hotel!");
										} else {
										p.setBalance(p.getBalance() - 4000 + 1000 * estate.getHouses());
										estate.setHotel(true);
										estate.setHouses(0);
										}
									} else if (estate.getHouses() >= Integer.valueOf(button2)) {
										//TODO Thow an exeception?
										gui.showMessage("what you doin boiiiiiiii");
									} else {
										p.setBalance(p.getBalance() - 1000 * Integer.valueOf(button2) + estate.getHouses() * 1000);
										estate.setHouses(Integer.valueOf(button2));
									}
								}
							}
						}
					}
					} else {
					gui.showMessage(p.getName() + " you don't have anything you can build on");
				}
			}

		}

	public void setDiecount(int diecount1, int diecount2) {
		Diecount = diecount1 + diecount2;
	}

	public int getDiecount() {
		return Diecount;
	}
}
