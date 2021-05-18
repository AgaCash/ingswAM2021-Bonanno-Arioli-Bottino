package clientModel.strongbox;


import clientModel.resources.LightResource;

import java.util.ArrayList;

public class LightStrongbox {

    private ArrayList<LightResource> strongbox = new ArrayList<LightResource>();
    private ArrayList<LightResource> tmpStrongbox = new ArrayList<>();
/*
    public void addResource (Resource resource){
        strongbox.add(resource);
    }

    public void removeResource (Resource resource) { strongbox.remove(resource); }

 */

    public LightResource getResource (int whichOne){
        LightResource res;
        res = strongbox.get(whichOne);
        return res;
    }
/*
    public static boolean isPresent(ArrayList<LightResource> res){
        ArrayList<LightResource> clonedStrongbox = (ArrayList<LightResource>) strongbox.clone();
        for(LightResource r : res) {
            boolean found = false;
            for (int i = 0; i < clonedStrongbox.size(); i++)
                if (clonedStrongbox.get(i) == r) {
                    clonedStrongbox.remove(i);
                    res.remove(i);
                    found = true;
                    break;
                }
            if(!found)
                return false;
        }
        return true;
    }

 */
}
