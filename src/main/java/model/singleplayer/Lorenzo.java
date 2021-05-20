package model.singleplayer;

import model.table.DevelopmentBoard;
import model.table.FaithBox;
import model.table.FaithTrack;
import model.utilities.JsonParser;

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

    private void shuffle(){
        Collections.shuffle(tokens);
    }

    public DevelopmentBoard getDevelopmentBoard() {
        return developmentBoard;
    }

    public FaithTrack getFaithTrack() {
        return faithTrack;
    }

    public FaithBox getFaithBox() {
        return faithBox;
    }

    public void setFaithBox(FaithBox newBox){
        this.faithBox = newBox;
    }
/*


    public void pick () {
        Token token = tokens.get(0);
        Token tmp;
        if (!(token.getIsAboutLorenzo())) {
            for (int i = 0; i < token.getRemoveQuantity(); i++)
                    token.cardAction(developmentBoard);
        } else {
            for(int i=0; i< token.getBlackCrossFaithPoints();i++) {
                faithBox = faithTrack.faithAdvance(faithBox, faithTrack);
                if (faithBox.getPosition() == 24)
                    //endgame
                    ;
                boolean[] check = faithBox.getPopeFlag();
                checkPopeFlags(check);
            }
            if (token.getShuffle())
                shuffle();
        }
        tmp = tokens.get(0);
        for(int i=0; i<tokens.size()-1; i++){
            tokens.set(i, tokens.get(i+1));
        }
        tokens.set(tokens.size()-1, tmp);
    }



    public void checkPopeFlags(boolean[] flags){
        if (flags[0])
            //serve che controller faccia chiamare al game il metodo, dandogli indicazione
            //di quale player ha chiamato
            ;
        if (flags[1])
            //same
            ;
        if(flags[2])
            //same
            ;
    }

 */


}
