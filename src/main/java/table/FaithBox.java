package table;

public class FaithBox {
    private int position;
    private boolean flagPopeOne;
    private boolean isFlagPopeTwo;
    private boolean flagPopeThree;
    private int points;

    public void setPosition (int position){
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public boolean getIsFlagPopeOne() {
        return flagPopeOne;
    }

    public void setFlagPopeOne(boolean flagPopeOne) {
        this.flagPopeOne = flagPopeOne;
    }

    public boolean getIsFlagPopeTwo() {
        return isFlagPopeTwo;
    }

    public void setFlagPopeTwo(boolean flagPopeTwo) {
        isFlagPopeTwo = flagPopeTwo;
    }

    public boolean getIsFlagPopeThree() {
        return flagPopeThree;
    }

    public void setFlagPopeThree(boolean flagPopeThree) {
        this.flagPopeThree = flagPopeThree;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
