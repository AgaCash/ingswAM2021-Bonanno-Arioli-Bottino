package exceptions;

public class EmptyDeckException extends Exception {

    public EmptyDeckException(){
        super();
    }

    public EmptyDeckException(String s){
        super(s);
    }
}
