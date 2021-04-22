package singleplayer;

import singleplayer.Token;
import table.FaithTrack;

import java.util.ArrayList;
import java.util.Collections;

public class Lorenzo {
    private FaithTrack faithTrack = new FaithTrack();
    public ArrayList<Token> lorenzoDeck = new ArrayList<>(6);
    private int faithPoints;
    private Token token = new Token(0, 1);//DA CAMBIARE

    public void pick (){
        token = lorenzoDeck.get(0);
        token.execute();
    }

    public void shuffle(){
        Collections.shuffle(lorenzoDeck);
    }
}
