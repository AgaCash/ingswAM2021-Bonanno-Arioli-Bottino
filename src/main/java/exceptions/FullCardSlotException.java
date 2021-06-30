package exceptions;

/**
 * Exception thrown if in a slot in CardSlots can't be added any other DevelopmentCard
 */
public class FullCardSlotException extends Exception{

    public FullCardSlotException(){
        super();
    }

    public FullCardSlotException(String s){
        super(s);
    }
}
