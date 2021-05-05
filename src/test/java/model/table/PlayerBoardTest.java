package model.table;

import model.Player;
import model.cards.*;
import model.colour.Colour;
import model.resources.Resource;
import model.strongbox.Strongbox;
import model.warehouse.WarehouseDepot;
import org.junit.jupiter.api.Test;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PlayerBoardTest {

    @Test
    void selectLeader() {
        ArrayList<LeaderCard> chosenCards = new ArrayList<>();
        ArrayList<LeaderCard> quartet = new ArrayList<>();
        PlayerBoard player = new PlayerBoard(new CardSlots(), new WarehouseDepot(),
                                           new Strongbox(), new FaithTrack(),
                                            DevelopmentBoard.getDevBoardInstance(),
                                            MarketBoard.getMarketInstance(), chosenCards);

        quartet.add(new ExtraDepot(1, false, new ArrayList<>(), new ArrayList<>()));
        quartet.add(new WhiteConverter(2, false, new ArrayList<>(), Resource.SERVANT));
        quartet.add(new Discount(3, false, new ArrayList<>(), Resource.COIN));
        quartet.add(new ExtraDepot(16, false, new ArrayList<>(), new ArrayList<>()));

        player.selectLeader(quartet, 1, 2);
        LeaderCard first = quartet.get(1);
        LeaderCard second = quartet.get(2);
        assertEquals(chosenCards.get(0), first);
        assertEquals(chosenCards.get(1), second);
    }

    @Test
    void buyDevCard() throws OperationNotSupportedException {
        DevelopmentBoard board = DevelopmentBoard.getDevBoardInstance();
        Strongbox strongbox = new Strongbox();
        WarehouseDepot warehouseDepot = new WarehouseDepot();
        CardSlots cardSlots = new CardSlots();

        PlayerBoard player = new PlayerBoard(cardSlots, warehouseDepot,
                strongbox, new FaithTrack(),
                board,
                MarketBoard.getMarketInstance(), new ArrayList<>());
        Deck deck = board.getDeck(1);
        DevelopmentCard card = deck.getCard();
        assertNotNull(card);
        player.buyDevCard(deck, 1, null);
        assertNull(cardSlots.getCard(1));

        ArrayList<Resource> cost = card.getCost();
        for(Resource res : cost)
            warehouseDepot.addResource(res);
        assertTrue(warehouseDepot.isPresent(cost));
        player.buyDevCard(deck, 1, null);
        assertEquals(cardSlots.getCard(1), card);
        assertFalse(warehouseDepot.isPresent(cost));

        card = deck.getCard();
        assertNotNull(card);
        cost = deck.getCost();
        for(Resource res : cost)
            warehouseDepot.addResource(res);
        assertTrue(warehouseDepot.isPresent(cost));
        Resource discount = cost.get(0);
        Discount leaderCard = new Discount(0, true, null, discount);
        player.buyDevCard(deck, 2, leaderCard);
        assertEquals(cardSlots.getCard(2), card);
        assertFalse(warehouseDepot.isPresent(cost));
        ArrayList<Resource> discounts = new ArrayList<>();
        discounts.add(discount);
        assertTrue(warehouseDepot.isPresent(discounts));

        //spaccarlo...
    }

    @Test
    void buyResources() {
        WarehouseDepot warehouseDepot = new WarehouseDepot();
        Strongbox strongbox = new Strongbox();
        MarketBoard market = MarketBoard.getMarketInstance();
        market.initializeMarbleGrid();

        PlayerBoard player = new PlayerBoard(new CardSlots(), warehouseDepot,
                strongbox, new FaithTrack(),
                DevelopmentBoard.getDevBoardInstance(),
                market, new ArrayList<>());

        ArrayList<Resource> tokenResources = new ArrayList<>();

        for(int i = 0; i<=2; i++)
            tokenResources.add(market.getMarble(i, 1).convertMarble(null));
        player.buyResources(false, true, 1, null);
        tokenResources.removeAll(warehouseDepot.getThrown());
        tokenResources.remove(Resource.FAITH);
        assertTrue(warehouseDepot.isPresent(tokenResources));

        tokenResources = new ArrayList<>();
        for(int i = 0; i<=2; i++)
            tokenResources.add(market.getMarble(i, 1).convertMarble(null));
        player.buyResources(false, true, 1, null);
        tokenResources.removeAll(warehouseDepot.getThrown());
        tokenResources.remove(Resource.FAITH);
        assertTrue(warehouseDepot.isPresent(tokenResources));



        //spaccalo !!
        /*
        ArrayList<Resource> extra = new ArrayList<>();
        extra.add(Resource.COIN);
        extra.add(Resource.COIN);
        ExtraDepot card = new ExtraDepot(0, true, null, extra);
        tokenResources = new ArrayList<>();
        for(int i = 0; i<=2; i++)
            tokenResources.add(market.getMarble(i, 1).convertMarble(null));
        player.buyResources(false, true, 1, null);
        tokenResources.addAll(warehouseDepot.getThrown());
        tokenResources.remove(Resource.FAITH);
        //assertTrue(warehouseDepot.isPresent(tokenResources));*/


     }

    @Test
    void devCardProduction() throws OperationNotSupportedException {
        //initializing
        WarehouseDepot warehouseDepot = new WarehouseDepot();
        Strongbox strongbox = new Strongbox();
        CardSlots cardSlots = new CardSlots();
        //2 coin in input
        ArrayList<Resource> prodInput = new ArrayList<>();
        prodInput.add(Resource.COIN);
        prodInput.add(Resource.COIN);
        //2 servant produced
        ArrayList<Resource> prodOutput = new ArrayList<>();
        prodOutput.add(Resource.SERVANT);
        prodOutput.add(Resource.SERVANT);
        //card
        DevelopmentCard card = new DevelopmentCard(0, Colour.BLUE, 1,1,  new ArrayList<Resource>(),
                prodInput, prodOutput);
        cardSlots.addCard(1, card);
        PlayerBoard player = new PlayerBoard(cardSlots, warehouseDepot,
                strongbox, new FaithTrack(),
                DevelopmentBoard.getDevBoardInstance(),
                MarketBoard.getMarketInstance(), new ArrayList<>());
        //1 coin in model.warehouse 1 in model.strongbox
        warehouseDepot.addResource(prodInput.get(0));
        strongbox.addResource(prodInput.get(1));
            /*
            warehouseDepot.status().forEach(System.out::println);
            System.out.println("+++++");
            strongbox.status().forEach(System.out::println);
            System.out.println("+++++");*/

        player.devCardProduction(1, Resource.STONE, null);
            /*
            warehouseDepot.status().forEach(System.out::println);
            System.out.println("+++++");
            strongbox.status().forEach(System.out::println);
            System.out.println("+++++");*/

        ArrayList<Resource> prodInputFromWarehouse = new ArrayList<>();
        prodInputFromWarehouse.add(Resource.COIN);
        assertFalse(warehouseDepot.isPresent(prodInputFromWarehouse));
        assertTrue(strongbox.isPresent(prodOutput));

        ExtraProd leaderCard = new ExtraProd(0, true, null, Resource.SHIELD);
        warehouseDepot.addResource(Resource.SHIELD);
        warehouseDepot.addResource(prodInput.get(0));
        warehouseDepot.addResource(prodInput.get(1));

        player.devCardProduction(1, Resource.COIN, leaderCard);
        prodOutput.add(Resource.COIN);
        assertFalse(warehouseDepot.isPresent(prodInput));
        assertTrue(strongbox.isPresent(prodOutput));

        //spaccalo!!!!!!!!!! */


  }

    @Test
    void defaultProduction() {
        WarehouseDepot warehouseDepot = new WarehouseDepot();
        Strongbox strongbox = new Strongbox();
        PlayerBoard player = new PlayerBoard(new CardSlots(), warehouseDepot,
                strongbox, new FaithTrack(),
                DevelopmentBoard.getDevBoardInstance(),
                MarketBoard.getMarketInstance(), new ArrayList<>());
        ExtraProd card = new ExtraProd(0, true, new ArrayList<DevelopmentCard>(),
                                        Resource.COIN);
        //ExtraProdInput
        warehouseDepot.addResource(Resource.COIN);
        //ProdInput
        warehouseDepot.addResource(Resource.SHIELD);
        strongbox.addResource(Resource.SHIELD);
        ArrayList<Resource> prodInput = new ArrayList<>();
        prodInput.add(Resource.SHIELD);
        prodInput.add(Resource.SHIELD);
            /*warehouseDepot.status().forEach(System.out::println);
            System.out.println("+++++");
            strongbox.status().forEach(System.out::println);
            System.out.println("+++++");*/
      player.defaultProduction(prodInput, Resource.STONE, card, Resource.STONE);

        ArrayList<Resource> prodOutput = new ArrayList<>();
        prodOutput.add(Resource.STONE);
        prodOutput.add(Resource.STONE);
            //warehouseDepot.status().forEach(System.out::println);
            //System.out.println("+++++");
        assertTrue(strongbox.isPresent(prodOutput));
        prodInput.clear();
        prodInput.add(Resource.COIN);
        prodInput.add(Resource.SHIELD);
        assertFalse(warehouseDepot.isPresent(prodInput));
        prodInput.clear();
        prodInput.add(Resource.SHIELD);
            //strongbox.status().forEach(System.out::println);
            //System.out.println("+++++");


    }

    @Test
    void faithAdvanceTest() {
        DevelopmentBoard board = DevelopmentBoard.getDevBoardInstance();
        MarketBoard market = MarketBoard.getMarketInstance();
        PlayerBoard player = new PlayerBoard(new Player(), board, market);
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

    @Test
    void activateLeaderCard(){
        
    }
}
