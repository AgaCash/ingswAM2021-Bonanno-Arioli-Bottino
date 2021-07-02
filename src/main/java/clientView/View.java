package clientView;

import clientModel.cards.LightCardSlots;
import clientModel.cards.LightLeaderCard;
import clientModel.resources.LightResource;
import clientModel.singleplayer.LightLorenzo;
import clientModel.strongbox.LightStrongbox;
import clientModel.table.LightDevelopmentBoard;
import clientModel.table.LightFaithTrack;
import clientModel.table.LightMarketBoard;
import clientModel.warehouse.LightWarehouseDepot;
import network.server.Lobby;

import java.util.ArrayList;

/**
 * The interface containing all the View method and implemented by GUI and CLI classes
 */
public interface View {
    /**
     * Prints that Client lost the connection to Server and quits the application
     */
    void serverLostConnection();

    /**
     * Asks User for Server's IP address and Port
     */
    void askServerInfo();

    /**
     * Asks User for setting up a Username
     */
    void askUsername();

    /**
     * Asks user for Game modality: single player, create a multi player lobby or join to a lobby
     */
    void askMenu();

    /**Prints to the lobby members that a Player joined it
     * @param username the new Joiner's username
     */
    void notifyPlayerJoined(String username);

    /**
     * runs the waitStartGameString() method
     */
    void askStartGame();

    /**
     * Wait for Lobby Owner digit "START"/"start" and then calls LightController method to start the MultiPlayer Game
     */
    void waitStartGameString();

    /**
     * Notifies the Lobby Owner that Server successfully created the Lobby
     */
    void showCreatorWaitingRoom();

    /**Notifies the User that successfully joined to the Lobby and prints the list of members
     * @param usernames the Lobby Member's username ArrayList
     */
    void showWaitingRoom(ArrayList<String> usernames);

    /**
     * Shows to disconnected User that LightController is trying to reconnect to Server
     */
    void showReconnectionToGame();

    /**
     * Shows to User to wait it's turn in a Multi Player Game session
     */
    void waitingForMyTurn();

    /**Prints the available Lobby list in Server with their relatives ID
     * @param lobbies a Lobby ArrayList
     */
    void askLobbyID(ArrayList<Lobby> lobbies);

    /**Notifies to all others Users that a Player is disconnected
     * @param username the disconnected Player's username
     */
    void notifyPlayerDisconnected(String username);

    /**Notifies to all others Users that a Player is reconnected
     * @param username the reconnected Player's username
     */
    void notifyPlayerReconnected(String username);

    /**Notifies to all others Users that the Lobby Creator is disconnected
     */
    void notifyCreatorDisconnected();

    /**Asks to User to choose 2 of 4 LightLeaderCard received by LightController
     * and eventually 0 to 2 LightResources
     * @param quartet the 4-length LightLeaderCards ArrayList
     * @param numResources the number of Resources user has to choose
     * @param faithPoints true if User's Player has earned a FaithPoint
     */
    void askStartItems(ArrayList<LightLeaderCard> quartet, int numResources, boolean faithPoints);

    /**Asks User to do an action in the turn
     */
    void askTurn();

    /**
     * Prints the menu to calls LightController method to ask for a LeaderCard activation
     */
    void askLeaderCardActivation();

    /**
     * Prints the menu to calls LightController method to ask for a LeaderCard threw
     */
    void askLeaderCardThrowing();

    /**
     * Prints the menu to calls LightController method to ask for a Resources purchase at the Market
     */
    void askBuyResources();

    /**
     * Prints the menu to calls LightController method to ask for a DevelopmentCard purchase at the Market
     */
    void askBuyDevCards();

    /**
     * Prints the menu to calls LightController method to ask for a DevelopmentCard production
     */
    void askDevCardProduction();

    /**
     * Prints the menu to calls LightController method to ask for a DevelopmentBoard production
     */
    void askDefaultProduction();

    /**
     * Calls the LightController method to end the turn
     */
    void askEndTurn();

    /**Notifies the User of the Resources list purchased from the Market that were not added to Warehouse
     * @param threwResources a LightResource ArrayList
     */
    void showThrewResources(ArrayList<LightResource> threwResources);

    /**Shows an error message
     * @param message a String containing the message
     */
    void showError(String message);

    /**Shows a success message
     * @param message a String containing the message
     */
    void showSuccess(String message);

    /**Shows the Multi Player Game session winner, the final rank and quits the application
     * @param winner the winner Player's username
     * @param rank a String containing the final rank
     */
    void showRanking(String winner, String rank);

    /**Shows the final message in Single Player session and quit the application
     * @param message a String containing if the User won against Lorenzo or not
     */
    void endSinglePlayerGame(String message);

    /**
     * Quits the application
     */
    void endGame();

    /**Shows others Player action relatives to turn starting and turn ending
     * @param message a String containing
     */
    void showOthersActions(String message);

    /**Shows the Lorenzo actions in the Single player Game session
     * @param lollo the LightLorenzo instance containing all the actions it did in its turn
     */
    void showLorenzoActions(LightLorenzo lollo);

    /**Shows the new Player's CardSlots to all Users
     * @param username the LightPlayer's username
     * @param cardSlots the LightPlayer's new LightCardSlots
     */
    void updateCardSlots(String username, LightCardSlots cardSlots);

    /**Shows the new Player's WarehouseDepot to all Users
     * @param username the LightPlayer's username
     * @param warehouseDepot the LightPlayer's new LightWarehouseDepot
     */
    void updateWarehouseDepot(String username, LightWarehouseDepot warehouseDepot);

    /**Shows the new Player's Strongbox to all Users
     * @param username the LightPlayer's username
     * @param strongbox  the LightPlayer's new LightStrongbox
     */
    void updateStrongbox(String username, LightStrongbox strongbox);

    /**Shows the new Player's FaithTrack to all Users
     * @param username the LightPlayer's username
     * @param faithTrack the LightPlayer's new LightFaithTrack
     */
    void updateFaithTrack(String username, LightFaithTrack faithTrack);

    /**Shows the new Player's LeaderSlot to all Users
     * @param username the LightPlayer's username
     * @param leaderSlot the LightPlayer's new LightLeaderCards
     */
    void updateLeaderSlot(String username, ArrayList<LightLeaderCard> leaderSlot);

    /**Shows the new LightDevelopmentBoard
     * @param board a LightDevelopmentBoard instance
     */
    void updateDevBoard(LightDevelopmentBoard board);

    /**Shows the new LightMarketBoard
     * @param market a LightMarket instance
     */
    void updateMarketBoard(LightMarketBoard market);
}
