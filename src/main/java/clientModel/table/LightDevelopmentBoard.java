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
        String s = new String();
        for(int i=0; i<12; i+=4) {
            s+="\n________________________________";
            s+="________________________________";
            s+="________________________________";
            s+="________________________________\n";
            for(int j = 0; j<4; j++)
                s+=(i+j+1)+": "+insertTabs(29);
            s+="\n";
            for(int j = 0; j<4; j++) {
                if(decks.get(i+j).isUsable())
                    s += decks.get(i + j).toStringLevel() + insertTabs(24);
                else
                    s+="                                ";
            }
            s+="\n";
            for(int j = 0; j<4; j++) {
                if(decks.get(i+j).isUsable())
                    s += decks.get(i + j).toStringPoints() + insertTabs(15-decks.get(i+j).getPoints()/10);
                else
                    s+="                                ";
            }
            s+="\n";
            for(int j = 0; j<4; j++) {
                if(decks.get(i+j).isUsable())
                    s += decks.get(i + j).toStringCost() + insertTabs(32-6-3*decks.get(i+j).getCost().size());
                else
                    s+="             EMPTY              ";
            }
            s+="\n";
            for(int j = 0; j<4; j++) {
                if(decks.get(i+j).isUsable())
                    s += decks.get(i + j).toStringProdInput() + insertTabs(32 - 11 - 3*decks.get(i+j).getProdInput().size());
                else
                    s+="                                ";
            }
            s+="\n";
            for(int j = 0; j<4; j++) {
                if(decks.get(i+j).isUsable())
                    s += decks.get(i + j).toStringProdOutput() + insertTabs(32 -12  -3*decks.get(i+j).getProdOutput().size());
                else
                    s+="                                ";
            }
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
}
