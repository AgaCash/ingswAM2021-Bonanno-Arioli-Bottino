package clientModel.colour;

/**
 * LightModel copy of Model's Colour
 */
public enum LightColour {
    BLACK,
    BLUE,
    GREEN,
    PURPLE,
    RED,
    WHITE,
    YELLOW;

    /**Method to print in CLI
     * @return a String
     */
    @Override
    public String toString(){
        switch(this){
            case BLACK: return "\u001B[30m";
            case BLUE: return "\u001B[34m";
            case GREEN: return "\u001B[32m";
            case PURPLE: return "\u001B[35m";
            case RED: return "\u001B[31m";
            case WHITE: return "\u001B[0m";
            case YELLOW: return "\u001B[33m";
        }
        return null;
    }

}
