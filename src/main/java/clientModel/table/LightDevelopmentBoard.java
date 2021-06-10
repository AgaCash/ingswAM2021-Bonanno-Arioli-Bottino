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
        for(int i=0; i<12; i+=4) {
            s+="\n__________________________________";
            s+="__________________________________";
            s+="__________________________________";
            s+="__________________________________\n";
            for(int j = 0; j<4; j++)
                s+=(i+j+1)+":  "+insertTabs(7);
            s+="\n";
            for(int j = 0; j<4; j++)
                s +=decks.get(i + j).toStringLevel()+insertTabs(6);
            s+="\n";
            for(int j = 0; j<4; j++)
                s+=decks.get(i+j).toStringPoints()+insertTabs(4);
            s+="\n";
            for(int j = 0; j<4; j++)
                s+=decks.get(i+j).toStringCost()+insertTabs(4-i/2+i/8);
            s+="\n";
            for(int j = 0; j<4; j++)
                s+=decks.get(i+j).toStringProdInput()+insertTabs(4-i/8+i/4);
            s+="\n";
            for(int j = 0; j<4; j++)
                s+=decks.get(i+j).toStringProdOutput()+insertTabs(3-i/8);
        }
        s+="\n__________________________________";
        s+="__________________________________";
        s+="__________________________________";
        s+="__________________________________\n";

        return s;
    }

    private String insertTabs(int num){
        String s = new String();
        for(int i=0; i<num; i++)
            s+="\t";
        return s;
    }
}
