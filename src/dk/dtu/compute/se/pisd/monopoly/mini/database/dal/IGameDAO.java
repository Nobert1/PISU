package dk.dtu.compute.se.pisd.monopoly.mini.database.dal;

import dk.dtu.compute.se.pisd.monopoly.mini.model.Game;

public interface IGameDAO {

    //Jeg er ikke sikker på de her ting
    //GameId er det id der kommer fra databasen så den ved hvilket save den skal have informationen fra. Det er en henvisning til databasen.
    //Delete save laver vi hvis vi får tid, det er ikke en høj priotet.
    void savegame () throws DALException;

    void getGame (int gameId) throws DALException;

    void creategame() throws DALException;

    void deleteSave(int gameId) throws DALException;

}

