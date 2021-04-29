package marbles;

import cards.WhiteConverter;
import org.junit.jupiter.api.Test;
import resources.Resource;

import static org.junit.jupiter.api.Assertions.*;

class MarbleTest {

    @Test
    void convertMarble() {
        WhiteConverter card = new WhiteConverter(0, true, null, Resource.COIN);

        Marble blue = Marble.BLUE;
        Marble grey = Marble.GREY;
        Marble purple = Marble.PURPLE;
        Marble red = Marble.RED;
        Marble yellow = Marble.YELLOW;
        Marble white = Marble.WHITE;

        assertEquals(blue.convertMarble(card), Resource.SHIELD);
        assertEquals(grey.convertMarble(card), Resource.STONE);
        assertEquals(purple.convertMarble(card), Resource.SERVANT);
        assertEquals(red.convertMarble(card), Resource.FAITH);
        assertEquals(yellow.convertMarble(card), Resource.COIN);
        assertEquals(white.convertMarble(card), Resource.COIN);

        card = new WhiteConverter(0, true, null, Resource.SERVANT);
        assertEquals(white.convertMarble(card), Resource.SERVANT);

        card = new WhiteConverter(0, false, null, Resource.SERVANT);
        assertNull(white.convertMarble(card));
    }
}