package exceptions;

/**
 * Exception if the message received is not valid
 */
public class InvalidMessageException extends Exception{

    public InvalidMessageException(){
        super();
    }
    public InvalidMessageException(String s){
        super(s);
    }
}

