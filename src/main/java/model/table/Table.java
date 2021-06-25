package model.table;

import clientModel.table.LightTable;
import model.cards.LeaderCard;
import utilities.JsonParser;

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
        this.developmentBoard = new DevelopmentBoard();
        this.marketBoard = new MarketBoard();
        this.cards = new ArrayList<>();
        initializeLeaderCards();
    }

    private void initializeLeaderCards(){
        this.cards.addAll(new JsonParser("discount.json").getDiscountCards());
        this.cards.addAll(new JsonParser("extraDepot.json").getExtraDepotCards());
        this.cards.addAll(new JsonParser("whiteConverter.json").getWhiteConverterCard());
        this.cards.addAll(new JsonParser("extraProd.json").getExtraProdCards());
        //System.out.println(this.cards);
        Collections.shuffle(this.cards);
    }

    public DevelopmentBoard getDevBoard(){
        return this.developmentBoard;
    }

    public MarketBoard getMarketBoard(){
        return this.marketBoard;
    }

    public synchronized ArrayList<LeaderCard> sendQuartet(){

        ArrayList<LeaderCard> quartet = new ArrayList<>();
        for(int i=0; i<4; i++){
            quartet.add(pop());
        }
        return quartet;
    }

    private LeaderCard pop(){
        LeaderCard card = cards.get(0);
        cards.remove(card);
        return card;
    }

    public LightTable convert(){
        LightTable table = new LightTable();

        table.setMarketBoard(this.marketBoard.convert());
        table.setDevelopmentBoard(this.developmentBoard.convert());

        return table;
    }

}
