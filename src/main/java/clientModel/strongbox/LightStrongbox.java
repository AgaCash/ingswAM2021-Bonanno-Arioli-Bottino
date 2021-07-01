package clientModel.strongbox;


import clientModel.resources.LightResource;

import java.util.ArrayList;

/**
 * LightModel's copy of Strongbox instance in Model
 */
public class LightStrongbox {

    private ArrayList<LightResource> strongbox = new ArrayList<>();
    private ArrayList<LightResource> tmpStrongbox = new ArrayList<>();

    /**Updates the LightStrongbox with the new Model's Strongbox status
     * @param s a LightResource ArrayList representing the current Strongbox status
     */
    public void setStrongbox(ArrayList<LightResource> s){
        strongbox = s;
    }

    /**Updates the LightStrongbox Resource List that will be added in the next turn with the new Model's Strongbox Next Turn Resource List
     * @param t the LightResource ArrayList will be added in Model's Strongbox in the next turn
     */
    public void setTmpStrongbox(ArrayList<LightResource> t){
        tmpStrongbox = t;
    }

    /**Returns the current LightStrongbox status
     * @return a LightResource ArrayList
     */
    public ArrayList<LightResource> getStrongbox() {
        return strongbox;
    }

    /**Method to print LightStrongbox in CLI
     * @return a String
     */
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
