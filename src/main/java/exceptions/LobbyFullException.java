package exceptions;

public class LobbyFullException extends Exception{
    public LobbyFullException() {
    }

    public LobbyFullException(String message) {
        super(message);
    }
}
