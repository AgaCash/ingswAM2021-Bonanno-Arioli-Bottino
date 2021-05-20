package clientView;

public interface View {
    //server info: server ip address and port
    void askServerInfo();
    //username
    void askUsername();
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
}
