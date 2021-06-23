package clientModel.table;

import clientModel.colour.LightColour;

import java.util.ArrayList;

public class LightFaithTrack {
    private ArrayList<LightFaithBox> box = new ArrayList<>(25);
    private LightFaithBox actualPos;
    private LightFaithBox lorenzoPos;

    public void setTrack(ArrayList<LightFaithBox> box){
        this.box = box;
        actualPos = this.box.get(0);
        lorenzoPos = this.box.get(0);
    }

    public void setLorenzoPos(int pos){
        lorenzoPos.setLorenzoPos(false);
        lorenzoPos = box.get(pos);
        lorenzoPos.setLorenzoPos(true);
    }

    public void setCurrentPos(int position){
        actualPos.setPos(false);
        actualPos = box.get(position);
        actualPos.setPos(true);
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
