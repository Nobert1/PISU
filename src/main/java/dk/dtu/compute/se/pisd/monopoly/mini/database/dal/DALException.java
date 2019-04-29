package dk.dtu.compute.se.pisd.monopoly.mini.database.dal;

public class DALException extends Exception {

public DALException(String msg, Throwable e) {
    super(msg,e);
}

public DALException(String msg) {
    super(msg);
}
}
