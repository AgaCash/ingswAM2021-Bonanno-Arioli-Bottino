package clientView;

import model.resources.Resource;

import java.util.ArrayList;

public interface View {
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
    //
    void notifyPlayerJoined(String username);
    //lobby id to join it
    void askLobbyID();
    //lobby name to create it
    void askNewLobbyName();
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
    //todo: i metodi show dovrebbero interrompere un metodo in corso? come?
    //todo farlo alla C style? è un po`arrogante come sistema eh
    void showThrewResources(ArrayList<Resource> threwResources);
    void showError(String message);
    void showSuccess(String message);
}
