package network.messages.gameMessages;

import com.google.gson.Gson;
import controller.Controller;
import exceptions.FullWarehouseException;
import model.cards.WhiteConverter;
import network.messages.MessageType;

import java.io.PrintWriter;

public class MarketRequest extends GameMessage{
    private WhiteConverter card;
    private boolean line;
    private int num;

    public MarketRequest(String username) {
        super(username, MessageType.MARKET);
    }

        @Override
    public void executeCommand(Controller controller, PrintWriter out) {
        Gson gson = new Gson();
        try {
            controller.buyResources(line, num, card);
            out.println(gson.toJson(new MarketResponse(this.getUsername()), MarketResponse.class));
            update();
        } catch (FullWarehouseException e) {
            //out.println(gson.toJson(new MarketResponse(this.getUsername()), MarketResponse.class));
            //diobono che merda
        }
    }

    private void update(){

        //da FARE

    }
}
