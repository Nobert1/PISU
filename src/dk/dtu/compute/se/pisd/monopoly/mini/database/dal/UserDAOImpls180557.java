package dk.dtu.compute.se.pisd.monopoly.mini.database.dal;

import dk.dtu.compute.se.pisd.monopoly.mini.database.dto.UserDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//TODO Rename class
public class UserDAOImpls180557 implements IUserDAO {
    //TODO Make a connection to the database - Comment: SSL has been disabled.

    //I have updated username and password for the database.

    private Connection createConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://ec2-52-30-211-3.eu-west-1.compute.amazonaws.com/s180557?"
                + "user=s180557&password=SnM6HsTt8iPhYpasthnCW&useSSL=FALSE");
    }

    @Override
    public UserDTO getUser(int userId) throws DALException {
        //TODO Implement this

        try (Connection c = createConnection()){

            UserDTO user = null;

            Statement statement = c.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM userTable where userId="+ userId);

            //TODO: Make a user from the resultset

            if (resultSet.next()) {
                user = new UserDTO();
                user.setUserId(userId);
                user.setUserName(resultSet.getString(2));
                user.setIni(resultSet.getString(3));

                //Extract roles as String
                String roleString = resultSet.getString(4);
                //Split string by ;
                String[] roleArray = roleString.split(";");
                //Convert to List
                List<String> roleList = Arrays.asList(roleArray);
                user.setRoles(roleList);
            }

            return user;
        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }

    }

    @Override
    public List<UserDTO> getUserList() throws DALException {
        //TODO Implement this

        try (Connection c = createConnection()) {

            List<UserDTO> users = new ArrayList<UserDTO>();

            Statement statement = c.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM userTable;");

            while (resultSet.next()) {

                String userIDString = resultSet.getString(1);
                int userID = Integer.parseInt(userIDString);
                users.add(getUser(userID));
            }

            return users;

        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
    }

    @Override
    public void createUser(UserDTO user) throws DALException {
        //TODO Implement this

        try (Connection c = createConnection()) {

            Statement statement = c.createStatement();

            String roleString = String.join(";", user.getRoles());

            statement.execute("INSERT INTO userTable (userId,userName,ini,roles)" +
                    "VALUES (" + user.getUserId() + ", '" + user.getUserName() + "', '" + user.getIni() + "', '" + roleString +"')");

        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }

    }

    @Override
    public void updateUser(UserDTO user) throws DALException {
        //TODO Implement this - Comment: Update requires match for userID og userId cannot be changed.

        try (Connection c = createConnection()){

            Statement statement = c.createStatement();

            String roleString = String.join(";", user.getRoles());
            statement.execute("UPDATE userTable SET " +
                    "userName = '" + user.getUserName() + "', " +
                    "ini = '" + user.getIni() + "', " +
                    "roles = '" + roleString + "' " +
                    "WHERE userId = " + user.getUserId());

        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
    }

    @Override
    public void deleteUser(int userId) throws DALException {
        //TODO Implement this

        try (Connection c = createConnection()){

            Statement statement = c.createStatement();

            statement.execute("DELETE FROM userTable WHERE userId = " + userId);

        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }

    }
}