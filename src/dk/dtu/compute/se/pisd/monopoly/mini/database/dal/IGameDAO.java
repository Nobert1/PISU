package dk.dtu.compute.se.pisd.monopoly.mini.database.dal;

import dk.dtu.compute.se.pisd.monopoly.mini.database.dto.GameDTO;
import dk.dtu.compute.se.pisd.monopoly.mini.database.dto.UserDTO;

import java.util.List;

public interface IGameDAO {

    //Jeg er ikke sikker på de her ting
    //GameId er det id der kommer fra databasen så den ved hvilket save den skal have informationen fra. Det er en henvisning til databasen.
    //Delete save laver vi hvis vi får tid, det er ikke en høj priotet.
    void savegame (int gameId) throws DALException;

    GameDTO getGame (int gameId) throws IUserDAO.DALException;

    void newgame() throws IUserDAO.DALException;

    void deleteSave(int gameId) throws IUserDAO.DALException;

}

class DALException extends Exception {

    public DALException(String msg, Throwable e) {
        super(msg,e);
    }

    public DALException(String msg) {
        super(msg);
    }
}
