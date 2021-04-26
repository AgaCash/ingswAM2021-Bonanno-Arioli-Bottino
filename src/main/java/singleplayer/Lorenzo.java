package singleplayer;

import singleplayer.Token;
import table.DevelopmentBoard;
import table.FaithBox;
import table.FaithTrack;
import utilities.JsonParser;

import java.util.ArrayList;
import java.util.Collections;

public class Lorenzo {
    private FaithTrack faithTrack = new FaithTrack();
    private FaithBox faithBox = new FaithBox();
    public ArrayList<Token> tokens ;//= new ArrayList<>(6);
    private DevelopmentBoard developmentBoard;
    private int faithPoints; //probabilmente attributo già incluso in faithBox
    //private Token token = new Token(0, 1);//DA CAMBIARE

    /** creation of Lorenzo's deck of tokens and development board
     * @param developmentBoard connects Lorenzo with the development board
     */
    public Lorenzo(DevelopmentBoard developmentBoard){
        tokens = new JsonParser("src/main/resources/tokensList.json").getTokens();
        this.shuffle();
        this.developmentBoard = developmentBoard;
    }

    //public Lorenzo(){
    //     developmentBoard = getDevBoard();
    //     tokens = new JsonParser("src/main/resources/tokensList.json").getTokens();
    //     this.shuffle();
    //}

    /**
     * method that picks the token on top of the deck and executes the token's effects
     */
    public void pick () {
        Token token = tokens.get(0);
        Token tmp;
        if (!(token.getIsAboutLorenzo())) {
            for (int i = 0; i < token.getRemoveQuantity(); i++)
                token.cardAction(developmentBoard);
        } else {
            faithBox = faithTrack.faithAdvance(faithBox, faithTrack, token.getBlackCrossFaithPoints());
            if (faithBox.getPosition() == 24)
                //endgame
                ;
            if (token.getShuffle())
                shuffle();
        }
        tmp = tokens.get(0);
        for(int i=0; i<tokens.size();i++){
            tokens.set(i, tokens.get(i+1));
        }
        tokens.set(tokens.size()-1, tmp);
    }

    /**
     * method that shuffles the token's deck
     */
    public void shuffle(){
        Collections.shuffle(tokens);
    }


}
