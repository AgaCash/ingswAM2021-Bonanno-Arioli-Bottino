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
    public void setStrongbox(ArrayList<LightResource> s){
        strongbox = s;
    }

    public void setTmpStrongbox(ArrayList<LightResource> t){
        tmpStrongbox = t;
    }

    public LightResource getResource (int whichOne){
        LightResource res;
        res = strongbox.get(whichOne);
        return res;
    }

    @Override
    public String toString(){
        String s = "\nSTRONGBOX: \n";
        for(LightResource res: strongbox)
            s+=res.toColoredString()+" ";
        if(!tmpStrongbox.isEmpty()) {
            s += "\n disponibili dal prossimo turno: ";
            for (LightResource res : tmpStrongbox)
                s += res.toColoredString() + " ";
        }
        return s;
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
