package model.table;

import model.cards.LeaderCard;
import model.utilities.JsonParser;

import java.util.ArrayList;
import java.util.Collections;

public class Table {
   // private static Table instance = null;
    private ArrayList<LeaderCard> cards;
    private DevelopmentBoard developmentBoard;
    private MarketBoard marketBoard;
/*
    private Table(){
        developmentBoard.getDevBoardInstance();
        marketBoard.getMarketInstance();
        cards = initializeLeaderCards();
    }

    public static Table getTableInstance(){
        if(instance == null)
            instance = new Table();
        return instance;

    }

    public void deleteInstance(){
        instance = null;
    }*/

    public Table(){
        developmentBoard = new DevelopmentBoard();
        marketBoard = new MarketBoard();
    }

    private ArrayList<LeaderCard> initializeLeaderCards(){
        this.cards.addAll(new JsonParser("src/main/resources/discount.json").getDiscountCards());
        this.cards.addAll(new JsonParser("src/main/resources/extraDepot.json").getExtraDepotCards());
        this.cards.addAll(new JsonParser("src/main/resources/whiteConverter.json").getWhiteConverterCard());
        this.cards.addAll(new JsonParser("src/main/resources/extraProd.json").getExtraProdCards());
        Collections.shuffle(cards);
        return this.cards;
    }

    public DevelopmentBoard getDevBoard(){
        return this.developmentBoard;
    }

    public MarketBoard getMarketBoard(){
        return this.marketBoard;
    }

    public ArrayList<LeaderCard> sendQuartet(){
        ArrayList<LeaderCard> quartet = new ArrayList<>();
        for(int i=0; i<4; i++)
            quartet.add(pop());
        return quartet;
    }

    private LeaderCard pop(){
        LeaderCard card = cards.get(0);
        cards.remove(card);
        return card;
    }

}
