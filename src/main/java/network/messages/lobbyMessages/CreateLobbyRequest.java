package network.messages.lobbyMessages;

import com.google.gson.Gson;
import model.player.Player;
import network.messages.MessageType;
import network.server.LobbyHandler;
import view.VirtualClient;

import javax.naming.SizeLimitExceededException;

public class CreateLobbyRequest extends LobbyMessage{


    public CreateLobbyRequest(String username) {
        super(username, MessageType.CREATEMULTIPLAYER);
    }

    @Override
    public void executeCommand(VirtualClient virtualClient) {
        try {
            LobbyHandler.getInstance().createLobby(new Player(super.getUsername()), virtualClient);
            CreateLobbyResponse s = new CreateLobbyResponse(getUsername());
            virtualClient.getVirtualView().sendLobbyResponse(s);
        }catch (SizeLimitExceededException s){
            //non entrerà mai qua
            s.printStackTrace();
        }
    }
    /*
    public static void main(String[] args){
        Gson gson = new Gson();
        CreateLobbyRequest c = new CreateLobbyRequest(" ");
        System.out.println(gson.toJson(c));
        GetLobbyRequest d = new GetLobbyRequest(" ");
        System.out.println(gson.toJson(d));
        LoginMultiPlayerRequest l = new LoginMultiPlayerRequest(" ", 777);
        System.out.println(gson.toJson(l));
        StartMultiPlayerRequest s = new StartMultiPlayerRequest(" ");
        System.out.println(gson.toJson(s));
        StartSinglePlayerRequest ss = new StartSinglePlayerRequest(" ");
        System.out.println(gson.toJson(ss));

    }*/
}
