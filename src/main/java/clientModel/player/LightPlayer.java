package clientModel.player;


import clientModel.table.LightPlayerBoard;

public class LightPlayer {
    private String nickname;
    private int startingTurn;
    private int points = 0;
    private LightPlayerBoard playerBoard;

    public LightPlayer(){
        playerBoard = new LightPlayerBoard();
    }

    public int getPoints() {
        return points;
    }
    public void setPoints(int points){ this.points = points;}

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

    public LightPlayerBoard getPlayerBoard(){ return this.playerBoard; }
    public void setPlayerBoard(LightPlayerBoard playerBoard){ this.playerBoard = playerBoard; }
}
