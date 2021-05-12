package model;

import exceptions.*;
import model.cards.Discount;
import model.cards.ExtraProd;
import model.cards.LeaderCard;
import model.cards.WhiteConverter;
import model.resources.Resource;
import model.singleplayer.Lorenzo;
import model.table.Deck;
import model.table.PlayerBoard;
import model.table.Table;

import java.util.ArrayList;
import java.util.Collections;

public class Game {
    private ArrayList<PlayerBoard> players = new ArrayList<>();
    private Table table;
    private PlayerBoard currentPlayer;
    private Lorenzo cpu;
    private boolean singlePlayer;
    private ArrayList<Resource> bufferStrongbox = new ArrayList<>();
    //Lorenzo cpu = new Lorenzo();  will be ok when DevBoard will adopt singleton pattern

    public Game(boolean singlePlayer){
        this.singlePlayer = singlePlayer;
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

    public void updateTurn(){
        if(!bufferStrongbox.isEmpty())
            updateStrongbox();
        changeTurn();
    }



    //-----------GAME
    public void buyDevCard(Deck deck, int slot, Discount card)   throws FullCardSlotException,
                                                                        NonCorrectLevelCardException,
                                                                        InsufficientResourcesException{
        currentPlayer.buyDevCard(deck, slot, card);
    }

    public void buyResources(boolean line, int num, WhiteConverter card) throws FullWarehouseException {
        if(line)
            currentPlayer.buyResources(true, false, num, card);
        else
            currentPlayer.buyResources(false, true, num, card);
        
    }

    public void devCardProduction(int slot, Resource chosenOutput, ExtraProd card) throws
                                                                                            InsufficientResourcesException{
        this.bufferStrongbox.addAll(currentPlayer.devCardProduction(slot, chosenOutput, card));
    }

    public void defaultProduction(ArrayList<Resource> input, Resource output, LeaderCard card, Resource chosenOutput) throws InsufficientResourcesException {
        this.bufferStrongbox.addAll(currentPlayer.defaultProduction(input, output, card, chosenOutput));
    }

    public void activateLeaderCard(LeaderCard card) throws InsufficientRequirementsException, InsufficientResourcesException {
        currentPlayer.activateLeaderCard(card);
    }
    //============ENDGAME=======
    //devi fare la classifica dei punti vittoria
    //contare le carte dev per ogni giocatore
    //============utilities

    private void updateStrongbox(){
        this.bufferStrongbox.forEach(element -> currentPlayer.getStrongbox().addResource(element));
    }

    private PlayerBoard getPlayerBoard(String username){
        for(PlayerBoard p : players){
            if(username.equals(p.getPlayer().getNickname()))
                return p;
        }
        return null;
    }

    //change turn
    private void changeTurn(){
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




}
