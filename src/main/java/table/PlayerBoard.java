package table;

import cards.CardSlots;
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

    public PlayerBoard(DevelopmentBoard developmentBoard, MarketBoard marketBoard){
        this.developmentBoard = developmentBoard;
        this.marketBoard = marketBoard;
        this.hasInkwell = false;
    }

    public void setInkwell(boolean value){
        this.hasInkwell=value;
    }
    public void defaultProduction(ArrayList<Resource> input, Resource output){
        for(Resource ptr : input)
            this.warehouseDepot.removeResource(ptr);
        this.strongbox.addResource(output);
    }

    //da completare
    public void buyDevCard(Deck deck, int slotPosition) throws OperationNotSupportedException {
        Resource discount = checkDiscountLeaderCards();
        ArrayList<Resource> cost = deck.getCost();
        ArrayList<Resource> payment = new ArrayList<>();
        if(checkResources(cost, payment)){
            cardSlots.addCard(slotPosition, deck.popCard());
        }
        else
            //notificare al controller
            System.out.println("provv");
    }

    //@AgaCash
    private boolean checkResources(ArrayList<Resource> cost, ArrayList<Resource> payment){
        //facciamo dopo dai?
        return false;

    }

    //@teo
    private Resource checkDiscountLeaderCards(){
        return null;
    }
    //---------------------------------------------------------------
   //quando farete il controller stronzi?
   public void selectLeader(ArrayList<LeaderCard> quartet, int first, int second){
        //controller sceglie due delle quattre carte
       this.leaderSlots.add(quartet.get(first));
       this.leaderSlots.add(quartet.get(second));
   }

    //---------------------------------------------------------------

    //@Teo
    public void leaderProduction(){
        //controlla carte leader isEnabled() e ExtraProd
        //controlla se carta leader.input c'è in warehouse o strongbox
        //toglie risorsa
        //avvia leaderCard.production()
    }
    //--------------------------------------------------------------

    //@Cisco
    public void buyResources(boolean line, boolean column, int num){
        ArrayList<Resource> bought = new ArrayList<>();
        if(line && !column &&  num>=0 && num<=2)
            bought = marketBoard.addMarketLine(num, chooseCard());
        else if(!line && column /*&& ecc*/)
                System.out.println("finire");
        //dopo


    }

    private LeaderCard chooseCard(){
        //casino fra
        return null;
    }

    //--------------------------------------------------------------
    public void devCardProduction(int slot) throws OperationNotSupportedException {
        this.cardSlots.getCard(slot).createProduction(warehouseDepot, strongbox);
    }
    //--------------------------------------------------------------

    //@Cisco
    //tracciato fede



}
