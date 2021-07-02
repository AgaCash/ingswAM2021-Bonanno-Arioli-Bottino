package GUI;

import GUI.scenes.ChooseStartingThingsScene;
import GUI.scenes.CreateAndStartScene;
import GUI.scenes.GameScene;
import GUI.scenes.JoinAndWaitScene;
import clientController.LightController;
import clientModel.cards.LightCardSlots;
import clientModel.cards.LightLeaderCard;
import clientModel.resources.LightResource;
import clientModel.singleplayer.LightLorenzo;
import clientModel.strongbox.LightStrongbox;
import clientModel.table.LightDevelopmentBoard;
import clientModel.table.LightFaithTrack;
import clientModel.table.LightMarketBoard;
import clientModel.warehouse.LightWarehouseDepot;
import clientView.View;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.util.Duration;
import network.server.Lobby;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Singleton Class that handles the GUI view situations and calling the right showing methods, communicating with Light Controller and the Game Scenes
 */
public class GUI implements View {
    private LightController controller;
    private Stage primaryStage;
    private FXMLLoader fxmlLoader;
    private boolean isGameBeenInitialized;
    private GameScene gameGuiController;
    private Parent gameRoot;

    private static GUI instance = null;

    /** it returns the current GUI instance if it already exists, otherwise it creates a new one
     * @return
     */
    public static GUI getInstance() {
        if(instance == null)
            instance = new GUI();
        return instance;
    }

    /**
     * it initialise the GUI connecting it to a controller
     */
    private GUI(){
        isGameBeenInitialized = false;
        this.controller = new LightController(this);
    }

    /** Sets the current view stage
     * @param stage the stage to set
     */
    public void setStage(Stage stage) {
        this.primaryStage = stage;
    }

    /** It gets the current light controller
     * @return the light controller
     */
    public LightController getController() {
        return controller;
    }

