package exceptions;

/**
 * Exception thrown if Lobby can't ben joined by any other User (the size is max 4 members)
 */
public class LobbyFullException extends Exception{
    public LobbyFullException() {
    }

    public LobbyFullException(String message) {
        super(message);
    }
}
