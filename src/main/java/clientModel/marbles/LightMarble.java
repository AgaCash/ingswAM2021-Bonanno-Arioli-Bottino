package clientModel.marbles;

import model.marbles.Marble;

public enum LightMarble {
    BLUE("BLUE"),
    GREY("GREY"),
    PURPLE("PURPLE"),
    RED("RED"),
    WHITE("WHITE"),
    YELLOW("YELLOW");

    private transient String fileName;

    private LightMarble(String col){
        fileName = col+".png";
    }

    public String getFileName() {
        return fileName;
    }

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
