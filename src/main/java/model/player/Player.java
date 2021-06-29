package model.player;

import clientModel.player.LightPlayer;
import model.cards.DevelopmentCard;
import model.cards.LeaderCard;
import model.table.FaithTrack;
import model.table.PlayerBoard;

/**
 * Represents and encapsulates all the Player tools
 */
public class Player {
    private String nickname;
    private int startingTurn;
    private int points = 0;
    private PlayerBoard playerboard;
    private boolean victory;
    private boolean didAction;
    private boolean didProduction;
    private int didLeader;

    /**Constructor
     * @param nickname Player's username
     */
    public Player(String nickname){
        this.victory = false;
        this.nickname = nickname;
        this.playerboard = new PlayerBoard();
    }

    /**Adds victory points to Player
     * @param pts an int
     */
    public void addPoints(int pts){
        points += pts;
    }

    public int getNewPoints(){
        //todo da scrivere
        //capiamo
        return 69;
    }

    /**Returns Player's current victory points
     * @return an int
     */
    public int getPoints() {
        return points;
    }

    /**Returns Player username
     * @return a String
     */
    public String getNickname() {
        return nickname;
    }

    /**Returns the Player turns order position
     * @return an int
     */
    public int getStartingTurn() {
        return startingTurn;
    }

    /**Sets the Player turns order position
     * @param startingTurn an int
     */
    public void setStartingTurn(int startingTurn) {
        this.startingTurn = startingTurn;
    }

    /**Returns the Player's PlayerBoard
     * @return a PlayerBoard instance
     */
    public PlayerBoard getPlayerBoard(){
        return this.playerboard;
    }

    /**Returns Player's FaithTrack
     * @return a FaithTrack instance
     */
    public FaithTrack getFaithTrack(){
        return this.playerboard.getFaithTrack();
    }

    /**Returns the current Player's score summing all victory points gained
     * @return an int
     */
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

    /**Sets the Player as winner
     * @param value true if is the winner, false if not
     */
    public void setVictory(boolean value){
        this.victory = value;
    }

    /**Returns true if Player is the winner, false if not
     * @return a boolean
     */
    public boolean isTheWinner(){
        return this.victory;
    }


    /**just 4 tests
     * @return
     */
    @Override
    public String toString() {
        return "{" +
                "\"nickname\":\"" + nickname + '\"' +
                '}';
    }

    /**Returns the current state Player in a LightPlayer instance for LightModel
     * @return
     */
    public LightPlayer convert(){
        LightPlayer player = new LightPlayer();

        player.setNickname(getNickname());
        player.setStartingTurn(getStartingTurn());
        player.setPoints(this.points);
        player.setPlayerBoard(this.playerboard.convert());

        return player;
    }

    /**Returns true if Player can do a Resource purchase in the current turn
     * @return a boolean
     */
    public boolean canBuyResources(){
        return !didAction && !didProduction && didLeader != 2;
    }

    /**Returns true if Player can do a DevelopmentCard purchase in the current turn
     * @return a boolean
     */
    public boolean canBuyDevCards(){
        return !didAction && !didProduction && didLeader != 2;
    }

    /**Returns true if Player can do a DevelopmentCard production in the current turn
     * @return a boolean
     */
    public boolean canDoProduction(){
        return !didAction && didLeader != 2;
    }

    /**Returns true if Player can do a Default Production in the current turn
     * @return a boolean
     */
    public boolean canDoDefProduction(){
        return this.playerboard.canDoDefProduction();
    }

    /**Player did a BuyResource or BuyDevCards action
     *
     */
    public void didAction(){
        this.didAction = true;
    }

    /**Player did a DefaultProduction action
     *
     */
    public void didDefProduction(){
        this.playerboard.didProduction();
    }

    /**
     * Player did a Production
     */
    public void didProduction(){
        this.didProduction = true;
    }

    /**Player can't do another LeaderAction in this turn
     * @return
     */
    public boolean cantDoLeader(){
        return this.didLeader != 0 && (!didAction && !didProduction);
    }

    /**
     * Player did a Leader Action
     */
    public void didLeader(){
        this.didLeader++;
    }

    /**Update turn actions' flags after turn
     *
     */
    public void updateActions(){
        this.playerboard.backUsable();
        this.didAction = false;
        this.didProduction = false;
        this.didLeader = 0;
    }

}
