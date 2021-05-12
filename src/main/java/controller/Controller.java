package controller;

import exceptions.*;
import model.Game;
import model.cards.Discount;
import model.cards.ExtraProd;
import model.cards.LeaderCard;
import model.cards.WhiteConverter;
import model.resources.Resource;
import model.table.Deck;
import network.messages.Message;
import network.messages.gameMessages.GameMessage;
import view.VirtualView;

import javax.naming.OperationNotSupportedException;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Controller {
    private int id;
    private Game game;
    private ArrayList<VirtualView> views;

    public Controller(int id, ArrayList<VirtualView> views){
        System.out.println("CONTORLLER CREATO");
        this.views = views;
        this.id = id;
        boolean isSinglePlayer = (views.size()==1);
        game = new Game(isSinglePlayer);
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
    public void buyResources(boolean line, int num, WhiteConverter card) throws FullWarehouseException {

        game.buyResources(line, num, card);
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

    public void endTurn(){
        game.updateTurn();
    }
}
