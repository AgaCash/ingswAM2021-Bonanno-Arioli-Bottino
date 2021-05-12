package exceptions;

public class InsufficientResourcesException extends Exception{
    public InsufficientResourcesException(){
        super();
    }
    public InsufficientResourcesException(String s){
        super(s);
    }
}

