package table;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FaithTrackTest {

    @Test
    void faithAdvanceTest() {
        FaithTrack track = new FaithTrack();
        FaithBox startBox = new FaithBox();
        FaithBox nextBox1;
        FaithBox nextBox2;
        String s;
        s = startBox.toString();
        System.out.println(s);
        nextBox1 = track.faithAdvance(startBox, track, 8);
        nextBox2 = track.faithAdvance(startBox, track, 3);
        nextBox2 = track.faithAdvance(nextBox2, track, 5);
        assertEquals(nextBox1, nextBox2);
        s = startBox.toString();
        System.out.println(s);
    }
}