package dk.dtu.compute.se.pisd.monopoly.mini.database.dal;

import dk.dtu.compute.se.pisd.monopoly.mini.database.dto.GameDTO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;




public class GameDAO implements IGameDAO {

    /**
     * Så tænker jeg her for at det ikke skal blive alt for stort at der kommer en metode der tager fra properties og skriver til dem, en til spiller etc....
     * Indtil videre har vi ikke noget at gøre med chancekort. Lav evt forbindelsen til din egen database.
     */

    private Connection createConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://ec2-52-30-211-3.eu-west-1.compute.amazonaws.com/s185031?"
                + "user=s185031&password=UfudYEA2p7RmipWZXxT2R");
    }

    @Override
    public void savegame(int gameId) throws DALException {

    }

    @Override
    public void deleteSave(int gameId) throws IUserDAO.DALException {

    }

    @Override
    public void creategame() throws IUserDAO.DALException {

    }

    @Override
    public GameDTO getGame(int gameId) throws IUserDAO.DALException {
        return null;
    }






}
