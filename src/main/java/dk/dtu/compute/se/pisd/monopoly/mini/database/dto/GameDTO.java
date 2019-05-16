package dk.dtu.compute.se.pisd.monopoly.mini.database.dto;

import dk.dtu.compute.se.pisd.monopoly.mini.model.Player;

import java.util.ArrayList;
import java.util.List;

public class GameDTO implements IGameDTO {
    //Fields
    private int gameId;
    private int PlayerId;
    private int balance;
    private int position;
    private boolean injail;
    private boolean broke;
    private String tokencolor;
    private String tokentype;
    private int PropertyId;
    private boolean mortgaged;
    private int houses;
    private boolean hotel;
    private String PlayerName;


    //Constructor
    //Getters and Setters


    @Override
    public boolean isHotel() {return hotel; }

    @Override
    public void setHouses(int houses) { this.houses = houses; }

    @Override
    public boolean isMortgaged() { return mortgaged; }

    @Override
    public void setPropertyId(int propertyId) { PropertyId = propertyId; }

    @Override
    public void setTokentype(String tokentype) { this.tokentype = tokentype; }

    @Override
    public void setplayername(String name) { this.PlayerName = name; }

    @Override
    public String getplayername() { return PlayerName; }

    @Override
    public int getbalance() { return balance; }

    @Override
    public void setbalance(int Balance) { this.balance = Balance;}

    @Override
    public int getplayerid() { return PlayerId; }

    @Override
    public void setplayerid(int playerId) { this.PlayerId = playerId; }


    @Override
    public void setTokencolor(String tokencolor) { this.tokencolor = tokencolor; }

    @Override
    public void setGameid(int gameid) { this.gameId = gameid; }

    @Override
    public int getposition() { return position; }

    @Override
    public void setinjail(boolean injail) { this.injail = injail; }

    @Override
    public void setposition(int position) { this.position = position; }

    @Override
    public int getgameid() { return gameId; }

    @Override
    public void setBroke(boolean broke) { this.broke = broke; }

    @Override
    public int getPropertyId() { return PropertyId; }

    @Override
    public void setMortgaged(boolean mortgaged) { this.mortgaged = mortgaged; }

    @Override
    public String getTokencolor() { return tokencolor; }


    @Override
    public String getTokentype() { return tokentype; }



    @Override
    public int getHouses() { return houses; }

    @Override
    public void setHotel(boolean hotel) { this.hotel = hotel; }

    @Override
    public boolean getbroke() { return broke; }


    @Override
    public boolean getinjail() { return injail; }






}