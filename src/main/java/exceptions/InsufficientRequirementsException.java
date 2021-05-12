package exceptions;

public class InsufficientRequirementsException extends Exception{
    public InsufficientRequirementsException(){
        super();
    }
    public InsufficientRequirementsException(String s){
        super(s);
    }
}
