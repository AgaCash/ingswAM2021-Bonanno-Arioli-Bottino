package exceptions;

public class FullWarehouseException extends Exception {
    public FullWarehouseException(){
        super();
    }
    public FullWarehouseException(String s){
        super(s);
    }
}
