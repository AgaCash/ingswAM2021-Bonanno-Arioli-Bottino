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
    private ArrayList<LeaderCard> leaderSlots;
    private DevelopmentBoard developmentBoard;
    private MarketBoard marketBoard;
    private boolean hasInkwell;
    private int faithPoints = 0;
    private FaithBox faithBox = new FaithBox();

    //--------------------INITIALIZE--------------------

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
        ArrayList<Resource> warehouseResources = new ArrayList<>();
        ArrayList<Resource> strongboxResources = new ArrayList<>();
        ArrayList<Resource> tmp = new ArrayList<>();

        for(Resource r :cost){
            tmp.clear();
            tmp.add(r);
            if(warehouseDepot.isPresent(tmp)){
                warehouseResources.add(r);
            }else if(strongbox.isPresent(tmp)){
                strongboxResources.add(r);
            }else{
                return false;
            }
        }
        //TEORICAMENTE SE LE RISORSE NON CI SONO NON RAGGIUNGERA' MAI QUESTO PUNTO DELLA FUNZIONE

        for(Resource r: warehouseResources){
            warehouseDepot.removeResource(r);
        }
        for(Resource r: strongboxResources){
            strongbox.removeResource(r);
        }

        return true;
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
}
