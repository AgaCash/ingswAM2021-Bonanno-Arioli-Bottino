package model.utilities;

import clientModel.cards.LightDevelopmentCard;
import clientModel.cards.LightLeaderCard;
import clientModel.player.LightPlayer;
import clientModel.singleplayer.LightLorenzo;
import clientModel.strongbox.LightStrongbox;
import clientModel.table.*;
import clientModel.warehouse.LightWarehouseDepot;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.cards.DevelopmentCard;
import model.cards.LeaderCard;
import model.player.Player;
import model.singleplayer.Lorenzo;
import model.strongbox.Strongbox;
import model.table.*;
import model.warehouse.WarehouseDepot;

import java.util.ArrayList;

public class ModelToLightParser {
    private Gson gson = new GsonBuilder().registerTypeAdapter(LeaderCard.class, new LeaderCardDeserializer()).create();


    //TABLE
    public LightTable parseTable(Table table){
        LightTable lightTable = gson.fromJson(gson.toJson(table), LightTable.class);
        lightTable.setDevelopmentBoard(parseDevBoard(table.getDevBoard()));
        lightTable.setMarketBoard(parseMarket(table.getMarketBoard()));
        return lightTable;
    }

    public LightMarketBoard parseMarket(MarketBoard market){
        return gson.fromJson(gson.toJson(market), LightMarketBoard.class);
    }

    public LightDevelopmentBoard parseDevBoard(DevelopmentBoard board){
        LightDevelopmentBoard lightDevelopmentBoard = new LightDevelopmentBoard();
        ArrayList<LightDevelopmentCard> decks = new ArrayList<>();
        for(int i = 0; i<12; i++)
            decks.add(parseDeck(board.getDeck(i)));
        lightDevelopmentBoard.setDecks(decks);
        return lightDevelopmentBoard;
    }

    public LightDevelopmentCard parseDeck(Deck deck){
        return parseDevelopmentCard(deck.getCard());
    }

    public LightDevelopmentCard parseDevelopmentCard(DevelopmentCard card){
        return gson.fromJson(gson.toJson(card), LightDevelopmentCard.class);
    }


    //PLAYER
    public LightPlayer parsePlayer(Player player){
        LightPlayer lightPlayer = new LightPlayer();
        lightPlayer.setNickname(player.getNickname());
        lightPlayer.setPlayerBoard(parsePlayerBoard(player.getPlayerBoard(), lightPlayer));
        return lightPlayer;
    }

    public LightPlayerBoard parsePlayerBoard(PlayerBoard playerBoard, LightPlayer owner){
        LightPlayerBoard lightPlayerBoard =  new LightPlayerBoard(owner);
        //lightPlayerBoard.setCardSlots(parseCardSlots(playerBoard.getCardSlots()));
        lightPlayerBoard.setWarehouse(parseWarehouse(playerBoard.getWarehouseDepot()));
        lightPlayerBoard.setStrongbox(parseStrongbox(playerBoard.getStrongbox()));
        lightPlayerBoard.setFaithTrack(parseFaithTrack(playerBoard.getFaithTrack()));
        lightPlayerBoard.setLeaderSlot(playerBoard.getLeaders());
        //lightPlayerBoard.setLeaderSlot(parseLeaderSlot(playerBoard.getLeaders()));
        return lightPlayerBoard;
    }

    /*public LightCardSlots parseCardSlots(CardSlots slots){
        LightCardSlots lightCardSlots = new LightCardSlots();
        //return gson.fromJson(gson.toJson(slots), LightCardSlots.class);
        for(int i = 0; i<12; i++)
            lightCardSlots.add(parseDevelopmentCard(slots.getCard(i)));
    }*/

    public LightWarehouseDepot parseWarehouse(WarehouseDepot warehouse){
        return gson.fromJson(gson.toJson(warehouse), LightWarehouseDepot.class);
    }

    public LightStrongbox parseStrongbox(Strongbox strongbox){
        return gson.fromJson(gson.toJson(strongbox), LightStrongbox.class);
    }

   public LightFaithTrack parseFaithTrack(FaithTrack faithTrack){
        return gson.fromJson(gson.toJson(faithTrack), LightFaithTrack.class);
    }

    public ArrayList<LightLeaderCard> parseLeaderSlot(ArrayList<LeaderCard> cards){
        ArrayList<LightLeaderCard> lightLeaderCards = new ArrayList<>();
        for(LeaderCard card: cards)
            lightLeaderCards.add(parseLeaderCard(card));
        return lightLeaderCards;
    }

    public LightLeaderCard parseLeaderCard(LeaderCard card){
        return gson.fromJson(gson.toJson(card), LightLeaderCard.class);
    }

    //SINGLEPLAYER
    public LightLorenzo parseLorenzo(Lorenzo cpu){
        LightLorenzo lorenzo = gson.fromJson(gson.toJson(cpu), LightLorenzo.class);
        //lorenzo.setTokens(parseTokens(cpu.getTokens()));
        lorenzo.setFaithTrack(parseFaithTrack(cpu.getFaithTrack()));
        lorenzo.setDevBoard(parseDevBoard(cpu.getDevelopmentBoard()));
        return lorenzo;
    }




}
