package GUI;

import GUI.scenes.ServerSetupScene;
import GUI.scenes.UsernameScene;
import clientController.LightController;
import clientModel.cards.LightLeaderCard;
import clientModel.resources.LightResource;
import clientView.View;
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

    public void sendServerInfo(String addr, int port){
        controller.connectToServer(addr, port);
    }
    public void sendUsername(String username){
        controller.setUsername(username);
    }


    @Override
    public void askServerInfo() {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/FXMLFiles/serverSetup.fxml"));
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void askUsername() {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/FXMLFiles/login.fxml"));
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void serverLostConnection() {

    }

    @Override
    public void askMenu() {

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

    }

    @Override
    public void askStartGame() {

    }

    @Override
    public void waitStartGameString() {

    }

    @Override
    public void showWaitingRoom() {

    }

    @Override
    public void showReconnectionToGame() {

    }

    @Override
    public void waitingForMyTurn() {

    }

    @Override
    public void askLobbyID(ArrayList<Lobby> lobbies) {

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

    }

    @Override
    public void askTurn() {

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
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("An error occurred");
        alert.setContentText(message);
        alert.showAndWait();
    }

    @Override
    public void showSuccess(String message) {

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
}
