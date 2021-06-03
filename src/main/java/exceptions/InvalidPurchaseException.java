package exceptions;

public class InvalidPurchaseException extends Exception{

        public InvalidPurchaseException(){
            super();
        }

        public InvalidPurchaseException(String s){
            super(s);
        }


}
