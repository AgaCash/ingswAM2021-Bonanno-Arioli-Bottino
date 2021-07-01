package clientModel.singleplayer;

import clientModel.cards.LightDevelopmentCard;
import clientModel.colour.LightColour;
import clientModel.table.LightFaithTrack;

import java.util.ArrayList;

/**
 * LightModel's copy of Lorenzo instance in Model
 */
public class LightLorenzo {
    private ArrayList<LightDevelopmentCard> cards;
    private int lorenzoPos;
    private int shuffles;
    private int advance;

    public LightLorenzo(int pos){
        this.lorenzoPos = pos;
        this.advance = 0;
        this.shuffles = 0;
        this.cards = new ArrayList<>();
    }

    /**Sets the LightLorenzo's actions flags about the advancing of FaithTrack by Lorenzo instance in Model
     * @param position the current position of Lorenzo on FaithTrack in Model
     * @param delta the position advancement
     */
    public void hasAdvanced(int position, int delta){
        this.lorenzoPos = position;
        this.advance = delta;
    }

    /**Sets in the LightLorenzo instance the DevelopmentCard couple copy picked by Lorenzo instance in Model
     * @param cards a LightDevelopmentCard ArrayList
     */
    public void hasPickedCards(ArrayList<LightDevelopmentCard> cards){
        this.cards = cards;
    }

    /**Sets the LightLorenzo's actions flags about the Token shuffling by Lorenzo instance in Model
     * @param howManyShuffles the number of shuffles
     */
    public void hasShuffled(int howManyShuffles){
        this.shuffles = howManyShuffles;
    }

    /**Returns a String containing all the Lorenzo actions done in the last turn
     * @param track the LightPlayer's LightFaithTrack instance
     * @return a String for View
     */
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


    /**Returns the current Lorenzo's position on LightFaithTrack
     * @return an int
     */
    public int getPosition(){
        return this.lorenzoPos;
    }
}
