package clientModel.table;

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



    @Override
    public String toString(){
        String s = "\n|";
        for(LightFaithBox box: this.box)
            s += box.toString();
        s+="|";
        return s;
    }
}
