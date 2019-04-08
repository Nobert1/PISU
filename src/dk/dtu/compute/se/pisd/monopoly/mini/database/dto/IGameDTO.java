package dk.dtu.compute.se.pisd.monopoly.mini.database.dto;

import java.util.List;

public interface IGameDTO {

    int getgameid();
    void setGameid(int gameid);
    String getplayername();
    void setplayername(String name);
    int getplayerid();
    void setplayerid(int playerId);
    int getbalance();
    void setbalance(int Balance);
    int getposition();
    void setposition(int position);
    boolean getinjail();
    void setinjail (boolean injail);
    boolean getbroke();
    void setBroke(boolean broke);
    String getTokencolor();
    void setTokencolor(String tokencolor);
    String getTokentype();
    void setTokentype(String tokentype);
    int getPropertyId();
    void setPropertyId(int propertyId);
    boolean isMortgaged();
    void setMortgaged(boolean mortgaged);
    int getHouses();
    void setHouses(int houses);
    boolean isHotel();
    void setHotel(boolean hotel);


}
