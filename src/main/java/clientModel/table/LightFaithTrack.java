package clientModel.table;

import clientModel.colour.LightColour;

import java.util.ArrayList;
//todo da rivedere?
public class LightFaithTrack {
    private ArrayList<LightFaithBox> box = new ArrayList<>(25);
    private LightFaithBox actualPos;

    //public ArrayList<LightFaithBox> getTrack(){
    //  return box;
    //}
    public void setTrack(ArrayList<LightFaithBox> box){
        this.box = box;
        actualPos = this.box.get(0);
    }

    public void setLorenzoPos(int pos){
        this.box.get(pos).setLorenzoPos();
    }

    public void setCurrentPos(int position){
        actualPos.setPos(false);
        actualPos = box.get(position);
        actualPos.setPos(true);
    }

    public int getCurrentPos(){
        return this.box.indexOf(this.actualPos);
    }



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
