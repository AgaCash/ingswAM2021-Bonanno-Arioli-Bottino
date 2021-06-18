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

    public Player(String nickname){
        this.nickname = nickname;
        this.playerboard = new PlayerBoard();
    }

    /*public Player(){
        this.playerboard = new PlayerBoard(this);
    }*/

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
}
