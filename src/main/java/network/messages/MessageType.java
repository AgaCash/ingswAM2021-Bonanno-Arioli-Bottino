
package network.messages;

        import network.messages.gameMessages.StartGameRequest;
        import network.messages.gameMessages.StartGameResponse;
        import network.messages.lobbyMessages.*;

public enum MessageType {
    //SETUP
    CHECK_USERNAME_REQUEST,
    CHECK_USERNAME_RESPONSE,

    //LOGIN
    REGISTER_USERNAME(RegisterUsernameRequest.class, "LOBBY"),
    REGISTER_USERNAME_RESPONSE(RegisterUsernameResponse.class, "LOBBY"),
    JOIN_SINGLEPLAYER(StartSinglePlayerRequest.class, "LOBBY"),
    JOIN_SINGLEPLAYER_RESPONSE(StartSinglePlayerResponse.class, "LOBBY"),
    JOINMULTIPLAYER(LoginMultiPlayerRequest.class, "LOBBY"),
    JOINMULTIPLAYER_RESPONSE(LoginMultiPlayerResponse.class, "LOBBY"),
    CREATEMULTIPLAYER(CreateLobbyRequest.class, "LOBBY"),
    CREATEMULTIPLAYER_RESPONSE(CreateLobbyResponse.class, "LOBBY"),
    GETLOBBIES(GetLobbyRequest.class, "LOBBY"),
    GETLOBBIES_RESPONSE(GetLobbyResponse.class, "LOBBY"),
    STANDARDRESPONSE(StandardLobbyResponse.class, "LOBBY"),
    //LobbyStartGame, only to set controllers to right views and notify clients that game is going to start
    LOBBYSTARTGAME_REQUEST(StartMultiPlayerRequest.class, "LOBBY"),
    LOBBYSTARTGAME_RESPONSE(StartMultiPlayerResponse.class, "LOBBY"),

    //EffectiveStartGame
    STARTGAME(StartGameRequest.class, "GAME"),
    STARTGAMERESPONSE(StartGameResponse.class, "GAME"),
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

    //da eliminare
    FAILEDACTIONNOTIFY;


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
