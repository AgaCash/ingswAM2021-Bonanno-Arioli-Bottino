package exceptions;

public class NotEnoughPlayersException extends Exception {

    public NotEnoughPlayersException(){
            super();
        }

    public NotEnoughPlayersException(String s){
            super(s);
        }
}
