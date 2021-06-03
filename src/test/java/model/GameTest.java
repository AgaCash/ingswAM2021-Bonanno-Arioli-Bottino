package model;

import clientModel.table.LightMarketBoard;
import exceptions.InvalidActionException;
import exceptions.UnusableCardException;
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
        Game game = new Game(true);
        Player player = new Player("teodoro");
        game.addPlayer(player);
        game.setOrder();

        LightMarketBoard marketBoard = game.getMarketBoard().convert();
        System.out.println(marketBoard.toString());
        try {
            game.buyResources(true, 1, null);
        }catch(UnusableCardException | InvalidActionException e){
            System.out.println(e.getMessage());
        }
        System.out.println(game.getCurrentPlayer().getPlayerBoard().getWarehouseDepot().status());
        System.out.println(game.getCurrentPlayer().getPlayerBoard().getWarehouseDepot().getThrewResources());

        marketBoard = game.getMarketBoard().convert();
        System.out.println(marketBoard.toString());
        try {
            game.buyResources(true, 1, null);
        }catch(UnusableCardException | InvalidActionException e){
            System.out.println(e.getMessage());
        }
        System.out.println(game.getCurrentPlayer().getPlayerBoard().getWarehouseDepot().status());
        System.out.println(game.getCurrentPlayer().getPlayerBoard().getWarehouseDepot().getThrewResources());



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