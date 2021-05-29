package clientView;

import clientController.LightController;
import clientModel.cards.LightExtraProd;
import clientModel.cards.LightLeaderCard;
import clientModel.resources.LightResource;
import exceptions.MessageNotSuccededException;
import model.resources.Resource;
import network.server.Lobby;

import java.io.IOException;
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
        }while (!connectionCorrect);
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
    }

    public void askMenu() {
        boolean stop = false;
        while (!stop) {
            out.println("Scegli cosa vuoi fare \n1- Singleplayer \n2- Join Multiplayer Lobby" +
                    " \n3- Create Multiplayer Lobby");
            int choice = in.nextInt();
            switch (choice) {
                case 1 -> {
                    handleSinglePlayer();
                    stop = true;
                    break;
                }
                case 2 -> {
                    handleMultiJoin();
                    stop = true;
                    break;
                }
                case 3 -> {
                    handleMultiCreate();
                    stop = true;
                    break;
                }
                default -> {
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
        controller.getLobbyList();
    }

    private void handleMultiCreate(){
        controller.createMultiLobby();
    }

    public void notifyLobbyCreated(){
        System.out.println("Lobby created\nWaiting for players to join...");
    }

    public void notifyPlayerJoined(String username){
        System.out.println(username +" has joined the lobby");
    }

    public void askStartGame(){
        System.out.println("Write \"start\" to begin the game");
        waitStartGameString();
    }

    //TODO:
    //  BUG:
    //      se scrivo start quando non ci sono giocatori dopo che ne entra uno al creatore inizia subito la partita
    public void waitStartGameString(){
        String s = null;
        do{
            s = in.nextLine().toLowerCase();
        }while (!s.equals("start"));
        controller.sendSignalMultiPlayerGame();
    }

    public void notifyCreatorPlayerJoined(){
        boolean success = false;
        String ch = null;
        do{
            System.out.println("Start game? (y/n)");
            do {
                 ch = in.nextLine(); //temporary solution, alla buona
            }while (ch.isEmpty());

            switch (ch.toLowerCase()){
                case "y":
                    success = true;
                    controller.sendSignalMultiPlayerGame();
                    break;
                case "n":
                    success = true;
                    break;
            }
        }while (!success);

    }


    public void showWaitingRoom(){
        System.out.println("You joined the room");
        System.out.println("Waiting for creator of the room starts the game...");
    }

    @Override
    public void askLobbyID(ArrayList<Lobby> lobbies) {
        if(!lobbies.isEmpty()) {
            System.out.println("\tID\tPlayers");
            lobbies.forEach((lobby -> {
                System.out.println("\t" + lobby.getId() + "\t" + lobby.getUsernameList());
            }));
            System.out.println("Choose the lobby you want to join by ID:");
            int numLobby = in.nextInt();
            controller.joinLobbyById(numLobby);
        }else{
            System.out.println("No lobby found");
            controller.askLobbyMenu();
        }
    }

    @Override
    public void askStartup() {

    }

    @Override
    public void askTurn() {
        System.out.println("PARTIAMOOOOOO");
        int ans = 0;
        do {
            System.out.println("scegli un azione\n\b" +
                    "1 per attivare la carta leader\n\b" +
                    "2 per attivare la produzione\n\b" +
                    "3 per comprare le risorse\n\b" +
                    "4 per comprare la carta leader");
            ans = in.nextInt();
        }while(ans<1 || ans>4);
        switch(ans){
            case 1 -> askLeaderCardActivation();
            case 2 -> askProduction();
            case 3 -> askBuyResources();
            case 4 -> askBuyDevCards();
        }

    }

    private void askProduction(){
        System.out.println("da fare ");
    }

    @Override
    public void askLeaderCardActivation() {
        System.out.println(controller.getPlayer());
        System.out.println(controller.getPlayerBoard());
        ArrayList<LightLeaderCard> couple = controller.getPlayerBoard().getLeaderSlot();
        couple.forEach(System.out::println);
        int ans = 0;
        do {
            System.out.println("scegli la carta:");
            for(LightLeaderCard card: couple){
                System.out.println("\n\b"+ (couple.indexOf(card)+1)+" per "+card);
            }
            ans = in.nextInt();
        } while(ans!= 1 && ans != 2);
       controller.sendLeaderCardActivationRequest(couple.get(ans-1));

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
        System.out.println(message);

    }
    @Override
    public void askStartItems(ArrayList<LightLeaderCard> quartet, int numResources, boolean faithPoints) {
        quartet.forEach(System.out::println);
        System.out.println("you have " + numResources +
                " resources to choose");
        if (faithPoints)
            System.out.println("you have earned 1 faith point");
        ArrayList<LightLeaderCard> couple = new ArrayList<>();
        int first, second;
        do{
            System.out.println("insert two number from 1 to 4 to choose 2 leader cards mammt hai capito");
            first = in.nextInt() - 1;
            second = in.nextInt() - 1;
         }
        while((first<0 || first>3) && (second<0 || second>3));
        couple.add(quartet.get(first));
        couple.add(quartet.get(second));
        ArrayList<LightResource> chosenResources = new ArrayList<>();
        for(int i=0; i<numResources; i++){
            int res;
            LightResource choice = null;
            do {
                System.out.println("choose resource " +
                        "1 for COIN\n2 for SERVANT\n3 for SHIELD\n4 for STONE");
                res = in.nextInt();
            }
            while(res<1 || res>4);
            switch (res) {
                case 1 -> choice = LightResource.COIN;
                case 2 -> choice = LightResource.SERVANT;
                case 3 -> choice = LightResource.SHIELD;
                case 4 -> choice = LightResource.STONE;
            }
            chosenResources.add(choice);
        }
        controller.sendStartItems(couple, chosenResources, faithPoints);

    }

    public void notifyJoin(){

    }
}
