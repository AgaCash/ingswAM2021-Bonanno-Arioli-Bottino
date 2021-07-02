package clientModel.table;

import clientModel.cards.LightDevelopmentCard;
import clientModel.colour.LightColour;

import java.util.ArrayList;

/**
 * LightModel's copy of DevelopmentBoard in Model. It contains only a LightDevelopmentCard copy of the DevelopmentCard on the top for each Deck
 */
public class LightDevelopmentBoard {
    private final ArrayList<LightDevelopmentCard> decks = new ArrayList<>(); //12 top cards

    /**Updates the LightDevelopmentBoard with the new Model's DevelopmentBoard status
     * @param cards a LightDevelopmentCard ArrayList representing the 12 top DevelopmentCards over each Deck
     */
    public void setDecks (ArrayList<LightDevelopmentCard> cards){
        for (int i=0; i<12; i++)
            decks.add(cards.get(i));
    }

    /**
     * Method that get the size of the decks of the development board
     * @return size of the decks
     */
    public int getDecksSize(){
        return decks.size();
    }

    /**Returns the LightDevelopmentCard on top of Deck by Deck number
     * @param deckId the deck number
     * @return a LightDevelopmentCard instance
     */
    public LightDevelopmentCard getTopCardFromDeck(int deckId){
        return decks.get(deckId);
    }

    /**Method to print LightDevelopmentBoard in CLI
     * @return a String
     */
    @Override
    public String toString(){
        String[] s = new String[5];
        String ans = "DEVELOPMENT BOARD:\n".indent(50) +
                "----------------------------------------------------------------------------------------------------------------\n";
        for(int i=0; i<decks.size(); i++) {
            String[] levels = decks.get(i).toString().split("\n");
            for(int j = 0; j<5; j++) {
                s[j] += LightColour.WHITE+ "|"+ levels[j];
            }
            if(i==3 || i==7 || i==11) {
                for (int k = 0; k < 5; k++) {
                    ans +=s[k].replace("null", "") + "\n";
                }
                ans+="----------------------------------------------------------------------------------------------------------------\n";
                s = new String[5];
            }
        }
        return ans;
    }

}