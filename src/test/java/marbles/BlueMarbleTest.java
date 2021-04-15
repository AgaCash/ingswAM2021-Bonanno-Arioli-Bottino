package marbles;

import cards.LeaderCard;
import cards.WhiteConverter;
import org.junit.jupiter.api.Test;
import resources.Resource;

import static org.junit.jupiter.api.Assertions.*;
class BlueMarbleTest {
    //We consider BlueMarble Test valid also for all the others Marbles classes (except for White Marble)
    //as the internal code has the same structure

    @Test
    void convertMarbleTest() {
        BlueMarble bmar = new BlueMarble();
        Resource sh = Resource.SHIELD;
        LeaderCard extraActive = new WhiteConverter(0, true, null , Resource.SERVANT);
        LeaderCard extraNotActive = new WhiteConverter(0, false, null, Resource.COIN);

        //convertMarble return an object
        assertNotNull(bmar.convertMarble(extraNotActive));

        //convertMarble always return a SHIELD with all types of cards:
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