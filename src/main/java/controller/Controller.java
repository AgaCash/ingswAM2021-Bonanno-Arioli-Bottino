package controller;

import exceptions.FullCardSlotException;
import exceptions.InsufficientRequirementsException;
import exceptions.InsufficientResourcesException;
import exceptions.NonCorrectLevelCardException;
import model.Game;
import model.cards.*;
import model.resources.Resource;
import model.table.Deck;
import model.table.DevelopmentBoard;
import model.table.MarketBoard;
import model.table.PlayerBoard;
import network.messages.gameMessages.GameMessage;
import view.VirtualClient;
import view.VirtualView;

import java.util.ArrayList;

public class Controller {
    private int id;
    private Game game;
    private ArrayList<VirtualClient> views;

    public Controller(int id, ArrayList<VirtualClient> views){
        this.views = views;
        this.id = id;
        boolean isSinglePlayer = (views.size()==1);
        game = new Game(isSinglePlayer);
        System.out.println("CONTORLLER CREATO");
    }

    public void executeCommand(GameMessage message) {
        message.executeCommand(this, views);
    }

    public int getId() {
        return id;
    }
    //-----------tutto quello nel gioco
    public void buyDevCard(Deck deck, int slot, Discount card) throws FullCardSlotException,
                                                                        NonCorrectLevelCardException,
                                                                        InsufficientResourcesException {
        game.buyDevCard(deck, slot, card);
    }
    public void buyResources(boolean line, int num, WhiteConverter card){
        game.buyResources(line, num, card);

    }
    public ArrayList getThrewResources(){
        return game.getThrewResources();
    }
    public void devCardProduction(int slot, Resource chosenOutput, ExtraProd card) throws InsufficientResourcesException {
        game.devCardProduction(slot, chosenOutput, card);
    }
    public void defaultProduction(ArrayList<Resource> input, Resource output, LeaderCard card, Resource chosenOutput) throws InsufficientResourcesException {
        game.defaultProduction(input, output, card, chosenOutput);
    }
    public void activateLeaderCard(LeaderCard card) throws InsufficientRequirementsException, InsufficientResourcesException {
        game.activateLeaderCard(card);
    }

    public PlayerBoard getCurrentPlayer() {
        return game.getCurrentPlayer();
    }

    public MarketBoard getMarketBoard(){
        return game.getMarketBoard();
    }

    public DevelopmentBoard getDevBoard(){
        return game.getDevBoard();
    }

    public void endTurn(){
        game.updateTurn();
    }
}
