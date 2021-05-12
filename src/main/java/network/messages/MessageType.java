package network.messages;

import network.messages.lobbyMessages.CreateLobbyRequest;
import network.messages.lobbyMessages.GetLobbyRequest;
import network.messages.lobbyMessages.GetLobbyResponse;

public enum MessageType {
    //LOGIN
    JOIN_SINGLEPLAYER,
    JOINMULTIPLAYER,
    CREATEMULTIPLAYER(CreateLobbyRequest.class, "LOBBY"),
    GETLOBBIES(GetLobbyRequest.class, "LOBBY"),
    LOBBYLISTRESPONSE(GetLobbyResponse.class, "LOBBY"),
    STANDARDRESPONSE,
    STARTGAME,

    //ACTIONS
    STARTINGTURNCHOOSES,
    STARTINGUPDATE,
    MARKET,
    MARKETUPDATE,
    PRODUCTION,
    PRODUCTIONUPDATE,
    BUYDEVCARDS,
    BUYDEVCARDSUPDATE,
    LEADERCARD,
    LEADERCARDUPDATE,
    ENDTURN,
    ENDTURNUPDATE,
    //notify
    FAILEDACTIONNOTIFY;

    public final Class<? extends Message> c;
    public final String megaType;

    private MessageType(Class<? extends Message> c, String megaType){
        this.c = c;
        this.megaType = megaType;
    }


    private MessageType(){
        c = null;
        megaType = null;
    }

}
