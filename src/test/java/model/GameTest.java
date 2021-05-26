package model;

import model.player.Player;
import org.junit.jupiter.api.Test;

class GameTest {

    @Test
    void initializeGame() {
    }

    @Test
    void addPlayer() {
    }

    @Test
    void setOrder() {
        Game game = new Game(true);
        game.initializeGame();
        game.addPlayer(new Player("teodoro"));
        game.setOrder();
    }

    @Test
    void sendQuartet() {
    }

    @Test
    void addStartingResources() {
    }

    @Test
    void getPlayerTurns() {
    }

    @Test
    void updateTurn() {
    }

    @Test
    void addPlayerAtIndex() {
    }

    @Test
    void removePlayer() {
    }

    @Test
    void buyDevCard() {
    }

    @Test
    void buyResources() {
    }

    @Test
    void getThrewResources() {
    }

    @Test
    void devCardProduction() {
    }

    @Test
    void defaultProduction() {
    }

    @Test
    void faithAdvance() {
    }

    @Test
    void checkPopeFlags() {
    }

    @Test
    void activateLeaderCard() {
    }

    @Test
    void throwLeaderCard() {
    }

    @Test
    void getPlayer() {
    }

    @Test
    void getCurrentPlayer() {
    }

    @Test
    void getDevBoard() {
    }

    @Test
    void getMarketBoard() {
    }

    @Test
    void getFaithTracks() {
    }

    @Test
    void pick() {
    }
}