package exceptions;

/**
 * Exception thrown by CardSlots if there's not any DevelopmentCard in the referred Slot
 */
public class EmptySlotException extends Exception{

    public EmptySlotException(){
        super();
    }

    public EmptySlotException(String s){
        super(s);
    }
}
