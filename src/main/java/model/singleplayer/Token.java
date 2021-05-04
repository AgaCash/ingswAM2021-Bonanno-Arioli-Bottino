package model.singleplayer;

import model.cards.DevelopmentCard;
import model.colour.Colour;
import model.table.DevelopmentBoard;

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
        this.shuffle = (blackCrossFaithPoints == 1);
        this.isAboutLorenzo = true;
    }

    public int getTokenID() {
        return tokenID;
    }

    public Colour getColour() {
        return colour;
    }

    public int getRemoveQuantity(){ return removeQuantity;}

    public boolean getShuffle(){ return shuffle;}

    public int getBlackCrossFaithPoints(){ return blackCrossFaithPoints;}

    public boolean getIsAboutLorenzo() {
        return isAboutLorenzo;
    }


    /*private FaithBox lorenzoAction(FaithBox box, FaithTrack faithTrack){
        FaithBox nextLorenzoBox;
        nextLorenzoBox = faithTrack.faithAdvance()
    }
*/
    /** executes a token action by eliminating model.cards from the development board
     * @param developmentBoard passed to link Lorenzo with the development model.cards to discard
     */
    public void cardAction(DevelopmentBoard developmentBoard){
        DevelopmentCard d = developmentBoard.popCardFromDeckColour(colour);
        if(d == null){
            //GIOCO FINITO, GIOCATORE PERDE
            System.out.println("GIOCO FINITO, GIOCATORE PERDE");
        }
    }

    /*public void execute(DevelopmentBoard developmentBoard){
        if(isAboutLorenzo) {
            FaithBox nextLorenzoBox;
            nextLorenzoBox = faithTrack.faithAdvance(box, faithTrack);
        }
        else
            cardAction(developmentBoard);
    }
    */

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
