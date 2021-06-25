package exceptions;

public class EmptySlotException extends Exception{

    public EmptySlotException(){
        super();
    }

    public EmptySlotException(String s){
        super(s);
    }
}
