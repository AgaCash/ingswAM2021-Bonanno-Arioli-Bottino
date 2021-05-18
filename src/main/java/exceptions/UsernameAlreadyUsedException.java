package exceptions;

public class UsernameAlreadyUsedException extends Exception{

    public UsernameAlreadyUsedException(){
        super();
    }

    public UsernameAlreadyUsedException(String s){
        super(s);
    }
}
