package clientModel.cards;

import java.util.ArrayList;

/**
 * The LightModel copy of Model's CardSlots. It contains only the LightDevelopmentCard ont the top of each slot
 */
public class LightCardSlots {
    private ArrayList<LightDevelopmentCard> slots = new ArrayList<>(3);

    /**Returns the number of used DevelopmentCard slots
     * @return an int
     */
    public int getSize(){
        return this.slots.size();
    }

    /**Returns the DevelopmentCard on the top of the chosen Slot
     * @param num the Slot number
     * @return a LightDevelopmentCard instance
     */
    public LightDevelopmentCard getCard(int num){
        return slots.get(num);
    }

    /**Returns all the DevelopmentCards on the top of the slots
     * @return a LightDevelopmentCard ArrayList
     */
    public ArrayList<LightDevelopmentCard> getCards(){
        return this.slots;
    }

    /**Updates the old CardSlots with new Cards
     * @param cards
     */
    public void setCards(ArrayList<LightDevelopmentCard> cards){
        this.slots = cards;
    }

    /**Method used to print CardSlots in CLI
     * @return
     */
    @Override
    public String toString(){
        String[] s = new String[5];
        String ans = "CARD SLOTS:\n------------------------------------------------------------------------------------\n";
        for(int i=0; i< slots.size(); i++) {
            String[] levels = slots.get(i).toString().split("\n");
            for(int j = 0; j<5; j++) {
                s[j] += levels[j];
            }
        }
        for (int k = 0; k < 5; k++) {
            ans += s[k].substring(4) + "\n";
        }
        return ans+"------------------------------------------------------------------------------------\n";
    }

}

