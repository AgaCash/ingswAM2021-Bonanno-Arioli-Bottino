package table;

import org.junit.jupiter.api.Test;

class MarketBoardTest {

    @Test
    void initializeMarbleGrid() {
        MarketBoard market = new MarketBoard();
        market.initializeMarbleGrid();
    }

    @Test
    void addMarketLine() {
    }

    @Test
    void addMarketColumn() {
    }

    @Test
    void getMarble() {
        MarketBoard market = new MarketBoard();
        market.initializeMarbleGrid();
        market.getMarble(1,1);
    }
}