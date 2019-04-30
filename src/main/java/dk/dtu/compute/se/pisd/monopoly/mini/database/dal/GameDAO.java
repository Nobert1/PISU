package dk.dtu.compute.se.pisd.monopoly.mini.database.dal;

import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;
import com.mysql.cj.jdbc.MysqlDataSource;
import dk.dtu.compute.se.pisd.monopoly.mini.model.Game;
import dk.dtu.compute.se.pisd.monopoly.mini.model.Player;
import dk.dtu.compute.se.pisd.monopoly.mini.model.Property;
import dk.dtu.compute.se.pisd.monopoly.mini.model.Space;
import dk.dtu.compute.se.pisd.monopoly.mini.model.properties.Colors;
import dk.dtu.compute.se.pisd.monopoly.mini.model.properties.RealEstate;
import dk.dtu.compute.se.pisd.monopoly.mini.model.properties.Utility;
import gui_main.GUI;

import javax.sql.ConnectionPoolDataSource;
import javax.sql.DataSource;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;


public class GameDAO implements IGameDAO {

    /**
     * Så tænker jeg her for at det ikke skal blive alt for stort at der kommer en metode der tager fra properties og skriver til dem, en til spiller etc....
     * Indtil videre har vi ikke noget at gøre med chancekort. Lav evt forbindelsen til din egen database.
     */

//TODO - fix alle de der milliarder af forbindelser der bliver åbnet og lukket. Udover det skal der også laves nye skemaer, som rent faktisk giver mening.
// TODO Mulige fixes kan være en metode der opretter en forbindelse, hvis der allerede er en sund forbindelse returnerer den forbindelsen i stedet for.
// TODO lav et statement der kører "create if not exists" med tabellerne.

    private Game game;
    static Connection con=null;


    public GameDAO (Game game) {
        this.game = game;
    }

