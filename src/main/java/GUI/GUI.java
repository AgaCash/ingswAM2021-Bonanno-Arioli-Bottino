package GUI;

import GUI.scenes.*;
import clientController.LightController;
import clientModel.cards.LightCardSlots;
import clientModel.cards.LightLeaderCard;
import clientModel.resources.LightResource;
import clientModel.strongbox.LightStrongbox;
import clientModel.table.LightFaithTrack;
import clientModel.warehouse.LightWarehouseDepot;
import clientView.View;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import network.server.Lobby;

import java.io.IOException;
import java.util.ArrayList;

public class GUI implements View {
    LightController controller;
    Stage primaryStage;
    FXMLLoader fxmlLoader;

    private static GUI instance = null;

    public static GUI getInstance() {
        if(instance == null)
            instance = new GUI();
        return instance;
    }

    private GUI(){
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
    public void handleSinglePlayer() {

    }

    @Override
    public void handleMultiJoin() {

    }

    @Override
    public void handleMultiCreate() {

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

    }

    @Override
    public void waitStartGameString() {

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

    }

    @Override
    public void waitingForMyTurn() {

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

    }

    @Override
    public void notifyPlayerReconnected(String username) {

    }

    @Override
    public void notifyCreatorDisconnected() {

    }

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

    @Override
    public void askTurn() {
        System.out.println("CI SIAMOOOOOOOOOOOOOOOOOOOO DIOCANEEEEEEEEEEEEEEEEEEEEEEEEEEEee");
    }

    @Override
    public void askLeaderCardActivation() {

    }

    @Override
    public void askLeaderCardThrowing() {

    }

    @Override
    public void askBuyResources() {

    }

    @Override
    public void askBuyDevCards() {

    }

    @Override
    public void askDevCardProduction() {

    }

    @Override
    public void askDefaultProduction() {

    }

    @Override
    public void askEndTurn() {

    }

    @Override
    public void showThrewResources(ArrayList<LightResource> threwResources) {

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
    public void showRanking(String winner, String rank) {

    }

    @Override
    public void endGame() {

    }

    @Override
    public void showOthersActions(String message) {

    }

    @Override
    public void showLorenzoActions(String message) {

    }

    @Override
    public void updateCardSlots(String username, LightCardSlots cardSlots) {

    }

    @Override
    public void updateWarehouseDepot(String username, LightWarehouseDepot warehouseDepot) {

    }

    @Override
    public void updateStrongbox(String username, LightStrongbox strongbox) {

    }

    @Override
    public void updateFaithTrack(String username, LightFaithTrack faithTrack) {

    }

    @Override
    public void updateLeaderSlot(String username, ArrayList<LightLeaderCard> leaderSlot) {

    }
}
