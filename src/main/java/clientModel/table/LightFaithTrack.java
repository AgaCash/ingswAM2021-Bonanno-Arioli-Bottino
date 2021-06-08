package clientModel.table;

import model.table.FaithBox;

import java.util.ArrayList;
//todo da rivedere?
public class LightFaithTrack {
    private ArrayList<LightFaithBox> box = new ArrayList<>(25);
    private FaithBox actualPos = new FaithBox();

    public ArrayList<LightFaithBox> getTrack(){
        return box;
    }
    public void setTrack(ArrayList<LightFaithBox> box){ this.box = box;}



    @Override
    public String toString(){
        String s = "\n";
        for(LightFaithBox box: this.box)
            s += box.toString();
        return s;
    }
}
