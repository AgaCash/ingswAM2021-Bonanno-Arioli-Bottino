package GUI;

import GUI.scenes.ServerSetupScene;
import clientController.LightController;
import clientModel.cards.LightLeaderCard;
import clientModel.resources.LightResource;
import clientView.View;
import network.server.Lobby;

import java.util.ArrayList;

public class GUI implements View {
    LightController controller;
    ServerSetupScene serverSetupScene;

    public GUI(){
        this.controller = new LightController(this);
    }
    @Override
    public void serverLostConnection() {

    }

    @Override
    public void askServerInfo() {
    }

    @Override
    public void askUsername() {

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
