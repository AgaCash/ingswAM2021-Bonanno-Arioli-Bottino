package model.player;

import model.table.PlayerBoard;

public class Player {
    private String nickname;
    private transient int startingTurn;
    private transient int points = 0;
    private transient PlayerBoard playerboard;

    public Player(String nickname){
        this.nickname = nickname;
        this.playerboard = new PlayerBoard(this);
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



    @Override
    public String toString() {
        return "{" +
                "\"nickname\":\"" + nickname + '\"' +
                '}';
    }
}
