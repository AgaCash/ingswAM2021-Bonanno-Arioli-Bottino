package network.messages.gameMessages;

import com.google.gson.Gson;
import controller.Controller;
import model.cards.WhiteConverter;
import network.messages.MessageType;
import view.VirtualView;

import java.util.ArrayList;

public class BuyResourcesRequest extends GameMessage{
    private boolean line;
    private int num;
    private WhiteConverter card;

    public BuyResourcesRequest(String username){
        super(username, MessageType.MARKET);
    }

    @Override
    public void executeCommand(Controller controller, ArrayList<VirtualView> views){
        Gson gson = new Gson();
        controller.buyResources(line, num, card);
        //out.println(gson.toJson(new BuyDevCardResponse(this.getUsername()), MarketResponse.class));
        controller.getThrewResources();
        //out.println(gson.toJson(new ThrewResourcesNotify(this.getUsername(), controller.getThrewResources()), ThrewResourcesNotify));
        update();

    }

    public void update(){
        //tmpMarketBoard = game.getTable().getMarket().status();
        //tmpWarehouse = game.getPlayer().getWare
        //CHIEDERE

    }

}
