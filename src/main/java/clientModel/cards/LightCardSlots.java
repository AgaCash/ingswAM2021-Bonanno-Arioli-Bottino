package clientModel.cards;

import java.util.ArrayList;

public class LightCardSlots {
    private ArrayList<LightDevelopmentCard> slots = new ArrayList<>(3);

    public int getSize(){
        return this.slots.size();
    }

    public LightDevelopmentCard getCard(int num){
        return slots.get(num);
    }

    public ArrayList<LightDevelopmentCard> getCards(){
        return this.slots;
    }

    public void setCards(ArrayList<LightDevelopmentCard> cards){
        this.slots = cards;
    }

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

