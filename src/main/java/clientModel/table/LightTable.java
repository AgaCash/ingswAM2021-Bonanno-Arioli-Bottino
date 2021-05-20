package clientModel.table;

import clientModel.cards.LightLeaderCard;

import java.util.ArrayList;

public class LightTable {

    private ArrayList<LightLeaderCard> cards;
    private LightDevelopmentBoard developmentBoard;
    private LightMarketBoard marketBoard;

    public LightDevelopmentBoard getDevBoard(){
        return this.developmentBoard;
    }

    public LightMarketBoard getMarketBoard(){
        return this.marketBoard;
    }
}
