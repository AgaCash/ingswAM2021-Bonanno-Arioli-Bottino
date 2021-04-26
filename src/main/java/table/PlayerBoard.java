package table;

import cards.CardSlots;
import cards.ExtraProd;
import cards.LeaderCard;
import resources.Resource;
import strongbox.Strongbox;
import warehouse.WarehouseDepot;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;

public class PlayerBoard {
    private CardSlots cardSlots = new CardSlots();
    private WarehouseDepot warehouseDepot = new WarehouseDepot();
    private Strongbox strongbox = new Strongbox();
    private FaithTrack faithTrack = new FaithTrack();
    private FaithBox faithBox = new FaithBox();
    private ArrayList<LeaderCard> leaderSlots;
    private DevelopmentBoard developmentBoard;
    private MarketBoard marketBoard;
    private boolean hasInkwell;
    private int faithPoints = 0;

    //--------------------INITIALIZE--------------------

    // public PlayerBoard (){
    //     this.developmentBoard = getDevBoardInstance();
    //     this.marketBoard = getMarketInstance();
    //     this.hasInkwell = false;  initialization with singleton MarketBoard and DevBoard

    public PlayerBoard(DevelopmentBoard developmentBoard, MarketBoard marketBoard){
        this.developmentBoard = developmentBoard;
        this.marketBoard = marketBoard;
        this.hasInkwell = false;
    }

    public void setInkwell(boolean value){
        this.hasInkwell=value;
    }

    //quando farete il controller stronzi?
    public void selectLeader(ArrayList<LeaderCard> quartet, int first, int second){
        //controller sceglie due delle quattre carte
        this.leaderSlots.add(quartet.get(first));
        this.leaderSlots.add(quartet.get(second));
    }

    //--------------------UTILITIES--------------------

    //@Controller
    private LeaderCard chooseCard(){
        //casino fra
        return null;
    }

    //@AgaCash
    private boolean checkResources(ArrayList<Resource> cost){
        //facciamo dopo dai?
        return false;

    }

    //--------------------BUY DEV CARDS--------------------

    //@Controller
    public void buyDevCard(Deck deck, int slotPosition) throws OperationNotSupportedException {
        Resource discount = checkDiscountLeaderCards();
        ArrayList<Resource> cost = deck.getCost();
        ArrayList<Resource> payment = new ArrayList<>();

        if(discount!=null)
            cost.remove(discount);

        if(checkResources(cost)){
            cardSlots.addCard(slotPosition, deck.popCard());
        }
        else {
            //notificare al controller
        }
    }

    //@Controller
    private Resource checkDiscountLeaderCards(){
        Resource discount = null;
        LeaderCard card = chooseCard();
        try {
            if(card.isEnabled() && card.isDiscount())
                discount = card.whichDiscount();
        }catch(NullPointerException e){
            return null;
        }
        return discount;
    }

    //--------------------MARKET--------------------

    public void buyResources(boolean line, boolean column, int num){
        ArrayList<Resource> bought = new ArrayList<>();
        if(line && !column &&  num>=0 && num<=2) {
            bought = marketBoard.addMarketLine(num, chooseCard());
            for (Resource res : bought) {
                if (res == Resource.FAITH)
                    faithTrack.faithAdvance(faithBox, faithTrack, 1);
                else
                    warehouseDepot.addResource(res);
            }
        }
        else if(!line && column && num>=0 && num<=3) {
            bought = marketBoard.addMarketColumn(num, chooseCard());
            for (Resource res : bought) {
                if (res == Resource.FAITH)
                    faithTrack.faithAdvance(faithBox, faithTrack, 1);
                warehouseDepot.addResource(res);
            }
        }
    }


    //--------------------PRODUCTION--------------------

    public void devCardProduction(int slot, Resource chosenOutput) throws OperationNotSupportedException {
        LeaderCard card = chooseCard();
        if(card.isEnabled() && card.isExtraProd()){
            card.setChosenOutput(chosenOutput);
        }
        else
            card = null;
        this.cardSlots.getCard(slot).createProduction(warehouseDepot, strongbox, (ExtraProd) card);
    }

    public void defaultProduction(ArrayList<Resource> input, Resource output){
        for(Resource ptr : input)
            this.warehouseDepot.removeResource(ptr);
        this.strongbox.addResource(output);
    }

    //--------------------FAITH TRACK--------------------

    /** advances the player's pawn position in the Faith Track, calling faithAdvance in FaitTrack
     * @param advance how many position the player gets
     */
    public void faithAdvance (int advance){
        boolean[] check;
        faithBox = faithTrack.faithAdvance(faithBox, faithTrack, advance);
        check = faithBox.getPopeFlag();
        checkPopeFlags(check);
    }

    public FaithBox getFaithBox(){
        return faithBox;
    }

    public void checkPopeFlags(boolean[] flags){
        if (flags[0])
            //serve che controller faccia chiamare al game il metodo, dandogli indicazione
            //di quale player ha chiamato
            ;
        if (flags[1])
            //same
            ;
        if(flags[2])
            //same
            ;
    }
}
