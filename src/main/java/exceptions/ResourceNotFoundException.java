package exceptions;

public class ResourceNotFoundException extends Exception{
    public ResourceNotFoundException(){
        super();
    }

    public ResourceNotFoundException(String s){
        super(s);
    }
}
