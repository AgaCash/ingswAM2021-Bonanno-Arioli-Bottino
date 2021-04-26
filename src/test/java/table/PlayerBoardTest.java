package table;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerBoardTest {

    @Test
    void setInkwell() {
    }

    @Test
    void selectLeader() {
    }

    @Test
    void buyDevCard() {
    }

    @Test
    void buyResources() {
    }

    @Test
    void devCardProduction() {
    }

    @Test
    void defaultProduction() {
    }

    @Test
    void faithAdvanceTest() {
        DevelopmentBoard board = new DevelopmentBoard();
        MarketBoard market = new MarketBoard();
        PlayerBoard player = new PlayerBoard(board, market);
        //PlayerBoard player = new PlayerBoard();
        player.faithAdvance(2);
        assertTrue(player.getFaithBox().getPosition() == 2);
    }
}