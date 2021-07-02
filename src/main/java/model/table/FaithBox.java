package model.table;

import clientModel.table.LightFaithBox;

/**
 * Represents a box in the FaithTrack
 */
public class FaithBox {
    private int position;
    private boolean[] popeFlag = new boolean[3];
    private int points;

    /**Returns the box position in the FaithTrack
     * @return an int
     */
    public int getPosition() {
        return position;
    }

    /**Returns the victory points collected by Player if he reach this FaithBox
     * @return an int
     */
    public int getPoints() {
        return points;
    }

    /**Sets the FaithBox victory points
     * @param points an int
     */
    public void setPoints(int points) {
        this.points = points;
    }

    /**Return true if FaithBox contains a pope flag, false if not
     * @return a boolean
     */
    public boolean[] getPopeFlag() {
        return popeFlag;
    }

    /**Sets at the Game starting eventual pope Flags for the box
     * @param flagOne true if it's first pope flag's box, false if not
     * @param flagTwo true if it's second pope flag's box, false if not
     * @param flagThree true if it's third pope flag's box, false if not
     */
    public void setPopeFlag(boolean flagOne, boolean flagTwo, boolean flagThree) {
        popeFlag[0] = flagOne;
        popeFlag[1] = flagTwo;
        popeFlag[2] = flagThree;
    }

    /**Just 4 test
     * @return a String
     */
    @Override
    public String toString(){
        String s = "FaithBox";
        s+="\n"+position;
        s+="\n"+points;
        s+="\n"+popeFlag[0]+" "+popeFlag[1]+" "+popeFlag[2];
        s+="\n---";
        return s;
    }

    /**Converts the current FaithBox state in a LightFaithBox instance for LightModel
     * @return a LightFaithBox instance
     */
    public LightFaithBox convert(){
        LightFaithBox newBox = new LightFaithBox();
        newBox.setPopeFlag(this.popeFlag[0], this.popeFlag[1], this.popeFlag[2]);
        newBox.setPoints(this.points);
        return newBox;
    }
}
