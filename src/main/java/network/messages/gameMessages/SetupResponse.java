package network.messages.gameMessages;

import clientController.LightController;
import clientModel.player.LightPlayer;
import clientModel.table.LightDevelopmentBoard;
import clientModel.table.LightMarketBoard;
import model.player.Player;
import model.table.DevelopmentBoard;
import model.table.MarketBoard;
import model.utilities.ModelToLightParser;
import network.messages.MessageType;

import java.util.ArrayList;

public class SetupResponse extends GameMessage {
    private String message;
    private String firstPlayer;
    private LightDevelopmentBoard board;
    private LightMarketBoard market;
    private ArrayList<LightPlayer> players;
    private ModelToLightParser parser = new ModelToLightParser();

    public SetupResponse(String username, String firstPlayer,
                         MarketBoard market, DevelopmentBoard board, ArrayList<Player> players){
        super(username, MessageType.SETUPRESPONSE);
        this.message = " siamo che fega ";
        this.firstPlayer = firstPlayer;
        this.market = parser.parseMarket(market);
        this.board = parser.parseDevBoard(board);
        for(Player p: players)
            this.players.add(parser.parsePlayer(p));
        System.out.println("setup rsponse creata");
    }
    @Override
    public void executeCommand(LightController controller){
        controller.setPlayers(this.players);
        controller.setDevBoard(this.board);
        controller.setMarketBoard(this.market);
        controller.start(this.message);
    }
}
