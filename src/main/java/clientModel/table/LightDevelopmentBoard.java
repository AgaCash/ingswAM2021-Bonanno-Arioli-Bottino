package clientModel.table;

import clientModel.table.LightDeck;

import java.util.ArrayList;

public class LightDevelopmentBoard {
    private final ArrayList<LightDeck> decks = new ArrayList<>(); //12 top cards

    public void setDecks (ArrayList<LightDeck> cards){
        for (int i=0; i<12; i++)
            decks.add(cards.get(i));
    }

/*
    public Deck getDeck(int deckNumber){
        if(deckNumber<0 || deckNumber >decks.size()-1)
            return null;
        else
            return decks.get(deckNumber);
    }
    */
    public LightDeck getTopCard (int whichOne){
            return decks.get(whichOne);
    }

    /*
    public DevelopmentCard popCardFromDeck(int deckNumber){
        Deck d = this.getDeck(deckNumber);
        if(d == null)
            return null;
        else
            return d.popCard();
    }

    public void setTopCard (int whichOne, LightDevelopmentCard newTopCard){
        cards.set(whichOne, newTopCard);
    }
     */
}
