package clientView;

import clientController.LightController;
import model.resources.Resource;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

public class CLI implements View{
    private Scanner in;
    private PrintStream out;
    LightController controller;

    public CLI(){
        this.in = new Scanner(System.in);
        this.out = System.out;
        this.controller = new LightController();
    }

    public void askServerInfo(){
        out.println("IP: ");
        String ip = in.nextLine();
        out.println("Port: ");
        int port = in.nextInt();
        //controller.connectToServer(ip, port);
        askUsername();
    }

    public void askUsername(){
        out.println("Username: ");
        String username = in.nextLine();
        //controller.setUsername(username);
        askMenu();
    }

    @Override
    public void askLobbyID() {

    }

    @Override
    public void askNewLobbyName() {

    }

    @Override
    public void askStartup() {

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
    public void showThrewResources(ArrayList<Resource> threwResources) {

    }

    @Override
    public void showError(String message) {

    }

    @Override
    public void showSuccess(String message) {

    }

    public void askMenu() {
        boolean stop = false;
        while (!stop) {
            out.println("Scegli cosa vuoi fare \n1- Singleplayer \n2- Join Multiplayer Lobby" +
                    " \n3- Create Multiplayer Lobby");
            int choice = in.nextInt();
            switch (choice) {
                case 1: {
                    handleSinglePlayer();
                    stop = true;
                    break;
                }
                case 2: {
                    handleMultiJoin();
                    stop = true;
                    break;
                }
                case 3: {
                    handleMultiCreate();
                    stop = true;
                    break;
                }
                default: {
                    out.println("CaggFa? invalid choice ");
                }
            }
        }
    }

    public void handleSinglePlayer(){

    }

    public void handleMultiJoin(){

    }

    public void handleMultiCreate(){
        //controller.createLobby();
                    /* public void createLobby(){
                    *           manda un msg
                    *           riceve msg
                    *           view.showError
                    *              view.waitingLobby*/
    }

    public void notifyJoin(){

    }
}
