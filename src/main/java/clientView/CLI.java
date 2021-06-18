package clientView;

import clientController.LightController;
import clientModel.cards.LightLeaderCard;
import clientModel.resources.LightResource;
import clientModel.table.LightMarketBoard;
import network.server.Lobby;

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

    //ping
    public void serverLostConnection(){
        System.out.println("LOST SERVER CONNECTION");
        controller.quittingApplication();
    }

    public void askServerInfo(){
        boolean connectionCorrect = false;
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
        out.println("Username: ");
        username = in.nextLine();
        controller.setUsername(username);
    }

    public void askMenu() {
        boolean stop = false;
        while (!stop) {
            out.println("CHOOSE MODALITY: \n1- Single player \n2- Join Multiplayer Lobby" +
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
                    out.println("INVALID CHOICE\n");
                }
            }
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
        System.out.println("Lobby created\nWaiting for players to join...");
    }

    public void notifyPlayerJoined(String username){
        System.out.println(username +" has joined the lobby");
    }

    public void askStartGame(){
        System.out.println("Write \"start\" to begin the game");
        waitStartGameString();
    }

    public void waitStartGameString(){
        String s = "";
        do{
            s = in.nextLine();
            s = s.toLowerCase();
        }while (!s.equals("start"));
        controller.sendSignalMultiPlayerGame();
    }

    public void showWaitingRoom(){
        System.out.println("You joined the room");
        System.out.println("Waiting for creator of the room starts the game...");
    }

    public void showReconnectionToGame(){
        System.out.println("RICONNESSIONE AL GAME...");
    }

    public void waitingForMyTurn(){
        System.out.println("Others are playing, waiting for your turn starts");
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
    public void notifyPlayerDisconnected(String username) {
        System.out.println(username + " disconnected");
    }

    @Override
    public void notifyPlayerReconnected(String username) {
        System.out.println(username + " reconnected");
    }

    @Override
    public void notifyCreatorDisconnected() {
        System.out.println("The lobby creator has left the lobby");
    }


    @Override
    public void askTurn() {
        // System.out.println("IT'S YOUR TURN!\n");
        int ans = 0;
        do {
            printMenu();
            ans = in.nextInt();
        } while ((ans < 1 || ans > 6) && ans!=53550 );
        switch (ans) {
            case 1 : askLeader(); break;
            case 2 : askProduction(); break;
            case 3 : askBuyResources(); break;
            case 4 : askBuyDevCards(); break;
            case 5 : askShow(); break;
            case 6 : askEndTurn(); break;
            case 53550: cheat(); break;
        }
    }

    private void printMenu(){
        System.out.println(controller.getPlayerBoard().getFaithTrack().toString());
        System.out.println(controller.getPlayerBoard().getWarehouseDepot().toString());
        System.out.println(controller.getPlayerBoard().getStrongbox().toString());
        System.out.println(controller.getPlayerBoard().getCardSlots().toString());
        System.out.println("CHOOSE YOUR ACTION\n\b" +
                "1 per compiere un`azione leader\n\b" +
                "2 per attivare la produzione\n\b" +
                "3 per comprare le risorse\n\b" +
                "4 per comprare la carta sviluppo\n\b" +
                "5 per visualizzare lo stato del gioco\n\b" +
                "6 per terminare il turno\n\n");
    }

    private void askShow(){
        int ans;
        do {
            System.out.println(
                    "1 per visualizzare la DevBoard\n\b" +
                    "2 per visualzizare il Market\n\b" +
                     "3 per tornare al menu\n\b");

            ans = in.nextInt();
        } while (ans < 1 || ans > 3);
        switch (ans) {
            case 1 : System.out.println(controller.getDevBoard().toString()); break;
            case 2 : System.out.println(controller.getMarketBoard().toString()); break;
            case 3 : break;
        }
        askTurn();
    }

    private void askLeader(){
        int ans = 0;
        do {
            System.out.println("CHOOSE YOUR ACTION\n\b" +
                    "1 per attivare la carta leader\n\b" +
                    "2 per scartare la carta leader\n\b" +
                    "3 per tornare al menu");
            ans = in.nextInt();
        } while (ans < 1 || ans > 3);
        switch (ans) {
            case 1 : askLeaderCardActivation(); break;
            case 2 : askLeaderCardThrowing(); break;
            case 3 : break;
        }
        askTurn();
    }

    private void askProduction(){
        int ans =0;
        do {
            System.out.println("digita 1 per produrre da una carta sviluppo\n" +
                    "2 per produrre dalla board\n" +
                    "3 per tornare al menu\n");
            ans = in.nextInt();
        }while(ans <1 || ans > 3);

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
        couple.forEach(System.out::println);
        int ans = 0;
        do {
            System.out.println("scegli la carta\no digita 0 per uscire:");
            for(LightLeaderCard card: couple){
                System.out.println("\n\b"+ (couple.indexOf(card)+1)+" per "+card);
            }
            ans = in.nextInt();
        } while(ans!= 1 && ans != 2 && ans!=0);
        if(ans!=0)
            controller.sendLeaderCardActivationRequest(couple.get(ans-1));

    }

    @Override
    public void askLeaderCardThrowing() {
        ArrayList<LightLeaderCard> couple = controller.getPlayerBoard().getLeaderSlot();
        couple.forEach(System.out::println);
        int ans = 0;
        do {
            System.out.println("scegli la carta:\no digita 0 per uscire");
            for(LightLeaderCard card: couple){
                System.out.println("\n\b"+ (couple.indexOf(card)+1)+" per "+card);
            }
            ans = in.nextInt();
        } while(ans!= 1 && ans != 2 && ans!=0);
        if(ans!=0)
            controller.sendLeaderCardThrowRequest(couple.get(ans-1));
    }

    @Override
    public void askBuyResources() {
        LightMarketBoard market = controller.getMarketBoard();
        System.out.println(market.toString());
        int choice;
        do {
            System.out.println("1 per scegliere la colonna\n2 per scegliere la riga\n3 per uscire\n");
            choice = in.nextInt();
        } while(choice!= 1 && choice != 2 && choice!=3);

        if(choice != 3) {
            boolean line = choice == 2;
            if (line)
                System.out.println("u choosed line ");
            else
                System.out.println("u choosed column");
            int number;
            if (line) {
                do {
                    System.out.println("digitare il numero di riga");
                    number = in.nextInt();
                }
                while (number < 1 || number > 3);
            } else {
                do {
                    System.out.println("digitare il numero di colonna");
                    number = in.nextInt();
                }
                while (number < 1 || number > 4);
            }
            String ans;
            do {
                System.out.println("do u want to add a leader card?\n [y/n]\n");
                ans = in.nextLine();
            } while (!ans.equals("y") && !ans.equals("n"));
            if (ans.equals("y")) {
                LightLeaderCard card = askLeaderCardUse();
                if (card != null && card.isWhiteConverter())
                    controller.sendBuyResourceRequest(line, number - 1, card);
            } else
                controller.sendBuyResourceRequest(line, number - 1, null);


            System.out.println(controller.getPlayerBoard().getWarehouseDepot().toString());
        }
        askTurn();

    }

    @Override
    public void askBuyDevCards() {
        System.out.println(controller.getDevBoard().toString());
        System.out.println(controller.getPlayerBoard().getCardSlots().toString());
        int deck = 0;
        do{
            System.out.println("scegli il numero del deck\ndigita 0 per tornare al menu\n");
            deck=in.nextInt();
        }while(deck<0 || deck>12);
        if(deck != 0) {
            int slot = 0;
            do {
                System.out.println("scegli a quale slot aggiungere la carta");
                slot = in.nextInt();
            } while (slot < 1 || slot > 3);
            String ans;
            do {
                System.out.println("do u want to add a leader card?\n [y/n]\n");
                ans = in.nextLine();
            } while (!ans.equals("y") && !ans.equals("n"));
            if (ans.equals("y")) {
                LightLeaderCard card = askLeaderCardUse();
                if (card != null && card.isDiscount())
                    controller.sendBuyDevCardRequest(deck - 1, slot - 1, card);
                else
                    System.out.println("ERROR CANT USE THIS CARD\n");
            } else {
                controller.sendBuyDevCardRequest(deck - 1, slot - 1, null);
            }
            System.out.println(controller.getPlayerBoard().getCardSlots().toString());
        }
        askTurn();

    }

    @Override
    public void askDevCardProduction() {
        System.out.println(controller.getPlayerBoard().getCardSlots());
        int slot = 0;
        int numSlots = controller.getPlayerBoard().getCardSlots().getSize();
        if(numSlots == 0){
            System.out.println("nessuno slot disponibile");
            return;
        }
        else {
            do {
                System.out.println("scegli lo slot\n");
                slot = in.nextInt();
            } while (slot < 1 || slot > numSlots);
            try {
                if (controller.getPlayerBoard().getCardSlots().getCard(slot-1) != null){
                    String ans;
                    do {
                        System.out.println("do u want to add a leader card?\n [y/n]\n");
                        ans = in.nextLine();
                    }while(!ans.equals("y") && !ans.equals("n"));
                    if(ans.equals("y")) {
                        LightLeaderCard card = askLeaderCardUse();
                        if (card != null && card.isExtraProd()) {
                            LightResource chosenResource = null;
                            int res;
                            do {
                                System.out.println("choose resource " +
                                        "1 for COIN\n2 for SERVANT\n3 for SHIELD\n4 for STONE");
                                res = in.nextInt();
                            }
                            while(res<1 || res>4);
                            switch (res) {
                                case 1 -> chosenResource = LightResource.COIN;
                                case 2 -> chosenResource = LightResource.SERVANT;
                                case 3 -> chosenResource = LightResource.SHIELD;
                                case 4 -> chosenResource = LightResource.STONE;
                            }
                            controller.sendDevCardProductionRequest(slot-1, chosenResource, card);
                        }
                    }
                    else{
                        controller.sendDevCardProductionRequest(slot-1, null, null);
                    }
                    System.out.println(controller.getPlayerBoard().getStrongbox().toString());
                    System.out.println(controller.getPlayerBoard().getWarehouseDepot().toString());
                }
            } catch (NullPointerException | IndexOutOfBoundsException e) {
                System.out.println("slot vuoto");
            }
        }
    }

    @Override
    public void askDefaultProduction() {
       System.out.println(controller.getPlayerBoard().getStrongbox().toString());
        System.out.println(controller.getPlayerBoard().getWarehouseDepot().toString());
        int first =0;
        int second = 0;
        System.out.println("scegli le due risorse in input");
        do {
            System.out.println("choose resource " +
                    "1 for COIN\n2 for SERVANT\n3 for SHIELD\n4 for STONE");
            first = in.nextInt();
        }while(first<1 || first>4);
        do {
            System.out.println("choose resource " +
                    "1 for COIN\n2 for SERVANT\n3 for SHIELD\n4 for STONE");
            second = in.nextInt();
        }while(second<1 || second>4);
        System.out.println("scegli la risorsa in output");
        int outRes= 0;
        do {
            System.out.println("choose resource " +
                    "1 for COIN\n2 for SERVANT\n3 for SHIELD\n4 for STONE");
            outRes = in.nextInt();
        }while(outRes<1 || outRes>4);
        ArrayList<LightResource> choice = new ArrayList<>();
        switch (first) {
            case 1 -> choice.add(LightResource.COIN);
            case 2 -> choice.add(LightResource.SERVANT);
            case 3 -> choice.add(LightResource.SHIELD);
            case 4 -> choice.add(LightResource.STONE);
        }
        switch (second) {
            case 1 -> choice.add(LightResource.COIN);
            case 2 -> choice.add(LightResource.SERVANT);
            case 3 -> choice.add(LightResource.SHIELD);
            case 4 -> choice.add(LightResource.STONE);
        }
        LightResource output = null;
        switch (outRes) {
            case 1 -> output = LightResource.COIN;
            case 2 -> output = LightResource.SERVANT;
            case 3 -> output = LightResource.SHIELD;
            case 4 -> output = LightResource.STONE;
        }
        String ans;
        do {
            System.out.println("do u want to add a leader card?\n [y/n]\n");
            ans = in.nextLine();
        }while(!ans.equals("y") && !ans.equals("n"));
        if(ans.equals("y")) {
            LightLeaderCard card = askLeaderCardUse();
            if (card != null && card.isExtraProd()) {
                LightResource chosenResource = null;
                int res;
                do {
                    System.out.println("choose resource " +
                            "1 for COIN\n2 for SERVANT\n3 for SHIELD\n4 for STONE");
                    res = in.nextInt();
                }
                while(res<1 || res>4);
                switch (res) {
                    case 1 -> chosenResource = LightResource.COIN;
                    case 2 -> chosenResource = LightResource.SERVANT;
                    case 3 -> chosenResource = LightResource.SHIELD;
                    case 4 -> chosenResource = LightResource.STONE;
                }
                controller.sendDefaultProductionRequest(choice, output, card, chosenResource);
            }
        }
        else{
            controller.sendDefaultProductionRequest(choice, output, null, null);
        }
        System.out.println(controller.getPlayerBoard().getStrongbox().toString());
        System.out.println(controller.getPlayerBoard().getWarehouseDepot().toString());
    }

    @Override
    public void askEndTurn() {
        System.out.println(" FINE TURNO ");
        System.out.println(" attendi ...");
        controller.sendEndTurnRequest();
    }

    @Override
    public void showThrewResources(ArrayList<LightResource> threwResources) {
        if(!threwResources.isEmpty()) {
            System.out.println("le risorse: ");
            System.out.println(threwResources);
            System.out.println("non sono state accettate: spazio insufficiente");
        }
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

    public LightLeaderCard askLeaderCardUse(){
        System.out.println("choose an active leader card: ");
        ArrayList<LightLeaderCard> cards = controller.getPlayerBoard().getLeaderSlot();
        int choice = 0;
        do{
            System.out.println(cards);
            choice = in.nextInt();
        }while(choice != 1 && choice!=2);

        LightLeaderCard card = cards.get(choice-1);
        if(!card.isEnabled()) {
            System.out.println("Leader card not active!");
            card = null;
        }
        return card;
    }

    public void notifyJoin(){

    }

    public void endGame(){
        System.out.println("see you space cowboy...\n");
        controller.quittingApplication();
    }

    private void cheat(){
        System.out.println("\nBURLONEEEE\n");
        controller.sendCheat();
        askTurn();
    }
}
