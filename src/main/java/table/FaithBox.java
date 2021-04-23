package table;

public class FaithBox {
    private int position;
    private boolean[] popeFlag = new boolean[3];
    private int points;

    public void setPosition (int position){
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
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
        String s = "FaithBox";
        s+="\n"+position;
        s+="\n"+points;
        s+="\n"+popeFlag[0]+" "+popeFlag[1]+" "+popeFlag[2];
        s+="\n---";
        return s;
    }
}
