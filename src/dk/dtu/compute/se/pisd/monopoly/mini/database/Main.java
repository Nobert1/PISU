package dk.dtu.compute.se.pisd.monopoly.mini.database;

import dk.dtu.compute.se.pisd.monopoly.mini.database.dal.IUserDAO;
import dk.dtu.compute.se.pisd.monopoly.mini.database.dal.UserDAOImpls180557;
import dk.dtu.compute.se.pisd.monopoly.mini.database.dto.UserDTO;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IUserDAO.DALException {

        UserDAOImpls180557 myUserDAO = new UserDAOImpls180557();
        UserDTO user;
        user = myUserDAO.getUser(180557);
        System.out.println("---Using get user on id 180557---");
        System.out.println(user.getUserId()+"; "+user.getUserName()+"; "+user.getIni()+"; "+user.getRoles());

        System.out.println("---Using get user list with names only---");
        List<UserDTO> users = myUserDAO.getUserList();
        for(int i=0;i<users.size();i++){
            System.out.println(users.get(i).getUserName());
        }

        System.out.println("---Using create user with name id=123456, name=test, ini=tst, roles=janitor and lunchlady");
        UserDTO test = new UserDTO();
        List<String> roles = new ArrayList<String>();
        roles.add("janitor");
        roles.add("lunchlady");
        test.setUserId(123456); test.setUserName("test"); test.setIni("tst"); test.setRoles(roles);
        myUserDAO.createUser(test);

        user = myUserDAO.getUser(123456);
        System.out.println("---Using get user on id 123456---");
        System.out.println(user.getUserId()+"; "+user.getUserName()+"; "+user.getIni()+"; "+user.getRoles());

        System.out.println("---Using update user on id 123456 and changes ini to sts");
        test.setIni("abc");
        myUserDAO.updateUser(test);
        user = myUserDAO.getUser(123456);
        System.out.println("---Using get user on id 123456---");
        System.out.println(user.getUserId()+"; "+user.getUserName()+"; "+user.getIni()+"; "+user.getRoles());
        System.out.println("---Using get user list with names only---");
        users = myUserDAO.getUserList();
        for(int i=0;i<users.size();i++){
            System.out.println(users.get(i).getUserName());
        }

        System.out.println("---Using delete user on id 123456 and calls get user list again");
        myUserDAO.deleteUser(123456);
        System.out.println("---Using get user list with names only---");
        users = myUserDAO.getUserList();
        for(int i=0;i<users.size();i++){
            System.out.println(users.get(i).getUserName());
        }



    }

}