package clientModel.table;

import clientModel.cards.LightDevelopmentCard;
import clientModel.colour.LightColour;
import clientModel.resources.LightResource;

import java.util.ArrayList;
import java.util.InputMismatchException;

public class LightDeck {
    private ArrayList<LightDevelopmentCard> cards; //4 model.cards
    private LightColour colourDeck;
    private int levelDeck;


    public LightDeck(ArrayList<LightDevelopmentCard> cards){
        this.cards = cards;
        levelDeck = this.cards.get(0).getLevel();
        colourDeck = this.cards.get(0).getColour();
        cards.forEach((card) ->{
            if(card.getLevel() != levelDeck || card.getColour() != colourDeck)
                throw new InputMismatchException();
        });
    }
    public ArrayList<LightResource> getCost(){
        return cards.get(cards.size()-1).getCost();
    }

    public LightColour getColourDeck() {
        return colourDeck;
    }

    public int getLevelDeck() {
        return levelDeck;
    }

    public boolean isEmpty(){
        return cards.isEmpty();
    }

}