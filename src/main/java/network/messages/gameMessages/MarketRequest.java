package network.messages.gameMessages;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import controller.Controller;
import network.messages.MessageType;

import java.io.PrintWriter;

public class MarketRequest extends GameMessage{

    private boolean line;
    private int num;

    public MarketRequest(String username) {
        super(username, MessageType.MARKET);
    }

    @Override
    public void executeCommand(Controller controller, PrintWriter out) {
        Gson gson = new Gson();
        controller.market(line, num);
        out.println(gson.toJson(new MarketResponse(this.getUsername()), MarketResponse.class));
    }
}
