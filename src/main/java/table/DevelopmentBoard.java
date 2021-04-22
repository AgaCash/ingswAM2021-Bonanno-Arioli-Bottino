package table;


import cards.DevelopmentCard;
import utilities.JsonParser;
import java.util.ArrayList;

public final class DevelopmentBoard {
    /**
     * Class that load all the development cards from a configuration Json file
     */

    private ArrayList<Deck> decks = new ArrayList<>();  //12 decks

    /**
     * Default constructor that extract the data from Json and create the cards
     */
    public DevelopmentBoard(){
        ArrayList<DevelopmentCard> tmpCards =
                new JsonParser("src/main/resources/developmentCards.json").getDevelopmentCards();
        ArrayList<DevelopmentCard> tmpDeck = new ArrayList<>();
        int i = 1;
        for (DevelopmentCard card:tmpCards) {
            tmpDeck.add(card);
            if(i == 4){
                decks.add(new Deck((ArrayList<DevelopmentCard>) tmpDeck.clone()));
                tmpDeck.clear();
                i = 0;
            }
            i++;
        }
    }

    /**
     * Method getter that returns the reference to selected deck
     * @param deckNumber index of the deck (0-11)
     * @return the reference of the selected deck
     */
    public Deck getDeck(int deckNumber){
        if(deckNumber<0 || deckNumber >decks.size()-1)
            return null;
        else
            return decks.get(deckNumber);
    }

    /**
     * Method that pop the first card of the selected deck
     * @param deckNumber index of the deck (0-11)
     * @return the card on the top of the selected deck
     */

    public DevelopmentCard popCardFromDeck(int deckNumber){
        Deck d = this.getDeck(deckNumber);
        if(d == null)
            return null;
        else
            return d.popCard();
    }

}

