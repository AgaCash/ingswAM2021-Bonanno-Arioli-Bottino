package exceptions;

/**
 * Exception thrown if an action requires a Player whose username doesn't exists in Lobby (the size is min 2 members)
 */
public class NoSuchUsernameException extends Exception {

    public NoSuchUsernameException(){
        super();
    }

    public NoSuchUsernameException(String s){
        super(s);
    }
}
