package model.player;

import clientModel.player.LightPlayer;
import model.table.FaithTrack;
import model.table.PlayerBoard;

public class Player {
    private String nickname;
    private int startingTurn;
    private int points = 0;
    private PlayerBoard playerboard;

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
        player.getPlayerBoard().setPlayer(player);

        return player;
    }
}
