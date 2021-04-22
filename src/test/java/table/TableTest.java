package table;

import cards.LeaderCard;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class TableTest {

    @Test
    void initialize() {
        Table table = new Table();
        ArrayList<LeaderCard> cards = table.initialize();
        for(LeaderCard c : cards){
            //System.out.println(c.isEnabled()+" "+c.getClass().getSimpleName());
            assertNotNull(c);
            assertFalse(c.isEnabled());
            assertNotNull(c.toString());
        }
    }
}