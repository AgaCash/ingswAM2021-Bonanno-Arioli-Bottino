package exceptions;

public class UnusableCardException extends Exception{

    public UnusableCardException(){
        super();
    }

    public UnusableCardException(String s){
        super(s);
    }
}
