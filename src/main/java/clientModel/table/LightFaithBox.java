package clientModel.table;

public class LightFaithBox {
    private boolean[] popeFlag = new boolean[3];
    private int points;
    private boolean actualPos = false;

    public int getPoints() {
        return points;
    }
    public void setPoints(int points) {
        this.points = points;
    }

    public void setPos(boolean pos){ this.actualPos = pos;}

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
        if(actualPos)
            return "| * ";
        return "|   ";
    }
}
