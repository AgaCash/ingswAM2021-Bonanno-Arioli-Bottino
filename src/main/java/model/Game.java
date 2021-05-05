package model;

import model.cards.Discount;
import model.cards.ExtraProd;
import model.cards.LeaderCard;
import model.cards.WhiteConverter;
import model.resources.Resource;
import model.singleplayer.Lorenzo;
import model.table.Deck;
import model.table.PlayerBoard;
import model.table.Table;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;
import java.util.Collections;

public class Game {
    private static Game instance = null;
    private ArrayList<PlayerBoard> players = new ArrayList<>();
    private Table table;
    private PlayerBoard currentPlayer;
    private Lorenzo cpu;
    private boolean singlePlayer;
    //Lorenzo cpu = new Lorenzo();  will be ok when DevBoard will adopt singleton pattern
    //-------starting
    private Game(boolean singlePlayer){
        this.singlePlayer = singlePlayer;
    }
    //update singleton
    public static Game getGameInstance(boolean singlePlayer){
        if (instance == null)
            //synchronized(model.Game.class){
            //    if(instance ==null)
            //        instance = new model.Game();
            //    }   double-checked locking
            instance = new Game(singlePlayer);
        return instance;
    }
    //primo: @controller aggiunge i player
    public void addPlayer(Player newPlayer){
        if(players.size()<4){
            players.add(new PlayerBoard(newPlayer, table.getDevBoard(), table.getMarketBoard()));
        }
    }
    //secondo: gioco inizia
    public void initializeGame(){
        table = Table.getTableInstance();
        Collections.shuffle(players);
        if(singlePlayer)
            cpu = new Lorenzo(table.getDevBoard());
    }
    //send leader cards
    public ArrayList<LeaderCard> sendQuartet(){
        return table.sendQuartet();
    }
    //receive leader cards
    public void assignLeaderCards(String username, LeaderCard first, LeaderCard second){
        PlayerBoard user = getPlayerBoard(username);
        if(user!=null)
            user.addLeaderCards(first, second);
    }
    //starting resources
    public void addStartingResources(){
        //bla bla bla
    }
    //
    public ArrayList<String> getPlayerTurns(){
        ArrayList<String> turns = new ArrayList<>();
        for(PlayerBoard p : players)
            turns.add(p.getPlayer().getNickname());
        return turns;
    }
    //change turn
    public void changeTurn(){
        if(singlePlayer) {
            cpu.pick();
        }
        else {
            int turn = players.indexOf(currentPlayer);
            if (turn == 3)
                turn = 0;
            else
                turn += 1;
            currentPlayer = players.get(turn);
        }
    }
    //-----------tutto quello nel gioco
    public void buyDevCard(Deck deck, int slot, Discount card) throws OperationNotSupportedException {
        currentPlayer.buyDevCard(deck, slot, card);
    }

    public void buyResources(boolean line, boolean column, int num, WhiteConverter card){
        currentPlayer.buyResources(line, column, num, card);
    }

    public void devCardProduction(int slot, Resource chosenOutput, ExtraProd card) throws OperationNotSupportedException {
        currentPlayer.devCardProduction(slot, chosenOutput, card);
    }

    public void defaultProduction(ArrayList<Resource> input, Resource output, LeaderCard card, Resource chosenOutput){
        currentPlayer.defaultProduction(input, output, card, chosenOutput);
    }

    public void activateLeaderCard(LeaderCard card){
        currentPlayer.activateLeaderCard(card);
    }
    //============ENDGAME=======
    //devi fare la classifica dei punti vittoria
    //contare le carte dev per ogni giocatore
    //










    public void ingConti(){
        System.out.println("ಠ_ಠ");

    }

    private PlayerBoard getPlayerBoard(String username){
        for(PlayerBoard p : players){
            if(username.equals(p.getPlayer().getNickname()))
                return p;
        }
        return null;
    }




}
