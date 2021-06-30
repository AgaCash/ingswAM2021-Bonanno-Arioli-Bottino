package GUI;

import GUI.scenes.*;
import clientController.LightController;
import clientModel.cards.LightCardSlots;
import clientModel.cards.LightLeaderCard;
import clientModel.player.LightPlayer;
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
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.util.Duration;
import network.server.Lobby;

import java.io.IOException;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Executable;
import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class GUI implements View {
    private LightController controller;
    private Stage primaryStage;
    private FXMLLoader fxmlLoader;
    private boolean isGameBeenInitialized;
    private GameScene gameGuiController;
    private Parent gameRoot;

    private static GUI instance = null;

    public static GUI getInstance() {
        if(instance == null)
            instance = new GUI();
        return instance;
    }

    private GUI(){
        isGameBeenInitialized = false;
        this.controller = new LightController(this);
    }

    public void setStage(Stage stage) {
        this.primaryStage = stage;
    }

    /*
    SAMPLE

        Parent root = null;
        try {
            fxmlLoader = new FXMLLoader(getClass().getResource("/FXMLFiles/.fxml"));
            root = fxmlLoader.load();
            //fxmlLoader.getController();
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

     */

    public LightController getController() {
        return controller;
    }

    @Override
    public void askServerInfo() {
        Platform.runLater(()->{
            Parent root = null;
            try {
                fxmlLoader = new FXMLLoader(getClass().getResource("/FXMLFiles/serverSetup.fxml"));
                root = fxmlLoader.load();
                //fxmlLoader.getController();
                primaryStage.setScene(new Scene(root));
                primaryStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

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

    @Override
    public void askMenu() {
        Platform.runLater(()->{
            Parent root = null;
            try {
                fxmlLoader = new FXMLLoader(getClass().getResource("/FXMLFiles/menuSelection.fxml"));
                root = fxmlLoader.load();
                //fxmlLoader.getController();
                primaryStage.setScene(new Scene(root));
                primaryStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }


    @Override
    public void notifyPlayerJoined(String username) {
        CreateAndStartScene controller = fxmlLoader.getController();
        Platform.runLater(()->{
            controller.addPlayer(username);
        });

    }

    @Override
    public void askStartGame() {
        //non serve in Gui
        //si può togliere da view
    }

    @Override
    public void waitStartGameString() {
        //non serve in Gui
        //si può togliere da view
    }

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

    @Override
    public void showReconnectionToGame() {
        Platform.runLater(()->{
            //ricarica la pagina del gioco
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

    @Override
    public void askLobbyID(ArrayList<Lobby> lobbies) {
        //mostro la finestra con le lobby
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

    @Override
    public void notifyPlayerDisconnected(String username) {
        showSuccess(username+" disconnected :(");
    }

    @Override
    public void notifyPlayerReconnected(String username) {
        showSuccess(username+" reconnected :)");
    }

    @Override
    public void notifyCreatorDisconnected() {

    }

    @Override
    public void askStartItems(ArrayList<LightLeaderCard> quartet, int numResources, boolean faithPoints) {
        //todo sostiturire con Task per evitare il blocco dell'app
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

    @Override
    public void askTurn() {
        Platform.runLater(()->{
            //carico la main page e la inizializzo
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
    public void askLeaderCardActivation() {
        //non serve in Gui
        //si può togliere da view
    }

    @Override
    public void askLeaderCardThrowing() {
        //non serve in Gui
        //si può togliere da view
    }

    @Override
    public void askBuyResources() {
        //non serve in Gui
        //si può togliere da view
    }

    @Override
    public void askBuyDevCards() {
        //non serve in Gui
        //si può togliere da view
    }

    @Override
    public void askDevCardProduction() {
        //non serve in Gui
        //si può togliere da view
    }

    @Override
    public void askDefaultProduction() {
        //non serve in Gui
        //si può togliere da view
    }

    @Override
    public void askEndTurn() {
        //non serve in Gui
        //si può togliere da view
    }

    @Override
    public void showThrewResources(ArrayList<LightResource> threwResources) {
        String s = "";
        for(LightResource r: threwResources){
            s+= r.name()+" ";
        }
        showError("Thrown resources: \n"+s);
    }

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

    @Override
    public void showOthersActions(String message) {
        showSuccess(message);
    }

    @Override
    public void showLorenzoActions(String message) {
        showSuccess(message);
    }

    @Override
    public void showRanking(String winner, String rank) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("ENDGAME");
        alert.setHeaderText("FINAL RANK:\n");
        alert.setContentText(rank +"\n" + "THE WINNER IS: " + winner);
        alert.showAndWait();
    }

    @Override
    public void endGame() {
        controller.quittingApplication();
    }

    @Override
    public void updateCardSlots(String username, LightCardSlots cardSlots) {
        Platform.runLater(()->{
            gameGuiController.updateCardSlots(username, cardSlots.getCards());
        });
    }

    @Override
    public void updateStrongbox(String username, LightStrongbox strongbox) {
        Platform.runLater(()->{
            gameGuiController.updateStrongBox(username, strongbox);
        });
    }

    @Override
    public void updateDevBoard(LightDevelopmentBoard board) {
        Platform.runLater(()-> {
            gameGuiController.updateDevBoard(board);
        });
    }

    @Override
    public void updateMarketBoard(LightMarketBoard market){
        Platform.runLater(()-> {
            gameGuiController.updateMarketBoard(market);
        });
    }

    @Override
    public void updateWarehouseDepot(String username, LightWarehouseDepot warehouseDepot) {
        Platform.runLater(()-> {
            gameGuiController.updateWarehouseDepot(username, warehouseDepot);
        });
    }

    @Override
    public void updateFaithTrack(String username, LightFaithTrack faithTrack) {
        Platform.runLater(()->{
            gameGuiController.updateFaithTrack(username, faithTrack);
        });
    }

    @Override
    public void updateLeaderSlot(String username, ArrayList<LightLeaderCard> leaderSlot) {

    }
}
