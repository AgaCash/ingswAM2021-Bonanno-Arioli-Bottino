package marbles;

import cards.GenericLeaderCard;
import cards.LeaderCard;
import cards.WhiteConverter;
import org.junit.jupiter.api.Test;
import resources.*;
import static org.junit.jupiter.api.Assertions.*;

class BlueMarbleTest {
    //We consider BlueMarble Test valid also for all the others Marbles classes (except for White Marble)
    //as the internal code has the same structure

    @Test
    void convertMarbleTest() {
        BlueMarble bmar = new BlueMarble();
        Shield sh = new Shield();
        LeaderCard extraActive = new WhiteConverter(new Servant(), true);
        LeaderCard extraNotActive = new GenericLeaderCard(true);

        //convertMarble return an object
        assertNotNull(bmar.convertMarble(extraNotActive));

        //convertMarble always return a Shield() with all types of cards:
        //with no Leader Card
        Resource ans = bmar.convertMarble(null);
        assertEquals(ans.getClass().getSimpleName(), sh.getClass().getSimpleName());
        // with a Generic Leader Card
        ans = bmar.convertMarble(extraNotActive);
        assertEquals(ans.getClass().getSimpleName(), sh.getClass().getSimpleName());
        // with a White Marble Leader Card
        ans = bmar.convertMarble(extraActive);
        assertEquals(ans.getClass().getSimpleName(), sh.getClass().getSimpleName());


    }
}