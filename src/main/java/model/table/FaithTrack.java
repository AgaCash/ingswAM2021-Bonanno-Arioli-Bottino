package model.table;

import clientModel.table.LightFaithBox;
import clientModel.table.LightFaithTrack;
import utilities.JsonParser;

import java.util.ArrayList;

public class FaithTrack {
    private ArrayList<FaithBox> box ;
    private FaithBox actualBox;

    /**
     * loads the faith boxes from a json file
     */
    public FaithTrack() {
        box = new JsonParser("faithBox.json").getFaithBoxes();
        actualBox = box.get(0);
    }

    public FaithBox getFaithBox(){
        return this.actualBox;
    }

    /**
     * method that advances player's position in the faith track
     *
     * //@param playerBox   current player position in the track
     * @return new player position in the track
     */
    public FaithBox faithAdvance() {
        FaithBox nextBox;
        int pos;
        pos = this.actualBox.getPosition();
        if(pos<box.size()-1) {
            nextBox = this.box.get(pos + 1);
            this.actualBox = nextBox;
        }
        return this.actualBox;
    }

    /**Converts the current FaithTrack state in a LightFaithTrack instance
     * @return a LightFaithTrack instance
     */
    public LightFaithTrack convert(){
        ArrayList<LightFaithBox> newTrack = new ArrayList<>();
        for(FaithBox box : this.box){
            newTrack.add(box.convert());
        }

        LightFaithTrack newFTrack = new LightFaithTrack();
        newFTrack.setTrack(newTrack);
        newFTrack.setCurrentPos(this.actualBox.getPosition());
        return newFTrack;
    }


}