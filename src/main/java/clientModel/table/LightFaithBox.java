package clientModel.table;

import clientModel.colour.LightColour;

public class LightFaithBox {
    private boolean[] popeFlag = new boolean[3];
    private int points;
    private boolean actualPos = false;
    private boolean lorenzoPos = false;

    public int getPoints() {
        return points;
    }
    public void setPoints(int points) {
        this.points = points;
    }
    public boolean getActualPos(){
        return actualPos;
    }
    public void setPos(boolean pos){ this.actualPos = pos;}

    public void setLorenzoPos(boolean pos){this.lorenzoPos = pos;}
    public boolean getLorenzoPos(){
        return lorenzoPos;
    }

    public boolean[] getPopeFlag() {
        return popeFlag;
    }
    public void setPopeFlag(boolean flagOne, boolean flagTwo, boolean flagThree) {
        popeFlag[0] = flagOne;
        popeFlag[1] = flagTwo;
        popeFlag[2] = flagThree;
    }

    @Override
    public String toString(){
        String s = new String();
        if(actualPos && !lorenzoPos)
            s+= " "+ LightColour.RED+ "✝ ";
        else if(!actualPos && lorenzoPos)
            s+= " "+ LightColour.BLACK+ "✝ ";
        else if(lorenzoPos && actualPos)
            s+= ""+ LightColour.RED+ "✝"+" "+LightColour.BLACK+ "✝";
        else
            s+="   ";
        return s;
    }
}
