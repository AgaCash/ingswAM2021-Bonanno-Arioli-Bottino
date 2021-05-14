package model.player;

public class Player {
    private String nickname;
    private int startingTurn;
    private int points = 0;

    public Player(String nickname){
        this.nickname = nickname;
    }

    public Player(){
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

}
