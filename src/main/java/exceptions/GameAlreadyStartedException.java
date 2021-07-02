package exceptions;

/**
 * Exception thrown if a User tries to join to a Lobby whose Game is already started
 */
public class GameAlreadyStartedException extends Exception{
    public GameAlreadyStartedException() {
    }

    public GameAlreadyStartedException(String message) {
        super(message);
    }
}
