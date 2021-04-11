import resources.* ;
import java.util.ArrayList;
import java.util.Scanner ;

public class Strongbox {

    private ArrayList strongbox = new ArrayList<Resource>();

    public void addResource (Resource resource){
        strongbox.add(resource);
    }
    public void removeResource (Resource resource){
        boolean result;
        result=strongbox.remove(resource);
        if(!result)
            System.out.println("resource not found");
    }
}
