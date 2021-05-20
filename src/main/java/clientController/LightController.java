package clientController;
import clientView.LightView;
import com.google.gson.Gson;
import model.cards.Discount;
import model.cards.ExtraProd;
import model.cards.LeaderCard;
import model.cards.WhiteConverter;
import model.resources.Resource;
import model.table.Deck;
import network.client.Client;
import network.messages.*;
import network.messages.gameMessages.*;
import view.VirtualClient;

import java.util.ArrayList;

public class LightController {
    private LightView view;
    private Client client;

    public void sendBuyDevCardRequest (Deck deck, int slot, Discount card){
        Gson gson = new Gson();
        BuyDevCardRequest request = new BuyDevCardRequest("Gabibbo", deck, slot, card);
        client.send(gson.toJson(request));
    }

    public void sendBuyResourceRequest (boolean line, int num, WhiteConverter card){
        Gson gson = new Gson();
        BuyResourcesRequest request = new BuyResourcesRequest("x", line, num, card);
        client.send(gson.toJson(request));
    }

    public void sendDefaultProductionRequest (ArrayList<Resource> input, Resource output, ExtraProd card, Resource chosenOutput){
        Gson gson = new Gson();
        DefaultProductionRequest request = new DefaultProductionRequest("x", input, output, card, chosenOutput);
        client.send(gson.toJson(request));
    }

    public void sendDevCardProductionRequest (int slot, Resource chosenResource, ExtraProd card){
        Gson gson = new Gson();
        DevCardProductionRequest request = new DevCardProductionRequest("x", slot, chosenResource, card);
        client.send(gson.toJson(request));
    }

    public void sendLeaderCardActivationRequest (LeaderCard card){
        Gson gson = new Gson();
        LeaderCardActivationRequest request = new LeaderCardActivationRequest("x", card);
        client.send(gson.toJson(request));
    }
}