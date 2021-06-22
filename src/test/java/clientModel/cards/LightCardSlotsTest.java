package clientModel.cards;

import clientModel.colour.LightColour;
import clientModel.resources.LightResource;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class LightCardSlotsTest {

    @Test
    void testToString(){
        LightCardSlots c = new LightCardSlots();
        ArrayList<LightDevelopmentCard> slots = new ArrayList<>();

        slots.add(new LightDevelopmentCard());
        slots.add(new LightDevelopmentCard());
        slots.add(new LightDevelopmentCard(0, LightColour.BLACK, 0, 0, new ArrayList<LightResource>(),
                new ArrayList<LightResource>(), new ArrayList<LightResource>()));

        c.setCards(slots);

        System.out.println(c.toString());
    }

}