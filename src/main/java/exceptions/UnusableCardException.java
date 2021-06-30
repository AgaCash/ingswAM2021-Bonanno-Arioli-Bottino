package exceptions;

/**
 * Exception thrown if a Card can't be used for a specific action:
 * - a DevelopmentCard was already used for a production in the current turn
 * - a LeaderCard can't be used
 */
public class UnusableCardException extends Exception{

    public UnusableCardException(){
        super();
    }

    public UnusableCardException(String s){
        super(s);
    }
}
