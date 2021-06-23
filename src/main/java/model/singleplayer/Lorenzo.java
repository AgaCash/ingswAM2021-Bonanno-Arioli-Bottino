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
        tokens = new JsonParser("src/main/resources/tokensList.json").getTokens();
        this.shuffle();
        this.developmentBoard = developmentBoard;
        this.cards = new ArrayList<>();
        this.shuffle = 0;
        this.position = 0;
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
                DevelopmentCard card  = token.cardAction(developmentBoard);
                if (card == null){
                    this.lorenzoLastAction+="GAME OVER: LORENZO HA PESCATO L'ULTIMA CARTA";
                    this.gameIsOver = true;
                    return;
                }
                else {
                    this.lorenzoLastAction += " picked the card: " + card.convert().toString();
                    cards.add(card.convert());
                }
            }
        } else {
            for(int i=0; i< token.getBlackCrossFaithPoints();i++) {
                faithBox = faithTrack.faithAdvance();
                this.lorenzoLastAction += "\nLorenzo ha avanzato sul tracciato ";
                if (faithBox.getPosition() == 24) {
                    this.lorenzoLastAction+="\nGAME OVER: LORENZO HA RAGGIUNTO LA FINE DEL TRACCIATO";
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
                this.lorenzoLastAction+="\nLorenzo ha rimescolato i segnalini";
            }
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

    public LightLorenzo convert(){
        LightLorenzo convertedLorenzo = new LightLorenzo();
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
