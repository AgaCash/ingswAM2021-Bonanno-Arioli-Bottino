package exceptions;

/**
 * Exception thrown if a there was a trying to add a DevelopmentCard over a non correct level DevelopmentCard
 * in a CardSlot
 * (or a non-level one DevelopmentCard in an empty Slot)
 */
public class NonCorrectLevelCardException extends Exception{

    public NonCorrectLevelCardException(){
        super();
    }

    public NonCorrectLevelCardException(String s){
        super(s);
    }
}
