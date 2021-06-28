package model.table;

import clientModel.table.LightTable;
import model.cards.LeaderCard;
import utilities.JsonParser;

import java.util.ArrayList;
import java.util.Collections;

/**The class containing the DevelopmentBoard and MarketBoard instance
 *
 */
public class Table {
    private ArrayList<LeaderCard> cards;
    private DevelopmentBoard developmentBoard;
    private MarketBoard marketBoard;

    /**
     * Initializes the DevelopmentBoard instance and the MarketBoard instance and LeaderCard instances
     */
    public Table(){
        this.developmentBoard = new DevelopmentBoard();
        this.marketBoard = new MarketBoard();
        this.cards = new ArrayList<>();
        initializeLeaderCards();
    }

    /**
     * Load from JSON the LeaderCards instances
     */
    private void initializeLeaderCards(){
        this.cards.addAll(new JsonParser("discount.json").getDiscountCards());
        this.cards.addAll(new JsonParser("extraDepot.json").getExtraDepotCards());
        this.cards.addAll(new JsonParser("whiteConverter.json").getWhiteConverterCard());
        this.cards.addAll(new JsonParser("extraProd.json").getExtraProdCards());
        Collections.shuffle(this.cards);
    }

    /**Returns the DevelopmentBoard current state
     * @return the DevelopmentBoard instance
     */
    public DevelopmentBoard getDevBoard(){
        return this.developmentBoard;
    }

    /**Returns the MarketBoard current state
     * @return the MarketBoard instance
     */
    public MarketBoard getMarketBoard(){
        return this.marketBoard;
    }

    /**Return a LeaderCard quartet
     * @return a LeaderCard ArrayList
     */
    public synchronized ArrayList<LeaderCard> sendQuartet(){

        ArrayList<LeaderCard> quartet = new ArrayList<>();
        for(int i=0; i<4; i++){
            quartet.add(pop());
        }
        return quartet;
    }

    /**Pops a LeaderCard instance from initial deck
     * @return a LeaderCard instance
     */
    private LeaderCard pop(){
        LeaderCard card = cards.get(0);
        cards.remove(card);
        return card;
    }

    /**Converts the actual Table instance in a LightTable instance for LightModel
     * @return a LightTable instance
     */
    public LightTable convert(){
        LightTable table = new LightTable();

        table.setMarketBoard(this.marketBoard.convert());
        table.setDevelopmentBoard(this.developmentBoard.convert());

        return table;
    }

}
