package exceptions;

/**
 * Exception if during reconnection username is already in a game
 */
public class UsernameAlreadyInAGameException extends Exception{

    public UsernameAlreadyInAGameException(){
        super();
    }

    public UsernameAlreadyInAGameException(String s){
        super(s);
    }
}
