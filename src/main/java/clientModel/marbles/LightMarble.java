package clientModel.marbles;

import clientModel.colour.LightColour;

/**
 * LightModel copy of Model's Marble
 */
public enum LightMarble {
    BLUE("blue"),
    GREY("grey"),
    PURPLE("purple"),
    RED("red"),
    WHITE("white"),
    YELLOW("yellow");

    private transient String fileName;

    /**It's the reference to Marble's image filename
     * @param col its colour
     */
    LightMarble(String col){
        fileName = col+".png";
    }

    /**Returns the Marble's image filename for GUI
     * @return a String containing the filename
     */
    public String getFileName() {
        return fileName;
    }

    /**Method to print in CLI
     * @return a String
     */
    @Override
    public String toString(){
        switch(this){
            case BLUE: return LightColour.BLUE +"●"+ LightColour.WHITE;
            case GREY: return LightColour.BLACK+"●"+ LightColour.WHITE;
            case PURPLE: return LightColour.PURPLE +"●" +LightColour.WHITE;
            case RED: return LightColour.RED+"●" +LightColour.WHITE;
            case WHITE: return LightColour.WHITE+"●" +LightColour.WHITE;
            case YELLOW: return LightColour.YELLOW+"●"+LightColour.WHITE;
        }
        return null;
    }
}
