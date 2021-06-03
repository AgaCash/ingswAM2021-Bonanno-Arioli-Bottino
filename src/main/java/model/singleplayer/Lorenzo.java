package model.singleplayer;

import model.table.DevelopmentBoard;
import model.table.FaithBox;
import model.table.FaithTrack;
import utilities.JsonParser;

import java.util.ArrayList;
import java.util.Collections;

public class Lorenzo {
    private FaithTrack faithTrack = new FaithTrack();
    private FaithBox faithBox = new FaithBox();
    private ArrayList<Token> tokens ;//= new ArrayList<>(6);
    private DevelopmentBoard developmentBoard;
    private int faithPoints; //probabilmente attributo già incluso in faithBox
    //private Token token = new Token(0, 1);//DA CAMBIARE
    private String lorenzoLastAction = new String();
    private boolean gameIsOver = false;

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

    public ArrayList<Token> getTokens(){
        return this.tokens;
    }

    public void pick () {
        this.lorenzoLastAction = "";
        Token token = tokens.get(0);
        Token tmp;
        if (!(token.getIsAboutLorenzo())) {
            for (int i = 0; i < token.getRemoveQuantity(); i++) {
                String message = token.cardAction(developmentBoard);
                if (message.equals("GAMEOVER")){
                    this.lorenzoLastAction+="GAME OVER: LORENZO HA PESCATO L'ULTIMA CARTA";
                    this.gameIsOver = true;
                    return;
                }
                else
                    this.lorenzoLastAction += message;
            }
            System.out.println("riga 65 Lorenzo");
        } else {
            for(int i=0; i< token.getBlackCrossFaithPoints();i++) {
                faithBox = faithTrack.faithAdvance(faithBox);
                this.lorenzoLastAction += "\nLorenzo ha avanzato sul tracciato ";
                if (faithBox.getPosition() == 24) {
                    this.lorenzoLastAction+="\nGAME OVER: LORENZO HA RAGGIUNTO LA FINE DEL TRACCIATO";
                    this.gameIsOver = true;
                    return;
                }
                boolean[] check = faithBox.getPopeFlag();
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
    
    public String getLorenzoLastAction(){
         return this.lorenzoLastAction;
    }

    public boolean gameIsOver(){
        return this.gameIsOver;
    }






}
