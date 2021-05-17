package controller;

import exceptions.*;
import model.Game;
import model.cards.Discount;
import model.cards.ExtraProd;
import model.cards.LeaderCard;
import model.cards.WhiteConverter;
import model.player.Player;
import model.resources.Resource;
import model.table.Deck;
import model.table.DevelopmentBoard;
import model.table.MarketBoard;
import network.messages.gameMessages.GameMessage;
import network.messages.gameMessages.InternalErrorNotify;
import view.VirtualClient;

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
        System.out.println("CONTROLLER CREATO");
    }

    public void executeCommand(GameMessage message, VirtualClient client) {

        message.executeCommand(this, client);
    }
    public ArrayList<VirtualClient> getViews(){
        return views;
    }
    public int getId() {
        return id;
    }

    //-----------tutto quello nel gioco
    public void buyDevCard(Deck deck, int slot, Discount card) throws FullCardSlotException,
            NonCorrectLevelCardException,
            InsufficientResourcesException,
            EmptyDeckException,
            UnusableCardException{
        try {
            game.buyDevCard(deck, slot, card);
        }catch(UnknownError e){
            handleError(e.getMessage());
        }
    }
    public void buyResources(boolean line, int num, WhiteConverter card) throws UnusableCardException {
        game.buyResources(line, num, card);
    }
    public ArrayList getThrewResources(){
        return game.getThrewResources();
    }
    public void devCardProduction(int slot, Resource chosenOutput, ExtraProd card) throws InsufficientResourcesException,
            UnusableCardException {
        game.devCardProduction(slot, chosenOutput, card);
    }
    public void defaultProduction(ArrayList<Resource> input, Resource output, LeaderCard card, Resource chosenOutput) throws InsufficientResourcesException,
            UnusableCardException {
        game.defaultProduction(input, output, card, chosenOutput);
    }
    public void activateLeaderCard(LeaderCard card) throws InsufficientRequirementsException,
            InsufficientResourcesException {
        game.activateLeaderCard(card);
    }

    public Player getCurrentPlayer() {
        return game.getCurrentPlayer();
    }
    public MarketBoard getMarketBoard(){
        return game.getMarketBoard();
    }
    public DevelopmentBoard getDevBoard(){
        return game.getDevBoard();
    }
    public void endTurn(){ game.updateTurn(); }

    private void handleError(String message){
        views.forEach(element -> element.getVirtualView().update(new InternalErrorNotify(element.getVirtualView().getUsername(), message)));
        //TODO: brutto?
    }
}
