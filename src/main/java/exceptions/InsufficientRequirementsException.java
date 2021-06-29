package exceptions;

/**
 * Exception thrown if Player don't own the DevelopmentCard instances required to do a particular action
 */
public class InsufficientRequirementsException extends Exception{
    public InsufficientRequirementsException(){
        super();
    }
    public InsufficientRequirementsException(String s){
        super(s);
    }
}
