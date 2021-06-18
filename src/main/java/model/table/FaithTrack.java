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
        box = new JsonParser("src/main/resources/faithBox.json").getFaithBoxes();
        actualBox = box.get(0);
    }

    /*public FaithBox faithAdvance (FaithBox playerBox, FaithTrack playerTrack, int advance){
        FaithBox nextBox;
        int pos;
        pos = playerBox.getPosition();
        nextBox = playerTrack.box.get(pos+advance);
        return nextBox;
    }

*/
    public FaithBox getFaithBox(){
        return this.actualBox;
    }

    /**
     * method that advances player's position in the faith track
     *
     * @param playerBox   current player position in the track
     * @return new player position in the track
     */
    public FaithBox faithAdvance(FaithBox playerBox) {
        FaithBox nextBox;
        int pos;
        pos = playerBox.getPosition();
        if(pos<25) {
            nextBox = this.box.get(pos + 1);
            this.actualBox = nextBox;
        }
        return this.actualBox;
    }

    public LightFaithTrack convert(){
        ArrayList<LightFaithBox> newTrack = new ArrayList<>();
        for(FaithBox box : this.box){
            LightFaithBox newBox = box.convert();
            if(box.equals(this.actualBox))
                newBox.setPos(true);
            newTrack.add(newBox);
        }

        LightFaithTrack newFTrack = new LightFaithTrack();
        newFTrack.setTrack(newTrack);
        return newFTrack;
    }


}