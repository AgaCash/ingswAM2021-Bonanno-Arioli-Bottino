package model.singleplayer;

import clientModel.cards.LightDevelopmentCard;
import clientModel.singleplayer.LightLorenzo;
import model.cards.DevelopmentCard;
import model.table.DevelopmentBoard;
import model.table.FaithBox;
import model.table.FaithTrack;
import utilities.JsonParser;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Class that implements Lorenzo, the CPU in single player game session
 */
public class Lorenzo {
    private FaithTrack faithTrack = new FaithTrack();
    private FaithBox faithBox = faithTrack.getFaithBox();
    private ArrayList<Token> tokens;
    private DevelopmentBoard developmentBoard;
    private String lorenzoLastAction = new String();
    private boolean gameIsOver = false;
    private ArrayList<LightDevelopmentCard> cards;
    private int position;
    private int shuffle;

    /** creation of Lorenzo's deck of tokens and development board
     * @param developmentBoard connects Lorenzo with the development board
     */
    public Lorenzo(DevelopmentBoard developmentBoard){
        tokens = new JsonParser("tokensList.json").getTokens();
        this.shuffle();
        this.developmentBoard = developmentBoard;
        this.cards = new ArrayList<>();
        this.shuffle = 0;
        this.position = 0;
    }

    /**
     * Shuffles the Lorenzo's tokens
     */
    private void shuffle(){
        Collections.shuffle(tokens);
    }

    /**Returns the current FaithBox where Lorenzo is
     * @return a FaithBox instance
     */
    public FaithBox getFaithBox() {
        return faithBox;
    }

    /**Returns all the Lorenzo's tokens
     * @return a Token ArrayList
     */
    public ArrayList<Token> getTokens(){
        return this.tokens;
    }

    /**
     * Implements all the Lorenzo's actions in its turn. It picks and execute the Token
     * Saves the action as a String in lorenzoLastAction attribute that will be sent to the User
     * if Lorenzo win the Game
     */
    public void pick () {
        this.lorenzoLastAction = "";
        Token token = tokens.get(0);
        Token tmp;
        if (!(token.getIsAboutLorenzo())) {
            for (int i = 0; i < token.getRemoveQuantity(); i++) {
                DevelopmentCard card  = token.cardAction(developmentBoard);
                if (card == null){
                    this.lorenzoLastAction+="GAME OVER: LORENZO PICKED THE LAST CARD!";
                    this.gameIsOver = true;
                    return;
                }
                else {
                    this.lorenzoLastAction += " > Lorenzo has removed the card:\n" + card.convert().toString();
                    cards.add(card.convert());
                }
            }
        } else {
            for(int i=0; i< token.getBlackCrossFaithPoints();i++) {
                faithBox = faithTrack.faithAdvance();
                this.lorenzoLastAction += "\n > Lorenzo has advanced on the track\n";
                if (faithBox.getPosition() == 24) {
                    this.lorenzoLastAction+="\nGAME OVER: LORENZO HAS REACHED THE END OF FAITH TRACK";
                    this.gameIsOver = true;
                    return;
                }
                else
                    this.position++;
                boolean[] check = faithBox.getPopeFlag();
            }
            if (token.getShuffle()) {
                shuffle();
                this.shuffle ++;
                this.lorenzoLastAction+="\n > Lorenzo has shuffled tokens\n";
            }
        }
        tmp = tokens.get(0);
        for(int i=0; i<tokens.size()-1; i++){
            tokens.set(i, tokens.get(i+1));
        }
        tokens.set(tokens.size()-1, tmp);
    }

    /**Returns lorenzoLastAction attribute
     * @return a String containing a final message
     */
    public String getLorenzoLastAction(){
         return this.lorenzoLastAction;
    }

    /**Returns true if Lorenzo has won
     * @return a boolean
     */
    public boolean gameIsOver(){
        return this.gameIsOver;
    }

    /**Converts the current Lorenzo state in a LightLorenzo instance for LightModel
     * @return a LightLorenzo instance
     */
    public LightLorenzo convert(){
        LightLorenzo convertedLorenzo = new LightLorenzo(faithBox.getPosition());
        if(!cards.isEmpty())
            convertedLorenzo.hasPickedCards(cards);
        if(position!=0)
            convertedLorenzo.hasAdvanced(faithBox.getPosition(), position);
        if(shuffle!=0)
            convertedLorenzo.hasShuffled(shuffle);

        this.cards = new ArrayList<>();
        this.shuffle = 0;
        this.position = 0;
        return convertedLorenzo;

    }






}
