package clientModel.colour;

public enum LightColour {
    BLUE,
    GREEN,
    PURPLE,
    WHITE,
    YELLOW;

    @Override
    public String toString(){
        switch(this){
            case BLUE: return "\u001B[34m";
            case GREEN: return "\u001B[32m";
            //case GREY: return "\u001B[30m"+"●"+ "\u001B[0m";
            case PURPLE: return "\u001B[35m";
           // case RED: return "\u001B[31m"+"●" +"\u001B[0m";
            case WHITE: return "\u001B[0m";
            case YELLOW: return "\u001B[33m";
        }
        return null;
    }

}
