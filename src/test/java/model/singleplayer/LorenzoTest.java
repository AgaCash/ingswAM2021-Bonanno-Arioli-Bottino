package model.singleplayer;

import org.junit.jupiter.api.Test;
import model.table.DevelopmentBoard;

import static org.junit.jupiter.api.Assertions.*;

class LorenzoTest {

    @Test
    void pickTest() {
        DevelopmentBoard board = new DevelopmentBoard();
        Lorenzo lorenzo = new Lorenzo(board);
        int tSize = lorenzo.getTokens().size();
        lorenzo.pick();
        assertEquals(tSize,lorenzo.getTokens().size());
        //the internal methods called by pick() are tested in FaithTrackTest and TokenTest classes.
    }
}