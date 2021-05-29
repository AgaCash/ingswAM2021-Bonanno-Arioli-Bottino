package clientModel.table;

import java.util.ArrayList;

public class LightFaithTrack {
    private ArrayList<LightFaithBox> box = new ArrayList<>(25);

    public ArrayList<LightFaithBox> getTrack(){
        return box;
    }
    public void setTrack(ArrayList<LightFaithBox> box){ this.box = box;}
}
