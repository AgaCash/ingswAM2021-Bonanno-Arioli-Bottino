package singleplayer;

import cards.DevelopmentCard;
import colour.Colour;
import table.DevelopmentBoard;
import table.FaithTrack;

public class Token {
    private int tokenID;
    private Colour colour;
    private int removeQuantity;
    private int blackCrossFaithPoints;
    private boolean shuffle;
    private boolean isAboutLorenzo;

    public Token(int tokenID, Colour colour, int removeQuantity) {
        this.tokenID = tokenID;
        this.colour = colour;
        this.removeQuantity = removeQuantity;
        this.blackCrossFaithPoints = 0;
        this.shuffle = false;
        this.isAboutLorenzo = false;
    }

    public Token(int tokenID, int blackCrossFaithPoints) {
        this.tokenID = tokenID;
        this.blackCrossFaithPoints = blackCrossFaithPoints;
        this.shuffle = blackCrossFaithPoints == 1;
        this.isAboutLorenzo = true;
    }

    public int getTokenID() {
        return tokenID;
    }

    public Colour getColour() {
        return colour;
    }

    private void lorenzoAction(FaithTrack faithTrack){
        //faithtrack.qualcosaAvanza(blackCrossFaithPoints);
    }

    private void cardAction(DevelopmentBoard developmentBoard){
        DevelopmentCard d = developmentBoard.popCardFromDeckColour(colour);
        if(d == null){
            //GIOCO FINITO, GIOCATORE PERDE
            System.out.println("GIOCO FINITO, GIOCATORE PERDE");
        }
    }

    public void execute(FaithTrack faithTrack, DevelopmentBoard developmentBoard){
        if(isAboutLorenzo)
            lorenzoAction(faithTrack);
        else
            cardAction(developmentBoard);
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
