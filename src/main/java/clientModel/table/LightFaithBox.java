package clientModel.table;

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

    public void setPos(boolean pos){ this.actualPos = pos;}

    public void setLorenzoPos(){this.lorenzoPos = true;}

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
        if(actualPos && !lorenzoPos)
            return "| * ";
        else if(!actualPos && lorenzoPos)
            return "| L ";
        else if(lorenzoPos && actualPos)
            return "|* L";
        return "|   ";
    }
}
