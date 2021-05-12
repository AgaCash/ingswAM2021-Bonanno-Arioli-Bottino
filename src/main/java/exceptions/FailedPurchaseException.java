package exceptions;

public class FailedPurchaseException extends Exception{
    public FailedPurchaseException(){
        super();
    }

    public FailedPurchaseException(String s){
        super(s);
    }
}
