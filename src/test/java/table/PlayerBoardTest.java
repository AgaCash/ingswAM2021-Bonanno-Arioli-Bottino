package table;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PlayerBoardTest {

    @Test
    void setInkwell() {
        PlayerBoard player = new PlayerBoard(new DevelopmentBoard(), new MarketBoard());
        player.setInkwell(true);
        //e poi?
    }

    @Test
    void selectLeader() {
    }

    @Test
    void buyDevCard() {
    }

    @Test
    void buyResources() {
        //MarketBoard market = new MarketBoard();
        //PlayerBoard player = new PlayerBoard(new DevelopmentBoard(), market);

       // player.buyResources(true, false, 1, null);

    }

    @Test
    void devCardProduction(){
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
        boolean[] check = player.getFaithBox().getPopeFlag();
        for(int i=0; i<3; i++) {
            System.out.println(check[i]);
            assertFalse(check[i]);
        }
        player.faithAdvance(6);
        check = player.getFaithBox().getPopeFlag();
        for(int i=0; i<3; i++)
            System.out.println(check[i]);
        //System.out.println(player.getFaithBox().toString());
        assertTrue(check[0]);
        player.faithAdvance(16);
    }
}