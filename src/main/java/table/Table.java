package table;

import cards.LeaderCard;
import utilities.JsonParser;

import java.util.ArrayList;

public class Table {
    private ArrayList<LeaderCard> cards = new ArrayList<>();


    public ArrayList<LeaderCard> initialize(){
        this.cards.addAll(new JsonParser("src/main/resources/discount.json").getDiscountCards());
        this.cards.addAll(new JsonParser("src/main/resources/extraDepot.json").getExtraDepotCards());
        this.cards.addAll(new JsonParser("src/main/resources/whiteConverter.json").getWhiteConverterCard());
        this.cards.addAll(new JsonParser("src/main/resources/extraProd.json").getExtraProdCards());
        return this.cards;
    }
}
