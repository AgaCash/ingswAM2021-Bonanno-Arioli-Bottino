package clientModel.player;


import clientModel.table.LightPlayerBoard;

/**
 * LightModel copy of Model's Player
 */
public class LightPlayer {
    private String nickname;
    private int startingTurn;
    private int points = 0;
    private LightPlayerBoard playerBoard;

    public LightPlayer(){
        playerBoard = new LightPlayerBoard();
    }

    /**Returns the Player's current victory points
     * @return an int
     */
    public int getPoints() {
        return points;
    }

    /**Updates the Player's current victory points
     * @param points an int
     */
    public void setPoints(int points){ this.points = points;}

    /**Returns Player's username
     * @return a String
     */
    public String getNickname() {
        return nickname;
    }

    /**Sets the Player's username in this LightPlayer copy
     * @param nickname Player's username
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**Returns Player's corresponding PlayerBoard
     * @return a LightPlayerBoard instance
     */
    public LightPlayerBoard getPlayerBoard(){ return this.playerBoard; }

    /**Updates the Player's PlayerBoard in LightModel
     * @param playerBoard the LightPlayerBoard copy of PlayerBoard in Model
     */
    public void setPlayerBoard(LightPlayerBoard playerBoard){ this.playerBoard = playerBoard; }
}
