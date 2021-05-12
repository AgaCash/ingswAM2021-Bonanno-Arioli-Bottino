package exceptions;

public class NonCorrectLevelCardException extends Exception{

    public NonCorrectLevelCardException(){
        super();
    }

    public NonCorrectLevelCardException(String s){
        super(s);
    }
}
