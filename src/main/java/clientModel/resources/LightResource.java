package clientModel.resources;

import clientModel.colour.LightColour;

/**
 * LightModel copy of Model's Resource
 */
public enum LightResource {
    COIN,
    FAITH,
    SERVANT,
    SHIELD,
    STONE;

    /**Method to print in CLI. It's distinguished than toString for not broke the Message conversion in Server Controller
     * @return a String
     */
    public String toColoredString(){
        switch(this){
            case COIN: return LightColour.YELLOW +"CO"+LightColour.WHITE;
            case FAITH: return LightColour.RED+"FA" +LightColour.WHITE;
            case SERVANT: return LightColour.PURPLE +"SE" +LightColour.WHITE;
            case SHIELD: return LightColour.BLUE + "SH" + LightColour.WHITE;
            case STONE: return LightColour.BLACK+"ST"+ LightColour.WHITE;
        }
        return null;
    }
}
