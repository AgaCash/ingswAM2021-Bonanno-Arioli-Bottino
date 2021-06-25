package model;

import clientModel.table.LightMarketBoard;
import exceptions.*;
import model.cards.ExtraProd;
import model.cards.LeaderCard;
import model.player.Player;
import model.resources.Resource;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

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
        Game game = new Game(true);
        Player player = new Player("teodoro");
        game.addPlayer(player);
        game.setOrder();

        System.out.println(game.getDevBoard().convert());
        try {
            game.getCurrentPlayer().getPlayerBoard().getWarehouseDepot().addResource(Resource.SERVANT);
            game.getCurrentPlayer().getPlayerBoard().getWarehouseDepot().addResource(Resource.SERVANT);
            game.getCurrentPlayer().getPlayerBoard().getWarehouseDepot().addResource(Resource.STONE);
            game.getCurrentPlayer().getPlayerBoard().getWarehouseDepot().addResource(Resource.STONE);
        }catch(FullWarehouseException e){
            fail();
        }

        try {
            game.buyDevCard(2, 0, null);
        }catch (InvalidActionException | InsufficientResourcesException | FullCardSlotException | EmptyDeckException | UnusableCardException | NonCorrectLevelCardException e) {
            assertTrue(true);
        }
        try {
            game.buyDevCard(3, 0, null);
        }catch (InvalidActionException | InsufficientResourcesException | FullCardSlotException | EmptyDeckException | UnusableCardException | NonCorrectLevelCardException e) {
           fail();
        }
        System.out.println(game.getCurrentPlayer().getPlayerBoard().getWarehouseDepot().convert());
        System.out.println(game.getCurrentPlayer().getPlayerBoard().getStrongbox().convert());
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
        Game game = new Game(true);
        Player player = new Player("teodoro");
        game.addPlayer(player);
        game.setOrder();
        ExtraProd card = new ExtraProd(1, true, new ArrayList<>(), Resource.COIN);
        ArrayList<LeaderCard> cards = new ArrayList<>();
        cards.add(card);

        System.out.println(game.getDevBoard().convert());
        try {
            game.getCurrentPlayer().getPlayerBoard().getWarehouseDepot().addResource(Resource.SERVANT);
            game.getCurrentPlayer().getPlayerBoard().getWarehouseDepot().addResource(Resource.SERVANT);
            game.getCurrentPlayer().getPlayerBoard().getWarehouseDepot().addResource(Resource.STONE);
            game.getCurrentPlayer().getPlayerBoard().getWarehouseDepot().addResource(Resource.STONE);
            //game.getCurrentPlayer().getPlayerBoard().getWarehouseDepot().addResource(Resource.COIN);
        }catch(FullWarehouseException e){
            fail();
        }
        try {
            game.buyDevCard(3, 0, null);
        }catch (InvalidActionException | InsufficientResourcesException | FullCardSlotException | EmptyDeckException | UnusableCardException | NonCorrectLevelCardException e) {
            fail();
        }

        try {
            //game.getCurrentPlayer().getPlayerBoard().getWarehouseDepot().addResource(Resource.COIN);
            game.getCurrentPlayer().getPlayerBoard().getWarehouseDepot().addResource(Resource.COIN);
            game.getCurrentPlayer().getPlayerBoard().getWarehouseDepot().addResource(Resource.SHIELD);
        }catch(FullWarehouseException e){
            System.out.println(e.getMessage());
            fail();
        }

        game.getCurrentPlayer().getPlayerBoard().addLeaderCards(cards);
        //vediamo tutto:
        System.out.println(game.getCurrentPlayer().getPlayerBoard().getStrongbox().convert());
        System.out.println(game.getCurrentPlayer().getPlayerBoard().getWarehouseDepot().convert());
        System.out.println(game.getCurrentPlayer().getPlayerBoard().getCardSlots().convert());
        System.out.println(game.getCurrentPlayer().getPlayerBoard().getLeaders());

        game.updateTurn();
        System.out.println("ACQUISTO FALLITO");
        try{
            game.devCardProduction(0, Resource.SERVANT, card);
        } catch (InvalidActionException | InsufficientResourcesException | EmptySlotException | UnusableCardException e) {
            System.out.println(e.getMessage());
            assertTrue(true);
        }
        System.out.println("=================================================");
        System.out.println(game.getCurrentPlayer().getPlayerBoard().getWarehouseDepot().convert());
        System.out.println(game.getCurrentPlayer().getPlayerBoard().getStrongbox().convert());


    }

    @Test
    void defaultProduction() {
        /*
        Game game = new Game(true);
        Player player = new Player("teodoro");
        game.addPlayer(player);
        game.setOrder();
        ArrayList<Resource> input = new ArrayList<>();

        //test: resources only from warehouse

        try {
            player.getPlayerBoard().getWarehouseDepot().addResource(Resource.COIN);
            player.getPlayerBoard().getWarehouseDepot().addResource(Resource.COIN);
        }catch(FullWarehouseException e ){
            System.out.println(e.getMessage());
        }
        input.add(Resource.COIN); input.add(Resource.COIN);
        try{
            game.defaultProduction(input, Resource.SERVANT, null, null);
        }catch(InvalidActionException | InsufficientResourcesException | UnusableCardException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(player.getPlayerBoard().getWarehouseDepot().convert());
        System.out.println(player.getPlayerBoard().getStrongbox().convert());

         */

        //only from strongbox
        Game game2 = new Game(true);
        Player player2 = new Player("teodoro2");
        game2.addPlayer(player2);
        game2.setOrder();
        ArrayList<Resource> input2 = new ArrayList<>();


        player2.getPlayerBoard().getStrongbox().addResource(Resource.COIN);
        player2.getPlayerBoard().getStrongbox().addResource(Resource.COIN);
        input2.add(Resource.COIN); input2.add(Resource.COIN);

        System.out.println(player2.getPlayerBoard().getStrongbox().convert());
        game2.updateStrongbox();
        System.out.println(player2.getPlayerBoard().getStrongbox().convert());

        try{
            game2.defaultProduction(input2, Resource.SERVANT, null, null);
        }catch(InvalidActionException | InsufficientResourcesException | UnusableCardException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(player2.getPlayerBoard().getWarehouseDepot().convert());
        System.out.println(player2.getPlayerBoard().getStrongbox().convert());



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