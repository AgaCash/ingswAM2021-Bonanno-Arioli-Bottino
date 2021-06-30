package clientModel.cards;

/**
 * LightModel copy of Model's LeaderCard
 */
public class LightLeaderCard{
    protected int id;
    protected boolean isEnabled;
    protected int victoryPoints;
    protected LightLeaderCardType type;

    /**Constructor representing a "null" LeaderCard (an empty LeaderSlot)
     *
     */
    public LightLeaderCard(){
        this.type = null;
    }

    public boolean isDiscarded(){
        return this.type == null;
    }

    /**Returns card's ID
     * @return an int
     */
    public int getId(){ return id; }

    /**Returns true if LeaderCard instance is a Discount, false if not
     * @return a boolean
     */
    public boolean isDiscount(){
        return false;
    }

    /**Returns true if LeaderCard instance is a ExtraDepot, false if not
     * @return a boolean
     */
    public boolean isExtraDepot() {
        return false;
    }

    /**Returns true if LeaderCard instance is a ExtraProd, false if not
     * @return a boolean
     */
    public boolean isExtraProd(){
        return false;
    }

    /**Returns true if LeaderCard instance is a WhiteConverter, false if not
     * @return a boolean
     */
    public boolean isWhiteConverter(){
        return false;
    }

    /**Returns true if Card is enabled
     * @return a boolean
     */
    public boolean isEnabled(){ return  isEnabled; }

    /**Method to print LeaderCard in CLI
     * @return
     */
    @Override
    public String toString(){
        return "";
    }

}
