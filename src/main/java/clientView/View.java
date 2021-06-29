package clientView;

import clientModel.cards.LightCardSlots;
import clientModel.cards.LightLeaderCard;
import clientModel.resources.LightResource;
import clientModel.strongbox.LightStrongbox;
import clientModel.table.LightFaithTrack;
import clientModel.warehouse.LightWarehouseDepot;
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
    //every time
    void notifyPlayerJoined(String username);
    //write/press start to begin
    void askStartGame();
    //wait fot the creator that type "start" in the console
    void waitStartGameString();
    //  Waiting room for creator
    void showCreatorWaitingRoom();
    //  Waiting room for plebei
    void showWaitingRoom(ArrayList<String> usernames);
    //when user reconnect show a message
    void showReconnectionToGame();
    //show message wait for turn
    void waitingForMyTurn();
    //lobby id to join it
    void askLobbyID(ArrayList<Lobby> lobbies);
    //notify player disconnected
    void notifyPlayerDisconnected(String username);
    // // ... reconnected
    void notifyPlayerReconnected(String username);
    // // creator disconnected
    void notifyCreatorDisconnected();
    void askStartItems(ArrayList<LightLeaderCard> quartet, int numResources, boolean faithPoints);
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
    void showThrewResources(ArrayList<LightResource> threwResources);
    void showError(String message);
    void showSuccess(String message);
    void showRanking(String winner, String rank);
    void endGame();
    void showOthersActions(String message);
    void showLorenzoActions(String message);

    void updateCardSlots(String username, LightCardSlots cardSlots);
    void updateWarehouseDepot(String username, LightWarehouseDepot warehouseDepot);
    void updateStrongbox(String username, LightStrongbox strongbox);
    void updateFaithTrack(String username, LightFaithTrack faithTrack);
    void updateLeaderSlot(String username, ArrayList<LightLeaderCard> leaderSlot);
}
