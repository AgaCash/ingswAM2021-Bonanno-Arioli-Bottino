package clientView;

import clientController.LightController;
import clientModel.cards.LightLeaderCard;
import clientModel.colour.LightColour;
import clientModel.resources.LightResource;
import network.server.Lobby;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class CLI implements View{
    private Scanner in;
    private final PrintStream out;
    LightController controller;

    public CLI(){
        this.in = new Scanner(System.in);
        this.out = System.out;
        this.controller = new LightController(this);
    }

    //ping
    public void serverLostConnection(){
        System.out.println(LightColour.RED+"LOST SERVER CONNECTION"+LightColour.WHITE);
        controller.quittingApplication();
    }

    public void askServerInfo(){
        out.println("IP [127.0.0.1]: ");
        String ip = in.nextLine();
        if(ip.isBlank())
            ip = "127.0.0.1";
        out.println("Port [1234]: ");
        String portS = in.nextLine();
        if(portS.isBlank())
            portS = "1234";
        int port = Integer.parseInt(portS);
        controller.connectToServer(ip, port);
    }

    public void askUsername(){
        String username;
        do {
            username = askString("Username: ");
        }while(username.isBlank());
        controller.setUsername(username);
    }

    public void askMenu() {
        int choice;
        do {
            choice = askInt("CHOOSE MODALITY: \n1- Single player \n2- Join Multiplayer Lobby" +
                    " \n3- Create Multiplayer Lobby");
        }while(choice<1 || choice>3);
            switch (choice) {
                case 1 -> handleSinglePlayer();
                case 2 -> handleMultiJoin();
                case 3 -> handleMultiCreate();
            }
    }

    private void handleSinglePlayer(){
        controller.createSinglePlayerLobby();
    }

    private void handleMultiJoin(){
        controller.getLobbyList();
    }

    private void handleMultiCreate(){
        controller.createMultiLobby();
    }

    public void notifyLobbyCreated(){
        showSuccess("Lobby created\nWaiting for players to join...");
    }

    public void notifyPlayerJoined(String username){
        System.out.println(username +" has joined the lobby");
    }

    public void askStartGame(){
        waitStartGameString();
    }

    public void waitStartGameString(){
        String s;
        do{
            s = askString("Write \"start\" to begin the game");
            s = s.toLowerCase();
        }while (!s.equals("start"));
        controller.sendSignalMultiPlayerGame();
    }

    public void showWaitingRoom(){
        System.out.println("You joined the room");
        System.out.println("Waiting for creator of the room starts the game...");
    }

    public void showReconnectionToGame(){
        System.out.println("Reconnecting to game...");
    }

    public void waitingForMyTurn(){
        System.out.println("Others are playing, waiting for your turn starts");
    }

    @Override
    public void askLobbyID(ArrayList<Lobby> lobbies) {
        if(!lobbies.isEmpty()) {
            System.out.println("\tID\tPlayers");
            lobbies.forEach((lobby -> System.out.println("\t" + lobby.getId() + "\t" + lobby.getUsernameList())));
            int numLobby = askInt("Choose the lobby you want to join by ID:");
            controller.joinLobbyById(numLobby);
        }else{
            System.out.println("No lobby found");
            controller.askLobbyMenu();
        }
    }

    @Override
    public void notifyPlayerDisconnected(String username) {
        System.out.println(LightColour.RED+username + " disconnected"+LightColour.WHITE);
    }

    @Override
    public void notifyPlayerReconnected(String username) {
        System.out.println(LightColour.GREEN+username + " reconnected"+LightColour.WHITE);
    }

    @Override
    public void notifyCreatorDisconnected() {
        System.out.println("The lobby creator has left the lobby");
    }


    @Override
    public void askTurn() {
        // System.out.println("IT'S YOUR TURN!\n");
        int ans ;
        do {
            printMenu();
            ans = askInt("CHOOSE YOUR ACTION\n\b" +
                    "1 to do a leader action\n\b" +
                    "2 to activate a production\n\b" +
                    "3 to buy resources at the market\n\b" +
                    "4 to buy a development card\n\b" +
                    "5 to show the game status\n\b" +
                    "6 to end your turn\n\n");
        } while ((ans < 1 || ans > 6) && ans!=53550 );
        switch (ans) {
            case 1 -> askLeader();
            case 2 -> askProduction();
            case 3 -> askBuyResources();
            case 4 -> askBuyDevCards();
            case 5 -> askShow();
            case 6 -> askEndTurn();
            case 53550 -> cheat();
        }
    }

    private void printMenu(){
        System.out.println(controller.getPlayerBoard().getFaithTrack().toString());
        System.out.println(controller.getPlayerBoard().getWarehouseDepot().toString());
        System.out.println(controller.getPlayerBoard().getStrongbox().toString());
        System.out.println(controller.getPlayerBoard().getCardSlots().toString());
    }

    private void askShow(){
        int ans;
        do ans = askInt("1 to show the development board\n\b" +
                    "2 to show the market board\n\b" +
                    "3 to turn back to the main menu\n\b");
        while (ans < 1 || ans > 3);
        switch (ans) {
            case 1 : System.out.println(controller.getDevBoard().toString()); break;
            case 2 : System.out.println(controller.getMarketBoard().toString()); break;
            case 3 : break;
        }
        askTurn();
    }

    private void askLeader(){
        int ans ;
        do ans = askInt("CHOOSE YOUR ACTION\n\b" +
                    "1 to activate a leader card\n\b" +
                    "2 to throw a leader card\n\b" +
                    "3 to turn back to the main menu");
        while (ans < 1 || ans > 3);
        switch (ans) {
            case 1 : askLeaderCardActivation(); break;
            case 2 : askLeaderCardThrowing(); break;
            case 3 : break;
        }
        askTurn();
    }

    private void askProduction(){
        int ans;
        do ans = askInt("1 to activate the production from a development card\n" +
                    "2 to activate the production from the player board\n" +
                    "3 to turn back to the menu\n");
        while(ans <1 || ans > 3);
        switch (ans) {
            case 1 : askDevCardProduction(); break;
            case 2 : askDefaultProduction(); break;
            case 3 : break;
        }
        askTurn();

    }

    @Override
    public void askLeaderCardActivation() {
        ArrayList<LightLeaderCard> couple = controller.getPlayerBoard().getLeaderSlot();
        int ans ;
        for(LightLeaderCard card: couple)
            System.out.println("\n\b"+ (couple.indexOf(card)+1)+" for "+card);
        do ans = askInt("choose the card\nor press 0 to abort");
        while(ans!= 1 && ans != 2 && ans!=0);
        if(ans!=0)
            controller.sendLeaderCardActivationRequest(couple.get(ans-1));

    }

    @Override
    public void askLeaderCardThrowing() {
        ArrayList<LightLeaderCard> couple = controller.getPlayerBoard().getLeaderSlot();
        int ans;
        for(LightLeaderCard card: couple)
            System.out.println("\n\b"+ (couple.indexOf(card)+1)+" for "+card);
        do ans = askInt("choose the card:\nor press 0 to abort");
        while(ans!= 1 && ans != 2 && ans!=0);
        if(ans!=0)
            controller.sendLeaderCardThrowRequest(couple.get(ans-1));
    }

    @Override
    public void askBuyResources() {
        System.out.println(controller.getMarketBoard().toString());
        int choice;
        do choice = askInt("1 to choose column\n2 to choose row\n3 to abort\n");
        while(choice!= 1 && choice != 2 && choice!=3);
        if(choice != 3) {
            boolean line = choice == 2;
            int number;
            if (line)
                do number = askInt("insert your chosen row number");
                while (number < 1 || number > 3);
            else
                do number = askInt("insert your chosen column number");
                while (number < 1 || number > 4);
            String ans;
            do ans = askString("want to add a leader card?\n[y/n]\n");
            while (!ans.equals("y") && !ans.equals("n"));
            if (ans.equals("y")) {
                LightLeaderCard card = askLeaderCardUse();
                if (card != null && card.isWhiteConverter())
                    controller.sendBuyResourceRequest(line, number - 1, card);
            } else
                controller.sendBuyResourceRequest(line, number - 1, null);
            showSuccess("successful purchase! your new warehouse: ");
            System.out.println(controller.getPlayerBoard().getWarehouseDepot().toString());
        }
        askTurn();
    }

    @Override
    public void askBuyDevCards() {
        System.out.println(controller.getDevBoard().toString());
        System.out.println(controller.getPlayerBoard().getCardSlots().toString());
        int deck;
        do deck=askInt("digit a deck number\nor 0 to turn back to the main menu");
        while(deck<0 || deck>12);
        if(deck != 0) {
            int slot;
            do slot = askInt("choose the slot where add the development card");
            while (slot < 1 || slot > 3);
            String ans;
            do ans = askString("want to add a leader card?\n [y/n]\n");
            while (!ans.equals("y") && !ans.equals("n"));
            if (ans.equals("y")) {
                LightLeaderCard card = askLeaderCardUse();
                controller.sendBuyDevCardRequest(deck - 1, slot - 1, card);
            }else{
                controller.sendBuyDevCardRequest(deck - 1, slot - 1, null);
            }
            showSuccess("successful purchase! your new development card slots: ");
            System.out.println(controller.getPlayerBoard().getCardSlots().toString());
        }
        askTurn();
    }

    @Override
    public void askDevCardProduction() {
        System.out.println(controller.getPlayerBoard().getCardSlots().toString());
        int slot;
        int numSlots = controller.getPlayerBoard().getCardSlots().getSize();
        if(numSlots == 0)
            showError("no slot available");
        else {
            do slot = askInt("choose a slot");
            while (slot < 1 || slot > numSlots);
            try {
                if (controller.getPlayerBoard().getCardSlots().getCard(slot-1) != null){
                    String ans;
                    do ans = askString("want to add a leader card?\n [y/n]\n");
                    while(!ans.equals("y") && !ans.equals("n"));
                    if(ans.equals("y")) {
                        LightLeaderCard card = askLeaderCardUse();
                        LightResource chosenResource = askResource();
                        controller.sendDevCardProductionRequest(slot-1, chosenResource, card);
                    }
                    else{
                        controller.sendDevCardProductionRequest(slot-1, null, null);
                    }
                    showSuccess("successful production! your new resources: ");
                    System.out.println(controller.getPlayerBoard().getStrongbox().toString());
                    System.out.println(controller.getPlayerBoard().getWarehouseDepot().toString());
                }
            } catch (NullPointerException | IndexOutOfBoundsException e) {
                showError("empty slot!");
            }
        }
    }

    @Override
    public void askDefaultProduction() {
        System.out.println(controller.getPlayerBoard().getStrongbox().toString());
        System.out.println(controller.getPlayerBoard().getWarehouseDepot().toString());
        ArrayList<LightResource> chosenInput = new ArrayList<>();
        LightResource outRes;
        System.out.println("choose the 2 input resources: ");
        chosenInput.add(askResource());
        chosenInput.add(askResource());
        System.out.println("choose the output resource: ");
        outRes = askResource();
        String ans;
        do ans = askString("want to add a leader card?\n [y/n]");
        while(!ans.equals("y") && !ans.equals("n"));
        if(ans.equals("y")) {
            LightLeaderCard card = askLeaderCardUse();
            LightResource chosenResource = askResource();
            controller.sendDefaultProductionRequest(chosenInput, outRes, card, chosenResource);
        }
        else{
            controller.sendDefaultProductionRequest(chosenInput, outRes, null, null);
        }
        showSuccess("successful production! your new resources: ");
        System.out.println(controller.getPlayerBoard().getStrongbox().toString());
        System.out.println(controller.getPlayerBoard().getWarehouseDepot().toString());
    }

    @Override
    public void askEndTurn() {
        System.out.println("turn ended!\nwait...");
        controller.sendEndTurnRequest();
    }

    @Override
    public void showThrewResources(ArrayList<LightResource> threwResources) {
        if(!threwResources.isEmpty()) {
            System.out.println(LightColour.RED+"resources: ");
            System.out.println(threwResources);
            System.out.println("were not added to the warehouse: insufficient space!"+LightColour.WHITE);
        }
    }

    @Override
    public void showError(String message) {
        System.out.println(LightColour.RED+"ERROR: "+message+LightColour.WHITE);
    }

    @Override
    public void showSuccess(String message) {
        System.out.println(LightColour.GREEN+message+LightColour.WHITE);
    }

    @Override
    public void askStartItems(ArrayList<LightLeaderCard> quartet, int numResources, boolean faithPoints) {
        for(LightLeaderCard card: quartet)
            System.out.println(card.toString());
        System.out.println("you have " + numResources +
                " resources to choose");
        if (faithPoints)
            System.out.println("you have earned 1 faith point");
        ArrayList<LightLeaderCard> couple = new ArrayList<>();
        int first, second;
        do{
            System.out.println("insert two number from 1 to 4 to choose 2 leader cards from the quartet");
            first = askInt("first: ") - 1;
            second = askInt("second: ") - 1;
         }
        while((first<0 || first>3) && (second<0 || second>3));
        couple.add(quartet.get(first));
        couple.add(quartet.get(second));
        ArrayList<LightResource> chosenResources = new ArrayList<>();
        for(int i=0; i<numResources; i++)
            chosenResources.add(askResource());
        controller.sendStartItems(couple, chosenResources, faithPoints);
    }

    private LightLeaderCard askLeaderCardUse(){
        ArrayList<LightLeaderCard> cards = controller.getPlayerBoard().getLeaderSlot();
        int choice;
        do{
            System.out.println(cards);
            choice = askInt("choose an active leader card: ");
        }while(choice != 1 && choice!=2);

        LightLeaderCard card = cards.get(choice-1);
        if(!card.isEnabled()) {
            showError("Leader card not active!");
            card = null;
        }
        return card;
    }

    @Override
    public void showRanking(String winner, String rank){
        System.out.println(LightColour.YELLOW+"FINAL RANK:\n"+rank+LightColour.WHITE);
        System.out.println(LightColour.GREEN+"AND THE WINNER IS...\n\t"+winner+"!"+LightColour.WHITE);
    }

    @Override
    public void endGame(){
        System.out.println("see you space cowboy...\n");
        controller.quittingApplication();
    }

    private void cheat(){
        System.out.println("BURLONEEEE");
        int ans;
        do ans = askInt("choose 1 if you're a fucking liar, 2 for the strength of the Pope");
        while(ans!=1 && ans!=2);
        controller.sendCheat(ans);
        askTurn();
    }

    private String askString(String message){
        System.out.println(message);
        try{
            return in.nextLine();
        }catch(InputMismatchException e){
            showError("Input incorrect!");
            this.in = new Scanner(System.in);
            return "";
        }
    }

    private Integer askInt(String message){
        System.out.println(message);
        try{
            return in.nextInt();
        }catch(InputMismatchException e){
            showError("Input incorrect!");
            this.in = new Scanner(System.in);
            return 0;
        }
    }

    private LightResource askResource(){
        int res;
        do res = askInt("choose resource:\n" +
                    "1 for COIN\n2 for SERVANT\n3 for SHIELD\n4 for STONE");
        while(res<1 || res>4);
        return switch (res) {
            case 1 -> LightResource.COIN;
            case 2 -> LightResource.SERVANT;
            case 3 -> LightResource.SHIELD;
            case 4 -> LightResource.STONE;
            default -> null;
        };
    }


}
