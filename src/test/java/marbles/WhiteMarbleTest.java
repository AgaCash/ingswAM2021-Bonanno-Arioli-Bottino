package marbles;

import cards.ExtraDepot;
import cards.LeaderCard;
import cards.WhiteConverter;
import org.junit.jupiter.api.Test;
import resources.Resource;

import static org.junit.jupiter.api.Assertions.*;

class WhiteMarbleTest {

    @Test
    void convertMarbleTest() {
        WhiteMarble wmar = new WhiteMarble();
        LeaderCard extraActive = new WhiteConverter(0, true, null ,Resource.SHIELD);
        LeaderCard extraNotActive = new ExtraDepot(0, true, null, null );

        //convertMarble return an Object
        assertNotNull(wmar.convertMarble(extraActive));

        //convertMarble works with no active Leader Card
        assertNull(wmar.convertMarble(null));

        //correct restitutions of an object of the same class of the LeaderCard's one
        Resource ans =  wmar.convertMarble(extraActive);
        Resource sh = Resource.SHIELD;
        assertEquals(ans.getClass().getSimpleName(), sh.getClass().getSimpleName());

        //Testing error: convertMarble get a non-white converter LeaderCard (generic)
        assertNull(wmar.convertMarble(extraNotActive));

    }
}