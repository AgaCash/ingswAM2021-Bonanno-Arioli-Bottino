package clientModel.table;

import clientModel.cards.LightDevelopmentCard;

import java.util.ArrayList;

public class LightDevelopmentBoard {
    private final ArrayList<LightDevelopmentCard> decks = new ArrayList<>(); //12 top cards

    public void setDecks (ArrayList<LightDevelopmentCard> cards){
        for (int i=0; i<12; i++)
            decks.add(cards.get(i));
    }


    public LightDevelopmentCard getDeck(int deckNumber){
        return decks.get(deckNumber);
    }

    public LightDevelopmentCard getTopCard (int whichOne){
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
    @Override
    public String toString(){
        //todo renderlo + elegante
        String s = new String();
        int count = 1;
        for(LightDevelopmentCard card: decks) {
            s += count+": "+card.toString() + " ";
            if(count%4==0)
                s+="\n";
            count++;
        }
        return s;
    }
}
