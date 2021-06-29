package clientModel.strongbox;


import clientModel.resources.LightResource;

import java.util.ArrayList;

public class LightStrongbox {

    private ArrayList<LightResource> strongbox = new ArrayList<>();
    private ArrayList<LightResource> tmpStrongbox = new ArrayList<>();

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

    public ArrayList<LightResource> getStrongbox() {
        return strongbox;
    }

    @Override
    public String toString(){
        String s = "\nSTRONGBOX: \n";
        for(LightResource res: strongbox)
            s+=res.toColoredString()+" ";
        if(!tmpStrongbox.isEmpty()) {
            s += "\navailable by the next turn: ";
            for (LightResource res : tmpStrongbox)
                s += res.toColoredString() + " ";
        }
        return s;
    }

}
