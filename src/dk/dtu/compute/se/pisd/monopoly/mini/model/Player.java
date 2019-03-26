package dk.dtu.compute.se.pisd.monopoly.mini.model;

import dk.dtu.compute.se.pisd.designpatterns.Subject;

import java.awt.*;
import java.util.List;
import java.util.*;

/**
 * Represents a player and his current state in a Monopoly game.
 * This includes the player's position, owned money and properties
 * and chance cards. In order to use this part of the model with
 * the MVC-pattern, this class extends the
 * {@link dk.dtu.compute.se.pisd.designpatterns.Subject} of the observer
 * design pattern.
 * 
 * @author Ekkart Kindler, ekki@dtu.dk
 *
 */
public class Player extends Subject {
	
	private String name;
	
	private Color color;
	
	private Space currentPosition;
	
	private int balance = 40000;
	
	private boolean inPrison = false;
	
	private boolean broke = false;
	
	private Set<Property> ownedProperties = new HashSet<Property>();
	
	private List<Card> ownedCards = new ArrayList<Card>();


	/**
	 * Returns the name of the player.
	 * 
	 * @return the name of the player
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the player.
	 * 
	 * @param name the new name of the player
	 */
	public void setName(String name) {
		this.name = name;
		notifyChange();
	}

	/**
	 * Returns the colour of this player.
	 * 
	 * @return the colour
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Sets the colour of the player.
	 * 
	 * @param color the new colour of the player.
	 */
	public void setColor(Color color) {
		this.color = color;
		notifyChange();
	}

	/**
	 * Returns the space that is the current position of the player.
	 * 
	 * @return the current position
	 */
	public Space getCurrentPosition() {
		return currentPosition;
	}

	/**
	 * Sets the new position of the player (the space it is located on).
	 * 
	 * @param position the new position
	 */
	public void setCurrentPosition(Space position) {
		this.currentPosition = position;
		notifyChange();
	}

	/**
	 * Returns the current account balance of the player.
	 * 
	 * @return the balance of the player
	 */
	public int getBalance() {
		return balance;
	}

	/**
	 * Sets the new account balance of the player.
	 *  
	 * @param balance the new balance
	 */
	public void setBalance(int balance) {
		this.balance = balance;
		notifyChange();
	}
	
	/**
	 * Adds the given amount to the balance of the player.
	 * 
	 * @param amount the received amount
	 */
	public void receiveMoney(int amount) {
		balance = balance + amount;
		notifyChange();
	}
	
	/**
	 * Removes the given amount from the balance of the player.
	 * 
	 * @param amount the payed amount
	 */
	public void payMoney(int amount) {
		balance = balance - amount;
		notifyChange();
	}

	/**
	 * Returns a list of all properties the player currently owns as
	 * an unmodifiable list.
	 * 
	 * @return an unmodifiable list of all currently owned properties of the player
	 */
	public Set<Property> getOwnedProperties() {
		return Collections.unmodifiableSet(ownedProperties);
	}

	/**
	 * Sets the list of owned properties to the provided list. Note that
	 * the list is copied to avoid it to be changed without the player
	 * being aware of it.
	 * 
	 * @param ownedProperties the new list of owned properties
	 */
	public void setOwnedProperties(Set<Property> ownedProperties) {
		this.ownedProperties = new HashSet<Property>(ownedProperties);
		notifyChange();
	}
	
	/**
	 * Adds a property to the list of currently owned properties.
	 * 
	 * @param property the added property
	 */
	public void addOwnedProperty(Property property) {
		ownedProperties.add(property);
		notifyChange();
	}
	
	/**
	 * Removes a property from the list of currently owned properties.
	 * 
	 * @param property the property to be removed
	 * @return true, if the property was owned by the player
	 */
	public boolean removeOwnedProperty(Property property) {
		boolean result = ownedProperties.remove(property);
		notifyChange();
		return result;
	}
	
	/**
	 * Removes all properties from the player.
	 */
	public void removeAllProperties() {
		ownedProperties.clear();
		notifyChange();
	}
	

	/**
	 * Returns a list of all cards currently owned by the player as
	 * an unmodifiable list.
	 * 
	 * @return a list of all cards owned by the player
	 */
	public List<Card> getOwnedCards() {
		return Collections.unmodifiableList(ownedCards);
	}

	/**
	 * Sets the currently owned cards of the player to the provided
	 * list. Note that the provided list is copied.
	 * 
	 * @param ownedCards the new list of owned cards
	 */
	public void setOwnedCards(List<Card> ownedCards) {
		this.ownedCards = new ArrayList<Card>(ownedCards);
		notifyChange();
	}
	
	/**
	 * Removes a card from the owned cards.
	 * 
	 * @param card the card to be removed
	 * @return returns true if the card was owned and is removed by the method
	 */
	public boolean removeOwnedCard(Card card) {
		if (this.ownedCards.remove(card)) {
			notifyChange();
			return true;
		}
		return false;
	}

	/**
	 * Returns whether the player is broke.
	 * 
	 * @return true if the player is broke
	 */
	public boolean isBroke() {
		return broke;
	}

	/**
	 * Sets the new broke status of the player.
	 * 
	 * @param broke the new broke status of the player
	 */
	public void setBroke(boolean broke) {
		boolean oldBroke = this.broke;
		this.broke = broke;
		if (oldBroke !=  broke) {
			notifyChange();
		}
	}

	/**
	 * Returns whether the player is currently in prison.
	 * 
	 * @return true if the player is currently in prison
	 */
	public boolean isInPrison() {
		return inPrison;
	}

	/**
	 * Sets the status whether the player is currently in prison.
	 * 
	 * @param inPrison the new prison status of the player
	 */
	public void setInPrison(boolean inPrison) {
		boolean oldInPrison = this.inPrison;
		this.inPrison = inPrison;
		if (oldInPrison != inPrison) {
			notifyChange();
		}
	}

}
