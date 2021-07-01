package clientModel.table;

import clientModel.colour.LightColour;

import java.util.ArrayList;

/**
 * LightModel's copy of DevelopmentBoard in Model.
 * In single player mode the Player's FaithTrack contains also the Lorenzo's current position
 */
public class LightFaithTrack {
    private ArrayList<LightFaithBox> box = new ArrayList<>(25);
    private LightFaithBox actualPos;
    private LightFaithBox lorenzoPos;

    /**Returns the entire LightFaithTrack
     * @return a LightFaithBox ArrayList
     */
    public ArrayList<LightFaithBox> getBox() {
        return box;
    }

    /**Updates the LightFaithTrack with the Model's FaithTrack current status
     * @param box a LightFaithBox ArrayLIst
     */
    public void setTrack(ArrayList<LightFaithBox> box){
        this.box = box;
        actualPos = this.box.get(0);
        lorenzoPos = this.box.get(0);
    }

    /**Sets the Lorenzo's FaithTrack position in Model on the LightModel's LightFaithTrack
     * @param pos the current Lorenzo position on its FaithTrack in the Model
     */
    public void setLorenzoPos(int pos){
        this.lorenzoPos.setLorenzoPos(false);
        this.lorenzoPos = box.get(pos);
        this.lorenzoPos.setLorenzoPos(true);
    }

    /**Sets the Player's FaithTrack position in Model on the LightModel's LightFaithTrack
     * @param position the current Player position on its FaithTrack in the Model
     */
    public void setCurrentPos(int position){
        actualPos.setPos(false);
        actualPos = box.get(position);
        actualPos.setPos(true);
    }

    /**Method to print LightFaithTrack in CLI
     * @return a String
     */
    @Override
    public String toString(){
        String s = "FAITH TRACK:\n|";

        for(int i=0; i<25; i++) {
            LightColour color = LightColour.WHITE;
            if (i == 8 || i == 16 || i == 24)
                color = LightColour.RED;
            if((i>=5 && i<=7) || (i>=12 && i<=15) || (i>=19 && i<=23))
                color = LightColour.YELLOW;
            s +=color+"|"+box.get(i).toString()+color+"|"+LightColour.WHITE;
        }
        s+="|";
        return s;
    }
}
