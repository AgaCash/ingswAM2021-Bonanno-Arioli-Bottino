package exceptions;

/**
 * Exception thrown if there's not a specific Resource instance in Strongbox or WarehouseDepot
 */
public class ResourceNotFoundException extends Exception{
    public ResourceNotFoundException(){
        super();
    }

    public ResourceNotFoundException(String s){
        super(s);
    }
}
