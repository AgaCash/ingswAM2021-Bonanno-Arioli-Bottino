package exceptions;

public class InvalidMessageException extends Exception{

    public InvalidMessageException(){
        super();
    }
    public InvalidMessageException(String s){
        super(s);
    }
}

