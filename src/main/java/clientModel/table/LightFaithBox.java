package clientModel.table;

import clientModel.colour.LightColour;

/**
 * LightModel's copy of FaithBox in Model.
 */
public class LightFaithBox {
    private boolean[] popeFlag = new boolean[3];
    private int points;
    private boolean actualPos = false;
    private boolean lorenzoPos = false;

    /**Returns the LightFaithBox's victory points
     * @return an int
     */
    public int getPoints() {
        return points;
    }

    /**Sets the LightFaithBox's victory points value
     * @param points an int
     */
    public void setPoints(int points) {
        this.points = points;
    }

    /**Returns true if LightFaithBox is the current Player's position
     * @return a boolean
     */
    public boolean getActualPos(){
        return actualPos;
    }

    /**Sets the LightFaithBox instance as actual Player's position on the FaithTrack
     * @param pos true if it's Player's current position, false if not
     */
    public void setPos(boolean pos){ this.actualPos = pos;}

    /**Sets the LightFaithBox instance as actual Lorenzo's position on the FaithTrack
     * @param pos true if it's Lorenzo current position, false if not
     */
    public void setLorenzoPos(boolean pos){this.lorenzoPos = pos;}

    /**Returns true if LightFaithBox is the current Lorenzo's position
     * @return a boolean
     */
    public boolean getLorenzoPos(){
        return lorenzoPos;
    }

    public void setPopeFlag(boolean flagOne, boolean flagTwo, boolean flagThree) {
        popeFlag[0] = flagOne;
        popeFlag[1] = flagTwo;
        popeFlag[2] = flagThree;
    }

    /**Method to print LightFaithBox in CLI
     * @return a String
     */
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
