package model.singleplayer;

import exceptions.EmptyDeckException;
import model.cards.DevelopmentCard;
import model.colour.Colour;
import model.table.DevelopmentBoard;

/**
 * The Lorenzo's tokens which it does its actions in single player game session
 */
public class Token {
    private int tokenID;
    private Colour colour;
    private int removeQuantity;
    private int blackCrossFaithPoints;
    private boolean shuffle;
    private boolean isAboutLorenzo;


    /**just for tests
     * Tokens are build by JSON
     */
    public Token(int tokenID, Colour colour, int removeQuantity) {
        this.tokenID = tokenID;
        this.colour = colour;
        this.removeQuantity = removeQuantity;
        this.blackCrossFaithPoints = 0;
        this.shuffle = false;
        this.isAboutLorenzo = false;
    }

    /**just for tests
     * Tokens are build by JSON
     */
    public Token(int tokenID, int blackCrossFaithPoints) {
        this.tokenID = tokenID;
        this.blackCrossFaithPoints = blackCrossFaithPoints;
        this.shuffle = (blackCrossFaithPoints == 1);
        this.isAboutLorenzo = true;
    }

    /**Returns token's ID
     * @return an int
     */
    public int getTokenID() {
        return tokenID;
    }

    /**Returns token's colour
     * @return a Colour instance
     */
    public Colour getColour() {
        return colour;
    }

    /**The number of DevelopmentCard will be removed by DevelopmentBoard
     * @return an int
     */
    public int getRemoveQuantity(){ return removeQuantity;}

    /**Return true if token will be activate all tokens shuffling
     * @return a boolean
     */
    public boolean getShuffle(){ return shuffle;}

    /**Return the number of FaithBox Lorenzo will advance on its FaithTrack
     * @return an int
     */
    public int getBlackCrossFaithPoints(){ return blackCrossFaithPoints;}

    /**Return true if Token is about Faith advancing or shuffling, false if Token is about DevelopmentCard picking
     * @return a boolean
     */
    public boolean getIsAboutLorenzo() {
        return isAboutLorenzo;
    }

    /**Executes a token action by eliminating cards from the development board.
     * If test card is null, then Lorenzo has picked the last developmentCard by colour: Game is over and Lorenzo won
     * @param developmentBoard passed to link Lorenzo with the development cards to discard
     */
    public DevelopmentCard cardAction(DevelopmentBoard developmentBoard){
        DevelopmentCard card, test;
        try {
            card =  developmentBoard.popCardFromDeckColour(colour);
            test = developmentBoard.getDeck(colour).getCard();
            return card;
        }catch(EmptyDeckException e){
            return null;
        }
    }

    /**just 4 test
     * @return a String
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
