package clientModel.singleplayer;


import clientModel.colour.LightColour;

public class LightToken {

    private int tokenID;
    private LightColour colour;
    private int removeQuantity;
    private int blackCrossFaithPoints;
    private boolean shuffle;
    private boolean isAboutLorenzo;

    public LightToken(int tokenID, LightColour colour, int removeQuantity) {
        this.tokenID = tokenID;
        this.colour = colour;
        this.removeQuantity = removeQuantity;
        this.blackCrossFaithPoints = 0;
        this.shuffle = false;
        this.isAboutLorenzo = false;
    }

    public LightToken(int tokenID, int blackCrossFaithPoints) {
        this.tokenID = tokenID;
        this.blackCrossFaithPoints = blackCrossFaithPoints;
        this.shuffle = (blackCrossFaithPoints == 1);
        this.isAboutLorenzo = true;
    }

    public int getTokenID() {
        return tokenID;
    }

    public LightColour getColour() {
        return colour;
    }

    public int getRemoveQuantity(){ return removeQuantity;}

    public boolean getShuffle(){ return shuffle;}

    public int getBlackCrossFaithPoints(){ return blackCrossFaithPoints;}

    public boolean getIsAboutLorenzo() {
        return isAboutLorenzo;
    }
}
