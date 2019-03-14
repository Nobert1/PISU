package dk.dtu.compute.se.pisd.monopoly.mini.database.dal;

import dk.dtu.compute.se.pisd.monopoly.mini.database.dto.UserDTO;

import java.util.List;

public interface IUserDAO {

    UserDTO getUser(int userId) throws DALException;
    List<UserDTO> getUserList() throws DALException;
    void createUser(UserDTO user) throws DALException;
    void updateUser(UserDTO user) throws DALException;
    void deleteUser(int userId) throws DALException;

    public class DALException extends Exception {
        //Til Java serialisering...
        private static final long serialVersionUID = 7355418246336739229L;

        public DALException(String msg, Throwable e) {
            super(msg,e);
        }

        public DALException(String msg) {
            super(msg);
        }

    }

}