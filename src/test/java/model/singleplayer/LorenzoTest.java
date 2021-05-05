package model.singleplayer;

import org.junit.jupiter.api.Test;
import model.table.DevelopmentBoard;

import static org.junit.jupiter.api.Assertions.*;

class LorenzoTest {

    @Test
    void pickTest() {
        DevelopmentBoard board = DevelopmentBoard.getDevBoardInstance();
        Lorenzo lorenzo = new Lorenzo(board);
        int tSize = lorenzo.tokens.size();
        lorenzo.shuffle();
        lorenzo.pick();
        assertEquals(tSize,lorenzo.tokens.size());
        //the internal methods called by pick() are tested in FaithTrackTest and TokenTest classes.
        board = null;
    }
}