    private Connection createConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://ec2-52-30-211-3.eu-west-1.compute.amazonaws.com/s185031?"
                + "user=s185031&password=UfudYEA2p7RmipWZXxT2R");

    }



    /**
     * Probably needs something like a gameID that can be referenced.
     * Right now it's probably good enough if the methods are working allright.
     *
     * @param
     * @throws DALException
     */
    @Override
    public void savegame() throws DALException {
        try {
            Connection c = createConnection();
            insertgameID(c);
            int max = getmaxgameID(c);
            insertintoPlayers(c, max);
            insertintoTokens(c, max);
            insertintoproperties(c, max);
            insertintoRealEstates(c, max);
             } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }

    }

    /**
     * Not in scope for the first iteration.
     *
     * @param gameId
     * @throws DALException
     */
    @Override
    public void deleteSave(int gameId) throws DALException {
        //TODO går nok bare noget lignende et SQL statement der hedder DELETE * FROM gameID WHERE gameID=(?) og så med noget cascade.
    }


    /**
     * Den her skal jo gerne kunne et eller andet men den er ikke helt save game, så hvad skal den her indeholde?
     * TODO make methods for this to work.
     *
     * @throws DALException
     */
    @Override
    public void creategame() throws DALException {
        //TODO den her skal nok slettes, den giver sig selv.
    }

    /**
     * Takes a gameID as a parameter, we need the gui to show a list of saved games. In the long run you could save with a string
     * such that it can be "xxxxxx's, yyyy's, and wwwww's game.
     * TODO this needs something that actually sets the parameters in the game.
     * TODO - here one option is making a list of RealEstates, and a list of Utilities.
     */
    @Override
    public void getGame(int gameId) throws DALException {
        try {
            Connection c = createConnection();
            game.setPlayers(getPlayers(gameId, c));
            game.setSpaces(getspaces(gameId, c));

        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
    }

    /**
     * Theese are methods that create objects and returns them, just like in the database assignment.
     *
     * @return
     * @throws DALException
     */

    private List<Space> getspaces(int gameID, Connection c) throws DALException {
     List<Space> spacelist = game.getSpaces();
     List<RealEstate> estates = getRealEstates(gameID, c);
     List<Utility> utilities = getUtilites(gameID, c);
     int estatecounter = 0;
     int utilitycounter = 0;
     for (int i = 0; i < spacelist.size(); i++) {
         if (spacelist.get(i) instanceof RealEstate) {
             spacelist.set(i, estates.get(estatecounter));
             estatecounter++;
         } else if (spacelist.get(i) instanceof Utility) {
             spacelist.set(i, utilities.get(utilitycounter));
             utilitycounter++;
         }
     }

     return spacelist;
    }

    private List<Utility> getUtilites(int gameID, Connection c) throws DALException {

        //try (Connection c = getconnection()) {
            try {
            PreparedStatement statement = c.prepareStatement("SELECT * FROM Properties WHERE gameID=?");

            statement.setInt(1, gameID);
            ResultSet resultSet = statement.executeQuery();
            ArrayList<Utility> Utilitylist = new ArrayList<>();
            while (resultSet.next()) {
                Utility utility = makeUtilityFromResultset(resultSet);
                Utilitylist.add(utility);
            }
            return Utilitylist;
        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
    }

    private List<RealEstate> getRealEstates(int gameID, Connection c) throws DALException {

       try {
      //  try (Connection c = getconnection()){
            Statement statement = c.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM RealEstate WHERE gameID="+ gameID);
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

    private List<Player> getPlayers(int gameID, Connection c) throws DALException {
        try {
            Statement statement = c.createStatement();
            Statement statement2 = c.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Player WHERE gameID=" + gameID);
            ResultSet resultSet1 = statement2.executeQuery("SELECT * FROM Token WHERE gameID=" + gameID);
            ArrayList<Player> playerList = new ArrayList<>();
            while (resultSet.next()) {
                Player player = makePlayerFromResultset(resultSet);
                playerList.add(player);
            }
            while (resultSet1.next()) {
                for (Player player1 : playerList) {
                    if (player1.getPlayerID() == resultSet1.getInt("ownerID")) {
                        Color color = new Color(resultSet1.getInt("r"), resultSet1.getInt("g"), resultSet1.getInt("b"));
                        player1.setColor(color);
                    }
                }

            }
            return playerList;
        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
        }
        /**

        try (Connection c = getconnection()) {
            Statement statement = c.createStatement();
            Statement statement2 = c.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Player WHERE gameID=" + gameID);
            ResultSet resultSet1 = statement2.executeQuery("SELECT * FROM Token WHERE gameID=" + gameID);
            ArrayList<Player> playerList = new ArrayList<>();
            while (resultSet.next() && resultSet1.next()) {
                Player player = makePlayerFromResultset(resultSet);
                //TODO en eller anden form for reference til playerID her.
                Color color = new Color(resultSet1.getInt("r"), resultSet1.getInt("g"), resultSet1.getInt("b"));
                player.setColor(color);
                playerList.add(player);
            }
            return playerList;
        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
         *
         */


    private RealEstate makeRealestateFromResultset(ResultSet resultSet) throws SQLException {

        RealEstate realEstate = new RealEstate();
        for (RealEstate realEstate1 : game.getRealestates()) {
                if (realEstate1.getPropertid() == resultSet.getInt("RealEstateID")) {
                    realEstate = realEstate1;
                    for (Player player : game.getPlayers()) {
                        if (player.getPlayerID() == resultSet.getInt("ownerID")) {
                            realEstate.setOwner(player);
                            realEstate.setOwned(true);
                            player.addOwnedProperty(realEstate);
                        }
                    }
                    realEstate.setHouses(resultSet.getInt("houses"));
                    realEstate.setHotel(resultSet.getBoolean("hotel"));
                    realEstate.setMortgaged(resultSet.getBoolean("mortgaged"));
                }
            }
        return realEstate;
    }


    private Utility makeUtilityFromResultset(ResultSet resultSet) throws SQLException {
        //For at der skal kunne sættes en ejer kræver det han er oprettet først.
        Utility utility = new Utility();
        for (Utility utility1 : game.getUtilites()) {
            if (utility1.getPropertyid() == resultSet.getInt("PropertyID")) {
                    utility = utility1;
                    //Det her kan godt virke, men aner det ikke.
                    for (Player player : game.getPlayers()) {
                        if (player.getPlayerID() == resultSet.getInt("ownerID")) {
                            utility.setOwner(player);
                            utility.setOwned(true);
                            player.addOwnedProperty(utility);

                        }
                    }
                    utility.setMortgaged(resultSet.getBoolean("mortgaged"));
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

    private void insertintoPlayers(Connection c, int max) throws DALException {

        try {
       // try (Connection c = getconnection()) {
            PreparedStatement statement = c.prepareStatement("INSERT INTO Player VALUES (?, ?, ?, ?, ?, ?, ?)");

            c.setAutoCommit(false);

            for (Player player : game.getPlayers()) {

                //TODO Regex this perhaps?
                statement.setInt(1, player.getPlayerID());
                statement.setString(2, player.getName());
                statement.setInt(3, max);
                statement.setInt(4, player.getBalance());
                statement.setInt(5, player.getCurrentPosition().getIndex());
                statement.setBoolean(6, player.isInPrison());
                statement.setBoolean(7, player.isBroke());
                statement.addBatch();
            }
            int[] rows = statement.executeBatch();
            c.commit();
        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
    }

    //TODO find ud af hvordan det skal virke med bilerne.
    public void insertintoTokens(Connection c, int max) throws DALException{

        try {
        //try (Connection c = getconnection()) {

            PreparedStatement statement = c.prepareStatement("INSERT INTO Token VALUES (?, ?, ?, ?, ?, ?)");
            c.setAutoCommit(false);

            for (Player player : game.getPlayers()) {
            statement.setInt(1, player.getPlayerID());
            statement.setInt(2, max);
            statement.setInt(3, player.getColor().getRed());
            statement.setInt(4, player.getColor().getGreen());
            statement.setInt(5, player.getColor().getBlue());
            statement.setNull(6, Types.VARCHAR);

            statement.addBatch();
            }
            int[] colorrows = statement.executeBatch();
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
    private void insertintoproperties(Connection c, int max) throws DALException {

        try {
       // try (Connection c = getconnection()) {
            c.setAutoCommit(false);
            PreparedStatement statement = c.prepareStatement("INSERT INTO Properties VALUES (?, ?, ?, ?)");
                    for (Utility utility : game.getUtilites()) {
                    statement.setInt(1, max);
                    if (utility.getOwner() != null) {
                        statement.setInt(2, utility.getOwner().getPlayerID());
                    } else {
                        statement.setNull(2, Types.INTEGER);
                    }
                    statement.setInt(3, utility.getPropertyid());
                    statement.setBoolean(4, utility.isMortgaged());
                    statement.addBatch();
            }
            int[] Propertiesrow = statement.executeBatch();
            c.commit();
        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
    }

    private void insertintoRealEstates(Connection c, int max) throws DALException {

        //try (Connection c = getconnection()) {
          try {
            c.setAutoCommit(false);
            PreparedStatement statement = c.prepareStatement("INSERT INTO RealEstate VALUES (?, ?, ?, ?, ?, ?)");
                    for (RealEstate realEstate : game.getRealestates()) {
                    statement.setInt(1, max);
                    if (realEstate.getOwner() != null) {
                    statement.setInt(2, realEstate.getOwner().getPlayerID());
                    } else {
                    statement.setNull(2, Types.INTEGER);
                    }
                    statement.setBoolean(3, realEstate.isMortgaged());
                    statement.setInt(4, realEstate.getPropertid());
                    statement.setInt(5, realEstate.getHouses());
                    statement.setBoolean(6,realEstate.isHotel());
                    statement.addBatch();
                    }

            int[] Realestaterow = statement.executeBatch();
            c.commit();
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
        return null;
    }

    public void insertgameID(Connection c) {
        try {
        //try (Connection c = getconnection()) {
            Statement statement = c.createStatement();
            statement.executeUpdate("INSERT INTO Game VALUES (default)");
        } catch (SQLException e) {
            e.getMessage();
        }
    }

    public int getmaxgameID(Connection c) {
        try {
        //try (Connection c = getconnection()) {
            Statement statement = c.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT MAX(gameID) FROM Game");
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.getMessage();
        }
        System.out.println("there wasn't anything to be found");
        return -1;
    }
}