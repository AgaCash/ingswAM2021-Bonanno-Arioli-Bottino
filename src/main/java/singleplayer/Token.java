package singleplayer;

import colour.Colour;

public class Token {
    private int tokenID;
    private Colour colour;
    private int removeQuantity;
    private int blackCrossFaithPoints;
    private boolean shuffle;

    public Token(int tokenID, Colour colour, int removeQuantity) {
        this.tokenID = tokenID;
        this.colour = colour;
        this.removeQuantity = removeQuantity;
        this.blackCrossFaithPoints = 0;
        this.shuffle = false;
    }

    public Token(int tokenID, int blackCrossFaithPoints) {
        this.tokenID = tokenID;
        this.blackCrossFaithPoints = blackCrossFaithPoints;
        this.shuffle = blackCrossFaithPoints == 1;
    }

    public int getTokenID() {
        return tokenID;
    }

    public Colour getColour() {
        return colour;
    }

    public void execute(){

        // executes the action declared by the token, when picked

    }

    @Override
    public String toString(){
        String s = "Token---";
        s+="\n"+tokenID;
        s+="\n"+colour;
        s+="\n"+removeQuantity;
        s+="\n"+blackCrossFaithPoints;
        s+="\n"+shuffle;
        s+="\n---";
        return s;
    }
}
