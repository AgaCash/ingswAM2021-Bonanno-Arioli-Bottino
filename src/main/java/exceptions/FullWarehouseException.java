package exceptions;

/**
 * Exception thrown if in WarehouseDepot can't be added a Resource
 */
public class FullWarehouseException extends Exception {
    public FullWarehouseException(){
        super();
    }
    public FullWarehouseException(String s){
        super(s);
    }
}
