package exceptions;

/**
 * Exception thrown if Player don't own the Resource instances required to do a particular action
 */
public class InsufficientResourcesException extends Exception{
    public InsufficientResourcesException(){
        super();
    }
    public InsufficientResourcesException(String s){
        super(s);
    }
}

