
package network.messages;

        import network.messages.lobbyMessages.*;
        import network.messages.gameMessages.FailedActionNotify;

public enum MessageType {
    //SETUP
    CHECK_USERNAME_REQUEST,
    CHECK_USERNAME_RESPONSE,

    //LOGIN
    REGISTER_USERNAME(RegisterUsernameRequest.class, "LOBBY"),
    JOIN_SINGLEPLAYER(StartSinglePlayerRequest.class, "LOBBY"),
    JOINMULTIPLAYER(LoginMultiPlayerRequest.class, "LOBBY"),
    CREATEMULTIPLAYER(CreateLobbyRequest.class, "LOBBY"),
    GETLOBBIES(GetLobbyRequest.class, "LOBBY"),
    LOBBYLISTRESPONSE(GetLobbyResponse.class, "LOBBY"),
    STANDARDRESPONSE(StandardLobbyResponse.class, "LOBBY"),
    //LobbyStartGame, only to set controllers to right views and notify clients that game is going to start
    LOBBYSTARTGAME_REQUEST(StartMultiPlayerRequest.class, "LOBBY"),
    LOBBYSTARTGAME_RESPONSE(StartMultiPlayerResponse.class, "LOBBY"),

    //EffectiveStartGame
    STARTGAME,
    STARTGAMERESPONSE,
    SETUP,
    SETUPRESPONSE,

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
    FAILEDACTIONNOTIFY(FailedActionNotify.class, "NOTIFY");

    private final Class<? extends Message> classType;
    private final String upperType;

    private MessageType(Class<? extends Message> classType, String upperType){
        this.classType = classType;
        this.upperType = upperType;
    }


    private MessageType(){
        classType = null;
        upperType = null;
    }

    public String getUpperType() {
        return upperType;
    }

    public Class<? extends Message> getClassType() {
        return classType;
    }
}
