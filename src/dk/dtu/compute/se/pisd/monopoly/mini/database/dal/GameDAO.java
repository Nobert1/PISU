package dk.dtu.compute.se.pisd.monopoly.mini.database.dal;

import dk.dtu.compute.se.pisd.monopoly.mini.database.dto.GameDTO;
import dk.dtu.compute.se.pisd.monopoly.mini.model.Game;
import dk.dtu.compute.se.pisd.monopoly.mini.model.Player;
import dk.dtu.compute.se.pisd.monopoly.mini.model.Property;
import dk.dtu.compute.se.pisd.monopoly.mini.model.Space;
import dk.dtu.compute.se.pisd.monopoly.mini.model.properties.RealEstate;
import dk.dtu.compute.se.pisd.monopoly.mini.model.properties.Utility;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class GameDAO implements IGameDAO {

    /**
     * Så tænker jeg her for at det ikke skal blive alt for stort at der kommer en metode der tager fra properties og skriver til dem, en til spiller etc....
     * Indtil videre har vi ikke noget at gøre med chancekort. Lav evt forbindelsen til din egen database.
     */

    private Game game;

    private Connection createConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://ec2-52-30-211-3.eu-west-1.compute.amazonaws.com/s185031?"
                + "user=s185031&password=UfudYEA2p7RmipWZXxT2R");
    }


    /**
     * Probably needs something like a gameID that can be referenced.
     * Right now it's probably good enough if the methods are working allright.
     *
     * @param gameId
     * @throws DALException
     */
    @Override
    public void savegame(int gameId) throws DALException {
        insertintoPlayers();
        insertintoproperties();
        insertintoRealEstates();
    }

    /**
     * Not in scope for the first iteration.
     *
     * @param gameId
     * @throws IUserDAO.DALException
     */
    @Override
    public void deleteSave(int gameId) throws IUserDAO.DALException {

    }


    /**
     * Den her skal jo gerne kunne et eller andet men den er ikke helt save game, så hvad skal den her indeholde?
     * TODO make methods for this to work.
     *
     * @throws IUserDAO.DALException
     */
    @Override
    public void creategame() throws IUserDAO.DALException {

    }

    /**
     * Takes a gameID as a parameter, we need the gui to show a list of saved games. In the long run you could save with a string
     * such that it can be "xxxxxx's, yyyy's, and wwwww's game.
     * TODO this needs something that actually sets the parameters in the game.
     */
    @Override
    public GameDTO getGame(int gameId) throws IUserDAO.DALException {
        GameDTO game = new GameDTO();
        try {
            getPlayers(gameId);
            getproperties(gameId);
            getRealEstates(gameId);
            return game;
        } catch (DALException e) {
            throw new IUserDAO.DALException(e.getMessage());
        }
    }

    /**
     * Theese are methods that create objects and returns them, just like in the database assignment.
     *
     * @return
     * @throws DALException
     */
    private List<Property> getproperties(int gameID) throws DALException {

        try (Connection c = createConnection()) {
            Statement statement = c.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Properties WHERE GAME ID=" + gameID);
            ArrayList<Property> propertyList = new ArrayList<>();
            while (resultSet.next()) {
                Property property = makePropertyFromResultsSet(resultSet);
                propertyList.add(property);
            }
            return propertyList;
        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
    }

    private List<RealEstate> getRealEstates(int gameID) throws DALException {
        try (Connection c = createConnection()) {
            Statement statement = c.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM RealEstate WHERE GAME ID=" + gameID);
            ArrayList<RealEstate> realEstatelist = new ArrayList<>();
            while (resultSet.next()) {
                RealEstate realEstate = makeRealestateFromResultset(resultSet);
                realEstatelist.add(realEstate);
            }
            return realEstatelist;
        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
    }

    private List<Player> getPlayers(int gameID) throws DALException {

        try (Connection c = createConnection()) {
            Statement statement = c.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Player WHERE GAME ID=" + gameID);
            ArrayList<Player> playerList = new ArrayList<>();
            while (resultSet.next()) {
                Player player = makePlayerFromResultset(resultSet);
                playerList.add(player);
            }
            return playerList;
        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
    }

    private RealEstate makeRealestateFromResultset(ResultSet resultSet) throws SQLException {

        RealEstate realEstate = new RealEstate();
        for (Space space : game.getSpaces()) {
            if (space instanceof RealEstate) {
                if (((RealEstate) space).getRealEstateID() == resultSet.getInt("RealEstateID")) {
                    realEstate.setOwner(null);
                    for (Player player : game.getPlayers()) {
                        if (player.getPlayerID() == resultSet.getInt("PlayerID")) {
                            realEstate.setOwner(player);
                        }
                    }
                    realEstate.setHouses(resultSet.getInt("houses"));
                    realEstate.setHotel(resultSet.getBoolean("hotel"));
                    realEstate.setMortgaged(resultSet.getBoolean("mortgaged"));
                }
            }
        }
        return realEstate;
    }


    private Property makePropertyFromResultsSet(ResultSet resultSet) throws SQLException {
        //For at der skal kunne sættes en ejer kræver det han er oprettet først.
        Utility utility = new Utility();
        for (Space space : game.getSpaces()) {
            if (space instanceof RealEstate) {
                if (((Utility) space).getPropertyId() == resultSet.getInt("PropertyID")) {
                    utility.setOwner(null);
                    //Det her kan godt virke, men aner det ikke.
                    for (Player player : game.getPlayers()) {
                        if (player.getPlayerID() == resultSet.getInt("PlayerID")) {
                            utility.setOwner(player);
                        }
                    }
                    utility.setMortgaged(resultSet.getBoolean("mortgaged"));
                }
            }
        }
        return utility;
    }

    private Player makePlayerFromResultset(ResultSet resultSet) throws SQLException {
        Player player = new Player();
        player.setPlayerID(resultSet.getInt("playerID"));
        player.setName(resultSet.getString("PlayerName"));
        player.setBalance(resultSet.getInt("balance"));
        player.setCurrentPosition(game.getSpaces().get(resultSet.getInt("position")));
        player.setInPrison(resultSet.getBoolean("injail"));
        player.setBroke(resultSet.getBoolean("broke"));
        //Der skal også ske noget med et token.
        return player;
    }

    /**
     * Insert into methods, we still have to figure out how it works with peoples tokens.
     * I think it will probably be best if tokens just have an owener ID or so and their own tabel.
     *
     * @throws DALException
     */

    private void insertintoPlayers() throws DALException {

        try (Connection c = createConnection()) {
            List<Player> players = game.getPlayers();
            c.setAutoCommit(false);
            PreparedStatement statement = c.prepareStatement("INSERT INTO Player VALUES + (?, ?, ?, ?, ?, ?)");


            for (Player player : players) {

                //Igen der skal ske noget med hvordan vi får fat i folks token, jeg er ikke helt sikker på.
                //Det her skal være et batch statement når vi får opdateret det til prepared statements.
                statement.setInt(1, player.getPlayerID());
                statement.setString(2, player.getName());
                statement.setInt(3, player.getBalance());
                statement.setInt(4, player.getCurrentPosition().getIndex());
                statement.setBoolean(5, player.isInPrison());
                statement.setBoolean(6, player.isBroke());
                statement.addBatch();
            }
            int playerrows = statement.executeUpdate();
            c.commit();
        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
    }

    /**
     * Since nothing is actually properties but only utilities and realestates
     *
     * @throws DALException
     */
    private void insertintoproperties() throws DALException {
        try (Connection c = createConnection()) {
            c.setAutoCommit(false);
            PreparedStatement statement = c.prepareStatement("INSERT INTO Properties VALUES + (?, ?, ?)");
            for (Space space : game.getSpaces()) {
                if (space instanceof Utility) {
                    statement.setInt(1, ((Utility) space).getOwner().getPlayerID());
                    statement.setInt(2, ((Utility) space).getPropertyId());
                    statement.setBoolean(3, ((Utility) space).isMortgaged());
                    statement.addBatch();
                }
                int Propertiesrow = statement.executeUpdate();
                c.commit();
            }
        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
    }

    private void insertintoRealEstates() throws DALException {
        try (Connection c = createConnection()) {
            c.setAutoCommit(false);
            PreparedStatement statement = c.prepareStatement("INSERT INTO RealEstate VALUES + (?, ?, ?, ?, ?)");
            for (Space space : game.getSpaces()) {
                if (space instanceof RealEstate) {
                    statement.setInt(1, ((RealEstate) space).getOwner().getPlayerID());
                    statement.setBoolean(2, ((RealEstate) space).isMortgaged());
                    statement.setInt(3, ((RealEstate) space).getRealEstateID());
                    statement.setInt(4, ((RealEstate) space).getHouses());
                    statement.setBoolean(5, ((RealEstate) space).isHotel());
                    statement.addBatch();
                }
                int Realestaterow = statement.executeUpdate();
                c.commit();
            }
        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
    }


    public String[] generategameIDs() {

        ArrayList<Integer> gameIDsList = new ArrayList<>();
        try (Connection c = createConnection()) {
            Statement statement = c.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Game");
            while (resultSet.next()) {
                int gameid = resultSet.getInt("gameID");
                gameIDsList.add(gameid);
            }
            String[] gameIdsArray = new String[gameIDsList.size()];
            for (int i = 0; i < gameIDsList.size(); i++) {
                gameIdsArray[i] = String.valueOf(gameIDsList.get(i));
            }
            return gameIdsArray;
        } catch (SQLException e) {
        }
    }
}
