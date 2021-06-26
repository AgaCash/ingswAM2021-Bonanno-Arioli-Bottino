package model.player;

import clientModel.player.LightPlayer;
import model.cards.DevelopmentCard;
import model.cards.LeaderCard;
import model.table.FaithBox;
import model.table.FaithTrack;
import model.table.PlayerBoard;

public class Player {
    private String nickname;
    private int startingTurn;
    private int points = 0;
    private PlayerBoard playerboard;
    private boolean victory;
    private boolean didAction;
    private boolean didProduction;
    private int didLeader;

    public Player(String nickname){
        this.victory = false;
        this.nickname = nickname;
        this.playerboard = new PlayerBoard();
    }

    public void addPoints(int pts){
        points += pts;
    }

    public int getPoints() {
        return points;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getStartingTurn() {
        return startingTurn;
    }

    public void setStartingTurn(int startingTurn) {
        this.startingTurn = startingTurn;
    }

    public PlayerBoard getPlayerBoard(){
        return this.playerboard;
    }

    public FaithTrack getFaithTrack(){
        return this.playerboard.getFaithTrack();
    }

    public FaithBox getFaithBox(){
        return this.playerboard.getFaithBox();
    }

    public int getScore(){
        points += getFaithTrack().getFaithBox().getPoints();
        for(int i = 0; i<3; i++)
            try {
                DevelopmentCard card = this.playerboard.getCardSlots().getCard(i);
                points += card.getVictoryPoints();
            }catch(NullPointerException e){
                ;
            }
        for(LeaderCard card: this.playerboard.getLeaders())
            if(card.isEnabled())
                points += card.getVictoryPoints();

        int numResources = this.playerboard.getWarehouseDepot().status().size();
        numResources+=this.playerboard.getStrongbox().status().size();
        points += numResources/5;

        return points;
    }

    public void setVictory(boolean value){
        this.victory = value;
    }
    public boolean isTheWinner(){
        return this.victory;
    }


    @Override
    public String toString() {
        return "{" +
                "\"nickname\":\"" + nickname + '\"' +
                '}';
    }

    public LightPlayer convert(){
        LightPlayer player = new LightPlayer();

        player.setNickname(getNickname());
        player.setStartingTurn(getStartingTurn());
        player.setPoints(this.points);
        player.setPlayerBoard(this.playerboard.convert());

        return player;
    }

    public boolean canBuyResources(){
        if(didAction || didProduction || didLeader==2)
            return false;
        return true;
    }

    public void didAction(){
        this.didAction = true;
    }

    public boolean canBuyDevCards(){
        if(didAction || didProduction || didLeader==2)
            return  false;
        return true;
    }

    public boolean canDoProduction(){
        if(didAction || didLeader == 2 || this.playerboard.canDoDefProduction())
            return false;
        return true;
    }

    public void didProduction(){
        this.didProduction = true;
    }

    public boolean canDoLeader(){
        if(this.didLeader==0 || (didAction || didProduction))
            return true;
        return false;
    }

    public void didLeader(){
        this.didLeader++;
    }

    public void updateActions(){
        this.didAction = false;
        this.didProduction = false;
        this.didLeader = 0;
    }

    //TODO SISTEMARE I TURNI TEOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO DAI DAI CHE CE LA FAI

}
