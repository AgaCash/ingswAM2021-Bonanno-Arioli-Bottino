package clientModel.marbles;

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

    LightMarble(String col){
        fileName = col+".png";
    }

    public String getFileName() {
        return fileName;
    }

    /**Method to print in CLI
     * @return a String
     */
    @Override
    public String toString(){
        switch(this){
            case BLUE: return "\u001B[34m"+"●"+ "\u001B[0m";
            case GREY: return "\u001B[30m"+"●"+ "\u001B[0m";
            case PURPLE: return "\u001B[35m" +"●" +"\u001B[0m";
            case RED: return "\u001B[31m"+"●" +"\u001B[0m";
            case WHITE: return "\u001B[0m"+"●" +"\u001B[0m";
            case YELLOW: return "\u001B[33m"+"●"+"\u001B[0m";
        }
        return null;
    }
}
