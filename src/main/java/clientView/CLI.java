package clientView;

import clientController.LightController;
import exceptions.MessageNotSuccededException;
import model.resources.Resource;

import java.io.IOException;
import java.io.PrintStream;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;

public class CLI implements View{
    private Scanner in;
    private PrintStream out;
    LightController controller;

    public CLI(){
        this.in = new Scanner(System.in);
        this.out = System.out;
        this.controller = new LightController(this);
    }

    public void askServerInfo(){

        boolean connectionCorrect = false;
        do{
            out.println("IP [127.0.0.1]: ");
            String ip = in.nextLine();
            if(ip.isBlank())
                ip = "127.0.0.1";
            out.println("Port [1234]: ");
            String portS = in.nextLine();
            if(portS.isBlank())
                portS = "1234";
            int port = Integer.parseInt(portS);
            try {
                controller.connectToServer(ip, port);
                connectionCorrect = true;
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }while (connectionCorrect=false);
        askUsername();
    }

    public void askUsername(){
        String username;
        do{
            out.println("Username: ");
            username = in.nextLine();
            try {
                controller.setUsername(username);
            } catch (MessageNotSuccededException e) {
                System.out.println(e.getMessage());
                username = null;
            }
        }while (username == null);
        //askMenu();
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
                    out.println("CaggiaFà? invalid choice ");
                }
            }
        }
    }

    private void handleSinglePlayer(){
        controller.createSinglePlayerLobby();
    }

    public void switchToGame(boolean singlePlayer){
        if(singlePlayer){
            System.out.println("STAI GIOCANDO DA SOLO\nChe grande!");
        }else{
            System.out.println("Uoo stai giocando con altra gente\nTrooooppo frizzante!!");
        }
    }

    private void handleMultiJoin(){

    }

    private void handleMultiCreate(){
        controller.createMultiLobby();
        /* public void createLobby(){
         *           manda un msg
         *           riceve msg
         *           view.showError
         *              view.waitingLobby*/
    }

    public void notifyLobbyCreated(){
        System.out.println("Lobby created\nWaiting for players to join...");
    }

    public void notifyPlayerJoined(String username){
        System.out.println(username +" has joined the lobby");
    }

    public void showWaitingRoom(){
        System.out.println("You joined the room");
        System.out.println("Waiting for creator of the room starts the game...");
    }

    @Override
    public void askLobbyID() {

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
        System.out.println("ERROR: "+message);
    }

    @Override
    public void showSuccess(String message) {

    }

    public void notifyJoin(){

    }
}
