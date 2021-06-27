package clientModel.singleplayer;

import clientModel.cards.LightDevelopmentCard;
import clientModel.colour.LightColour;
import clientModel.table.LightFaithTrack;

import java.util.ArrayList;

public class LightLorenzo {
    private ArrayList<LightDevelopmentCard> cards;
    private int lorenzoPos;
    private int shuffles;
    private int advance;

    public LightLorenzo(){
       this.advance = 0;
       this.shuffles = 0;
       this.cards = new ArrayList<>();
    }

    public void hasAdvanced(int position, int delta){
        this.lorenzoPos = position;
        this.advance = delta;
    }

    public void hasPickedCards(ArrayList<LightDevelopmentCard> cards){
        this.cards = (ArrayList<LightDevelopmentCard>) cards.clone();
    }

    public void hasShuffled(int howManyShuffles){
        this.shuffles = howManyShuffles;
    }

    public String actions(LightFaithTrack track){
        String s = new String();
        if(!this.cards.isEmpty())
            for(LightDevelopmentCard card: cards)
                s+=LightColour.BLUE+" > Lorenzo has removed the card:\n"+LightColour.WHITE+card.toString()+LightColour.WHITE;

        if(this.shuffles!=0)
            for(int i = 0; i<shuffles; i++)
                s+=LightColour.BLUE+" > Lorenzo has shuffled tokens\n"+LightColour.WHITE;

        if(this.advance!=0) {
            for (int i = 0; i < advance; i++)
                s += LightColour.BLUE + " > Lorenzo has advanced on the track\n" + LightColour.WHITE;
            track.setLorenzoPos(lorenzoPos);
        }
        return s;
    }

    public int getPosition(){
        return this.lorenzoPos;
    }
}