    /**
     * it sets the ask info scene as a main stage
     */
    @Override
    public void askServerInfo() {
        Platform.runLater(()->{
            Parent root = null;
            try {
                fxmlLoader = new FXMLLoader(getClass().getResource("/FXMLFiles/serverSetup.fxml"));
                root = fxmlLoader.load();
                primaryStage.setScene(new Scene(root));
                primaryStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * it sets the ask username scene as a main stage
     */
    @Override
    public void askUsername() {
        Platform.runLater(()->{
            Parent root = null;
            try {
                fxmlLoader = new FXMLLoader(getClass().getResource("/FXMLFiles/login.fxml"));
                root = fxmlLoader.load();
                //fxmlLoader.getController();
                primaryStage.setScene(new Scene(root));
                primaryStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * it handles the connection lost error by calling the proper alert showing
     */
    @Override
    public void serverLostConnection() {
        Platform.runLater(()->{
            primaryStage.setScene(null);
            showError("LOST SERVER CONNECTION \nClosing the application in 5 seconds");
            Timeline timer = new Timeline(
                    new KeyFrame(Duration.seconds(5), (event)->controller.instantQuittingApplication())
            );
            timer.play();
        });
    }

    /**
     * Sets the ask menu scene as a main stage
     */
    @Override
    public void askMenu() {
        Platform.runLater(()->{
            Parent root = null;
            try {
                fxmlLoader = new FXMLLoader(getClass().getResource("/FXMLFiles/menuSelection.fxml"));
                root = fxmlLoader.load();
                primaryStage.setScene(new Scene(root));
                primaryStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }


    /** it calls the showing notify of a new player in the lobby
     * @param username the name of the new player to show
     */
    @Override
    public void notifyPlayerJoined(String username) {
        Platform.runLater(()->{
            try{
                CreateAndStartScene controller = fxmlLoader.getController();
                controller.addPlayer(username);
            }catch (Exception e){
                e.printStackTrace();
                controller.instantQuittingApplication();
            }
        });

    }

    @Override
    public void askStartGame() {
    }

    @Override
    public void waitStartGameString() {
    }

    /**
     * Sets the waiting room scene as a main stage for the lobby creator
     */
    @Override
    public void showCreatorWaitingRoom() {
        Platform.runLater(()-> {
            Parent root = null;
            try {
                fxmlLoader = new FXMLLoader(getClass().getResource("/FXMLFiles/createAndStart.fxml"));
                root = fxmlLoader.load();
                ((CreateAndStartScene) fxmlLoader.getController()).addPlayer(GUI.getInstance().getController().getUsername());
                primaryStage.setScene(new Scene(root));
                primaryStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    /** sets the waiting room scene as a main stage for all the non-creators who join the lobby
     * @param usernames the usernames' list of the players who are already on the lobby
     */
    @Override
    public void showWaitingRoom(ArrayList<String> usernames) {
        Platform.runLater(()->{

            Parent root = null;
            try {
                fxmlLoader = new FXMLLoader(getClass().getResource("/FXMLFiles/createAndStart.fxml"));
                root = fxmlLoader.load();
                CreateAndStartScene sceneCtrl = fxmlLoader.getController();
                sceneCtrl.addPlayers(usernames);
                sceneCtrl.setTitleLabel("Waiting for the Lobby Creator to Start the Game...");
                sceneCtrl.setButtonVisibility(false);
                primaryStage.setScene(new Scene(root));
                primaryStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }

        });
    }

    /**
     * method to call the re-showing game scene after a successful reconnection
     */
    @Override
    public void showReconnectionToGame() {
        Platform.runLater(()->{
            try {
                fxmlLoader = new FXMLLoader(getClass().getResource("/FXMLFiles/game.fxml"));
                gameRoot = fxmlLoader.load();
                gameGuiController = fxmlLoader.getController();
                gameGuiController.init(controller.getNumOfPlayerInLobby());
                primaryStage.setScene(new Scene(gameRoot));
                primaryStage.show();
                isGameBeenInitialized=true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        showSuccess("Reconnected to game");
    }

    /** It calls the lobbies list scene to show
     * @param lobbies the list of lobbies to load and show
     */
    @Override
    public void askLobbyID(ArrayList<Lobby> lobbies) {
        Parent root = null;
        try {
            fxmlLoader = new FXMLLoader(getClass().getResource("/FXMLFiles/joinAndWait.fxml"));
            root = fxmlLoader.load();
            JoinAndWaitScene j = fxmlLoader.getController();
            j.loadLobbies(lobbies);
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** it calls the showing of a player disconnection alert to all the other players
     * @param username the disconnected player username
     */
    @Override
    public void notifyPlayerDisconnected(String username) {
        showSuccess(username+" disconnected :(");
    }

    /** it calls the showing of a player reconnection alert to all the other players
     * @param username the reconnected player username
     */
    @Override
    public void notifyPlayerReconnected(String username) {
        showSuccess(username+" reconnected :)");
    }

    @Override
    public void notifyCreatorDisconnected() {}

    /** Sets and calls the showing of the ask start items scene
     * @param quartet the 4 leader cards the player has to chose between
     * @param numResources the number of resource the player can choose
     * @param faithPoints a boolean that establish if the player has a starting faith point
     */
    @Override
    public void askStartItems(ArrayList<LightLeaderCard> quartet, int numResources, boolean faithPoints) {
        Platform.runLater(()->{
            Parent root = null;
            try {
                fxmlLoader = new FXMLLoader(getClass().getResource("/FXMLFiles/chooseStartingThings.fxml"));
                root = fxmlLoader.load();
                ChooseStartingThingsScene j = fxmlLoader.getController();
                j.setMainTitleLabel("Choose 2 leader cards to keep");
                j.loadLeaderCards(quartet);
                j.showBonusFaithPoints(faithPoints);
                j.loadResources(numResources);
                primaryStage.setScene(new Scene(root));
                primaryStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * it sets the main game page and initialises it, giving the turn to the proper player
     */
    @Override
    public void askTurn() {
        Platform.runLater(()->{
            try {
                if(!isGameBeenInitialized){
                    fxmlLoader = new FXMLLoader(getClass().getResource("/FXMLFiles/game.fxml"));
                    gameRoot = fxmlLoader.load();
                    gameGuiController = fxmlLoader.getController();
                    gameGuiController.init(controller.getNumOfPlayerInLobby());
                    primaryStage.setScene(new Scene(gameRoot));
                    primaryStage.show();
                    isGameBeenInitialized=true;
                }
                gameGuiController.enableTurn();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * it sets the alert boxes to call and show for the players who are not playing their turn
     */
    @Override
    public void waitingForMyTurn() {
        Platform.runLater(()->{
            try {
                if (!isGameBeenInitialized) {
                    fxmlLoader = new FXMLLoader(getClass().getResource("/FXMLFiles/game.fxml"));
                    gameRoot = fxmlLoader.load();
                    gameGuiController = fxmlLoader.getController();
                    gameGuiController.init(controller.getNumOfPlayerInLobby());
                    isGameBeenInitialized = true;
                    primaryStage.setScene(new Scene(gameRoot));
                    primaryStage.show();
                }
                if (controller.getNumOfPlayerInLobby() != 1){
                    gameGuiController.disableTurn();
                    showSuccess("Others are playing, waiting for your turn starts");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void askLeaderCardActivation() {}

    @Override
    public void askLeaderCardThrowing() {}

    @Override
    public void askBuyResources() {}

    @Override
    public void askBuyDevCards() {}

    @Override
    public void askDevCardProduction() {}

    @Override
    public void askDefaultProduction() {}

    @Override
    public void askEndTurn() {}

    /** it sets and calls the showing of the resources thrown by a player after a market request
     * @param threwResources the resources that has benn thrown
     */
    @Override
    public void showThrewResources(ArrayList<LightResource> threwResources) {
        String s = "";
        for(LightResource r: threwResources){
            s+= r.name()+" ";
        }
        showError("Thrown resources: \n"+s);
    }

    /** it shows the error alert boxes with the given message
     * @param message the message to show
     */
    @Override
    public void showError(String message) {
        Platform.runLater(()->{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("An error occurred");
            alert.setContentText(message);
            alert.showAndWait();
        });
    }
    /** it shows the success alert boxes with the given message
     * @param message the message to show
     */
    @Override
    public void showSuccess(String message) {
        Platform.runLater(()->{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Successful action");
            alert.setContentText(message);
            alert.showAndWait();
        });
    }

    /** sets the alert box giving information about other players' actions
     * @param message the message to show
     */
    @Override
    public void showOthersActions(String message) {
        showSuccess(message);
    }

    /** sets the alert box giving information about lorenzo's action and makes an update of the faith track
     * @param lollo the reference to Lorenzo
     */
    @Override
    public void showLorenzoActions(LightLorenzo lollo) {
        Platform.runLater(()-> {
            showSuccess(lollo.plainActions(controller.getPlayerBoard().getFaithTrack()));
            gameGuiController.updateFaithTrack(getController().getUsername(), getController().getPlayerFull(getController().getUsername()).getPlayerBoard().getFaithTrack());
        });
    }

    /** sets and shows the alert box of the final ranking, then calls the instant quitting
     * @param winner the username of the winner
     * @param rank the final rank
     */
    @Override
    public void showRanking(String winner, String rank) {
        Platform.runLater(()->{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("ENDGAME");
            alert.setHeaderText("FINAL RANK:\n");
            alert.setContentText(rank + "\n" + "THE WINNER IS: " + winner);
            alert.showAndWait();
            controller.instantQuittingApplication();
        });
    }

    /** sets and shows the alert box of endgame, then quits the application
     * @param message the end game message to show
     */
    @Override
    public void endSinglePlayerGame(String message){
        Platform.runLater(()->{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("ENDGAME");
            alert.setHeaderText("Single Player has ended");
            alert.setContentText(message);
            alert.showAndWait();
            controller.instantQuittingApplication();
        });
    }

    /**
     * calls the controller to quit the application
     */
    @Override
    public void endGame() {
        Platform.runLater(()-> {
            controller.quittingApplication();
        });
    }

    /** sends a request to the game scene controller of updating the card graphics
     * @param username the username who made the change
     * @param cardSlots the reference to the user card slots
     */
    @Override
    public void updateCardSlots(String username, LightCardSlots cardSlots) {
        Platform.runLater(()->{
            gameGuiController.updateCardSlots(username, cardSlots.getCards());
        });
    }
    /** sends a request to the game scene controller of updating the strongbox
     * @param username the username who made the change
     * @param strongbox the reference to the user strongbox
     */
    @Override
    public void updateStrongbox(String username, LightStrongbox strongbox) {
        Platform.runLater(()->{
            gameGuiController.updateStrongBox(username, strongbox);
        });
    }
    /** sends a request to the game scene controller of updating the development board
     * @param board the reference to the game development board
     */
    @Override
    public void updateDevBoard(LightDevelopmentBoard board) {
        Platform.runLater(()-> {
            gameGuiController.updateDevBoard(board);
        });
    }
    /** sends a request to the game scene controller of updating the market board
     * @param market the reference to the game development board
     */
    @Override
    public void updateMarketBoard(LightMarketBoard market){
        Platform.runLater(()-> {
            gameGuiController.updateMarketBoard(market);
        });
    }
    /** sends a request to the game scene controller of updating the warehouse
     * @param username the username who made the change
     * @param warehouseDepot the reference to the user depot
     */
    @Override
    public void updateWarehouseDepot(String username, LightWarehouseDepot warehouseDepot) {
        Platform.runLater(()-> {
            gameGuiController.updateWarehouseDepot(username, warehouseDepot);
        });
    }
    /** sends a request to the game scene controller of updating the faith track
     * @param username the username who made the change
     * @param faithTrack the reference to the user faith track
     */
    @Override
    public void updateFaithTrack(String username, LightFaithTrack faithTrack) {
        Platform.runLater(()->{
            gameGuiController.updateFaithTrack(username, faithTrack);
        });
    }
    /** sends a request to the game scene controller of updating the leader slots
     * @param username the username who made the change
     * @param leaderSlot the reference to the user leader slots
     */
    @Override
    public void updateLeaderSlot(String username, ArrayList<LightLeaderCard> leaderSlot) {
        Platform.runLater(()->{
            gameGuiController.updateLeaderCards(username, leaderSlot);
        });
    }
}
