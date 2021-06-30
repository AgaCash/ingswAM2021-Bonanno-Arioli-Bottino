package exceptions;

/**
 * Exception thrown if Player asked for an action can't be done in the current turn
 */
public class InvalidActionException extends Exception{

        public InvalidActionException(){
            super();
        }

        public InvalidActionException(String s){
            super(s);
        }


}
