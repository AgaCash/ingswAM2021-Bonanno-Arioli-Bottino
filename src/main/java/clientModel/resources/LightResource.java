package clientModel.resources;

/**
 * LightModel copy of Model's Resource
 */
public enum LightResource {
    COIN,
    FAITH,
    SERVANT,
    SHIELD,
    STONE;

    /**Method to print in CLI
     * @return a String
     */
    public String toColoredString(){
        switch(this){
            case COIN: return "\u001B[33m"+"CO"+"\u001B[0m";
            case FAITH: return "\u001B[31m"+"FA" +"\u001B[0m";
            case SERVANT: return "\u001B[35m" +"SE" +"\u001B[0m";
            case SHIELD: return "\u001B[34m" + "SH" + "\u001B[0m";
            case STONE: return "\u001B[30m"+"ST"+ "\u001B[0m";
        }
        return null;
    }
}
