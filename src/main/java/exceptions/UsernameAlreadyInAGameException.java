package exceptions;
//todo aga
public class UsernameAlreadyInAGameException extends Exception{

    public UsernameAlreadyInAGameException(){
        super();
    }

    public UsernameAlreadyInAGameException(String s){
        super(s);
    }
}
