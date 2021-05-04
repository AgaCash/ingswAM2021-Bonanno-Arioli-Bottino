package model.marbles;

import org.junit.jupiter.api.Test;
import model.resources.Resource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MarbleTest {

    @Test
    void convertMarble() {

        Marble blue = Marble.BLUE;
        Marble grey = Marble.GREY;
        Marble purple = Marble.PURPLE;
        Marble red = Marble.RED;
        Marble yellow = Marble.YELLOW;
        Marble white = Marble.WHITE;

        assertEquals(blue.convertMarble(null), Resource.SHIELD);
        assertEquals(grey.convertMarble(null), Resource.STONE);
        assertEquals(purple.convertMarble(null), Resource.SERVANT);
        assertEquals(red.convertMarble(null), Resource.FAITH);
        assertEquals(yellow.convertMarble(null), Resource.COIN);
        assertEquals(white.convertMarble(Resource.COIN), Resource.COIN);

    }
}