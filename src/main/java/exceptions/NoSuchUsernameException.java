package exceptions;

public class NoSuchUsernameException extends Exception {

    public NoSuchUsernameException(){
        super();
    }

    public NoSuchUsernameException(String s){
        super(s);
    }
}
