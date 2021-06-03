package clientView;

import clientModel.cards.LightLeaderCard;
import clientModel.resources.LightResource;
import model.resources.Resource;
import network.server.Lobby;

import java.util.ArrayList;

public interface View {
    //resilience
    void serverLostConnection();
    //server info: server ip address and port
    void askServerInfo();
    //username
    void askUsername();
    //lobbyMenu
    void askMenu();
    //switch to singleplayer state
    void switchToGame(boolean singlePlayer);
    //show user that he has to wait for boss to starts the game
    void showWaitingRoom();
    //when lobby is created notify "waiting for players"
    void notifyLobbyCreated();
    //every time
    void notifyPlayerJoined(String username);
    //wait fot the creator that type "start" in the console
    void waitStartGameString();
    //notify player disconnected
    void notifyPlayerDisconnected(String username);
    // // ... reconnected
    void notifyPlayerReconnected(String username);
    // // creator disconnected
    void notifyCreatorDisconnected();
    // // the app is going to be killed
    void quittingApplication();
    //lobby id to join it
    void askLobbyID(ArrayList<Lobby> lobbies);
    //write/press start to begin
    void askStartGame();
    //automatic ask to leader cards, resources ecc
    void askStartup();
    //start turn
    void askTurn();
    //activate leader card
    void askLeaderCardActivation();
    //throwing leader card
    void askLeaderCardThrowing();
    //buy resources
    void askBuyResources();
    //buy dev cards
    void askBuyDevCards();
    //dev card production
    void askDevCardProduction();
    //default production
    void askDefaultProduction();
    //end turn request
    void askEndTurn();
    void askStartItems(ArrayList<LightLeaderCard> quartet, int numResources, boolean faithPoints);
    //todo: i metodi show dovrebbero interrompere un metodo in corso? come?
    //todo farlo alla C style? è un po`arrogante come sistema eh
    void showThrewResources(ArrayList<LightResource> threwResources);
    void showError(String message);
    void showSuccess(String message);
    void endGame();
}
