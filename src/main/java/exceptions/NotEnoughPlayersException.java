package exceptions;

/**
 * Exception thrown if Lobby Creator tries to start a MultiPlayer lobby without any other Users (
 */
public class NotEnoughPlayersException extends Exception {

    public NotEnoughPlayersException(){
            super();
        }

    public NotEnoughPlayersException(String s){
            super(s);
        }
}
