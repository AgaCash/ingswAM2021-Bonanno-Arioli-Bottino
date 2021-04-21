package table;

import cards.CardSlots;
import cards.LeaderCard;
import warehouse.WarehouseDepot;

import java.util.ArrayList;

public class PlayerBoard {
    private ArrayList<CardSlots> cardSlots;
    private WarehouseDepot warehouseDepot;
    private FaithTrack faithTrack;
    private ArrayList<LeaderCard> leaderSlots;
    private DevelopmentBoard developmentBoard;
    private MarketBoard marketBoard;
    private boolean hasInkwell;
    private int faithPoints;

}
