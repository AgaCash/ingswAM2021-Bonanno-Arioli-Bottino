package exceptions;

public class MessageNotSuccededException extends Exception{
    public MessageNotSuccededException() {
    }

    public MessageNotSuccededException(String message) {
        super(message);
    }
}
