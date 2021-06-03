
package network.messages;

        import network.messages.gameMessages.*;
        import network.messages.lobbyMessages.*;
        import network.messages.pingMessages.PingGameMessage;
        import network.messages.pingMessages.PingLobbyMessage;
        import network.messages.pingMessages.PongGameMessage;
        import network.messages.pingMessages.PongLobbyMessage;

public enum MessageType {
    //PING
    PING_GAME(PingGameMessage.class, "PING"),
    PING_LOBBY(PingLobbyMessage.class, "PING"),
    PONG_LOBBY(PongLobbyMessage.class, "PONG"),
    PONG_GAME(PongGameMessage.class, "PONG"),

    //RESILIENCE
    LOBBY_PLAYER_DISCONNECTED(LobbyPlayerDisconnectedMessage.class, ""),
    PLAYER_DISCONNECTED(PlayerDisconnectedMessage.class, ""),
    PLAYER_RECONNECTED(PlayerReconnectedMessage.class, ""),
    RECONNECTION_UPDATE_REQUEST(UpdateReconnectionRequest.class, ""),
    RECONNECTION_UPDATE_RESPONSE(UpdateReconnectionResponse.class, ""),

    //SETUP //SERVE ANCORA? //STA COSA LA FA GIA' REGISTER_USERNAME
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
    SETUP(SetupRequest.class, "GAME"),
    SETUPRESPONSE(SetupResponse.class, "GAME"),

    //ACTIONS
    // PRONTO AD ESPLODERE (GUARDAMI PER FUTURI PROBLEMI)
    STARTINGTURNCHOOSES,
    STARTINGUPDATE,
    MARKET(BuyResourcesRequest.class, "GAME"),
    MARKETUPDATE(BuyResourcesResponse.class, "GAME"),
    DEFPRODUCTION(DefaultProductionRequest.class, "GAME"),
    DEFPRODUCTIONUPDATE(DefaultProductionResponse.class, "GAME"),
    DEVCARDPRODUCTION(DevCardProductionRequest.class, "GAME"),
    DEVCARDPRODUCTIONUPDATE(DevCardProductionResponse.class, "GAME"),
    BUYDEVCARDS(BuyDevCardRequest.class, "GAME"),
    BUYDEVCARDSUPDATE(BuyDevCardResponse.class, "GAME"),
    LEADERCARD(LeaderCardActivationRequest.class, "GAME"),
    LEADERCARDUPDATE(LeaderCardActivationResponse.class, "GAME"),
    ENDTURN(EndTurnRequest.class, "GAME"),
    ENDTURNUPDATE(EndTurnResponse.class, "GAME"),

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
