import org.junit.jupiter.api.Test;
import resources.Resource;
import table.MarketBoard;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MarketBoardTest {

    @Test
    void initializeMarbleGridTest() {
    }

    @Test
    void addMarketLineTest() {
        int line;
        MarketBoard market = new MarketBoard();
        ArrayList<Resource> resource = new ArrayList<>();
        market.initializeMarbleGrid();
        resource.add(market.addMarketLine(1, null).get(0));
        assertNotNull(resource);
    }

    @Test
    void addMarketColumnTest() {
        int column;
        MarketBoard market = new MarketBoard();
        ArrayList<Resource> resource = new ArrayList<>();
        market.initializeMarbleGrid();
        resource.add(market.addMarketColumn(1, null).get(0));
        assertNotNull(resource);
    }
}