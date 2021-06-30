package exceptions;

/**
 * Exception thrown by DevelopmentBoard if Deck don't contain any DevelopmentCard in it
 */
public class EmptyDeckException extends Exception {

    public EmptyDeckException(){
        super();
    }

    public EmptyDeckException(String s){
        super(s);
    }
}
