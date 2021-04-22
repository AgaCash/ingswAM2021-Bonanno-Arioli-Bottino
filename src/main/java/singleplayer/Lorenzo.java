package singleplayer;

import singleplayer.Token;
import table.DevelopmentBoard;
import table.FaithTrack;
import utilities.JsonParser;

import java.util.ArrayList;
import java.util.Collections;

public class Lorenzo {
    private FaithTrack faithTrack = new FaithTrack();
    public ArrayList<Token> tokens ;//= new ArrayList<>(6);
    private DevelopmentBoard developmentBoard;
    private int faithPoints;
    //private Token token = new Token(0, 1);//DA CAMBIARE

    public Lorenzo(DevelopmentBoard developmentBoard){
        tokens  = new JsonParser("src/main/resources/tokensList.json").getTokens();
        this.shuffle();
        this.developmentBoard = developmentBoard;
    }

    public void pick (){
        Token token = tokens.get(0);
        token.execute(faithTrack, developmentBoard);
    }

    public void shuffle(){
        Collections.shuffle(tokens);
    }


}
