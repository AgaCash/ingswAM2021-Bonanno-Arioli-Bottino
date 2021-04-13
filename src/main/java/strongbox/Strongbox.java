package strongbox;

import resources.Resource;

import java.util.ArrayList;

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
    public boolean isPresent (ArrayList<Resource> res){
        boolean isPresent = true;
        ArrayList<Resource> clonedList = (ArrayList<Resource>) strongbox.clone();
        for(int pos = 0; res.get(pos) != null; pos++ )
        {
            if(clonedList.contains(res.get(pos))) {
                int index = clonedList.indexOf(res.get(pos));
                clonedList.remove(index);
            }
            else
                isPresent = false;
        }
        return isPresent;
    }
}
