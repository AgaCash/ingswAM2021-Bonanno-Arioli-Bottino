package clientModel.table;

import clientModel.cards.LightDevelopmentCard;

import java.util.ArrayList;

public class LightDevelopmentBoard {
    private final ArrayList<LightDevelopmentCard> decks = new ArrayList<>(); //12 top cards

    public void setDecks (ArrayList<LightDevelopmentCard> cards){
        for (int i=0; i<12; i++)
            decks.add(cards.get(i));
    }

    public int getDecksSize(){
        return decks.size();
    }

    @Override
    public String toString(){
        String[] s = new String[5];
        String ans = "DEVELOPMENT BOARD:\n----------------------------------------------------------------------------------------------------------------\n";
        for(int i=0; i<decks.size(); i++) {
            String[] levels = decks.get(i).toString().split("\n");
            for(int j = 0; j<5; j++) {
                s[j] += levels[j];
            }
            if(i==3 || i==7 || i==11) {
                for (int k = 0; k < 5; k++) {
                    ans += s[k].replace("null", "") + "\n";
                }
                s = new String[5];
            }
        }
        return ans+ "\n----------------------------------------------------------------------------------------------------------------\n";
    }
}
