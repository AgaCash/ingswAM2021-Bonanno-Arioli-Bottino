package exceptions;

/**
 * Exception if username is already used
 */
public class UsernameAlreadyUsedException extends Exception{

    public UsernameAlreadyUsedException(){
        super();
    }

    public UsernameAlreadyUsedException(String s){
        super(s);
    }
}
