package exceptions;

public class GameAlreadyStartedException extends Exception{
    public GameAlreadyStartedException() {
    }

    public GameAlreadyStartedException(String message) {
        super(message);
    }
}
