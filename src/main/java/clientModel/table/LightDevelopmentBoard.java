package clientModel.table;

import clientModel.cards.LightDevelopmentCard;

import java.util.ArrayList;
import java.util.Arrays;

public class LightDevelopmentBoard {
    private final ArrayList<LightDevelopmentCard> decks = new ArrayList<>(); //12 top cards

    public void setDecks (ArrayList<LightDevelopmentCard> cards){
        for (int i=0; i<12; i++)
            decks.add(cards.get(i));
    }
    public LightDevelopmentCard getDeck(int deckNumber){
        return decks.get(deckNumber);
    }

    @Override
    public String toString(){
        String[] s = new String[5];
        for(int i=0; i<decks.size(); i++) {
            String card = decks.get(i).toString();
            String[] levels = card.split("\n");
            System.out.println(i+Arrays.toString(levels));
        }
        return new String();
    }
/*
    @Override
    public String toString(){
        String s = "DEVELOPMENT BOARD:\n";
        for(int i=0; i<12; i+=4) {
            s+="________________________________";
            s+="________________________________";
            s+="________________________________";
            s+="________________________________\n";
            for(int j = 0; j<4; j++)
                s+=(i+j+1)+": "+insertTabs(29);
            s+="\n";
            for(int j = 0; j<4; j++)
                    s += decks.get(i + j).toStringLevel() + insertTabs(24);
            s+="\n";
            for(int j = 0; j<4; j++)
                    s += decks.get(i + j).toStringPoints() + insertTabs(15-decks.get(i+j).getPoints()/10);
            s+="\n";
            for(int j = 0; j<4; j++)
                    s += decks.get(i + j).toStringCost() + insertTabs(32-6-3*decks.get(i+j).getCost().size());
            s+="\n";
            for(int j = 0; j<4; j++)
                    s += decks.get(i + j).toStringProdInput() + insertTabs(32 - 11 - 3*decks.get(i+j).getProdInput().size());
            s+="\n";
            for(int j = 0; j<4; j++)
                    s += decks.get(i + j).toStringProdOutput() + insertTabs(32 -12  -3*decks.get(i+j).getProdOutput().size());
        }
        s+="\n________________________________";
        s+="________________________________";
        s+="________________________________";
        s+="________________________________\n";

        return s;
    }

    private String insertTabs(int num){
        String s = new String();
        for(int i=0; i<num; i++)
            s+=" ";
        return s;
    }

 */
}
