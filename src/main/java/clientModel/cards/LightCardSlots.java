package clientModel.cards;

import java.util.ArrayList;
import java.util.Arrays;

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
        for(int i=0; i< slots.size(); i++) {
            String card = slots.get(i).toString();
            String[] levels = card.split("\n");
            System.out.println(i+ Arrays.toString(levels));
        }
        return new String();
    }
/*
    @Override
    public String toString(){
        String s ="CARD SLOTS:\n";
        s+="________________________________";
        s+="________________________________";
        s+="________________________________\n";
        for(int j = 0; j<slots.size(); j++)
            s+=(j+1)+": "+insertTabs(29);
        s+="\n";
        for(int j = 0; j<slots.size(); j++)
                s += slots.get(j).toStringLevel()/* + insertTabs(24)*/;
    /*    s+="\n";
        for(int j = 0; j<slots.size(); j++)
            s += slots.get(j).toStringPoints() /*+ insertTabs(15-slots.get(j).getPoints()/10)*/;
    /*    s+="\n";
        for(int j = 0; j<slots.size(); j++)
            s += slots.get(j).toStringCost() /*+ insertTabs(32-6-3*slots.get(j).getCost().size())*/;
   /*     s+="\n";
        for(int j = 0; j<slots.size(); j++)
                s += slots.get(j).toStringProdInput() /*+ insertTabs(32 - 11 - 3*slots.get(j).getProdInput().size())*/;
   /*     s+="\n";
        for(int j = 0; j<slots.size(); j++)
            s += slots.get( j).toStringProdOutput()/* + insertTabs(32 -12  -3*slots.get(j).getProdOutput().size())*/;
    /*    s+="\n________________________________";
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

