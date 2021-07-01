package clientModel.table;

/**
 * LightModel copy of Model's Table
 */
public class LightTable {
    private LightDevelopmentBoard developmentBoard;
    private LightMarketBoard marketBoard;

    /**Returns current LightDevelopmentBoard
     * @return a LightDevelopmentBoard instance
     */
    public LightDevelopmentBoard getDevBoard(){
        return this.developmentBoard;
    }

    /**Updates the LightDevelopmentBoard with the current Model's DevelopmentBoard status
     * @param devBoard a LightDevelopmentBoard instance
     */
    public void setDevelopmentBoard(LightDevelopmentBoard devBoard){ this.developmentBoard = devBoard; }

    /**Returns current LightMarketBoard
     * @return a LightMarketBoard instance
     */
    public LightMarketBoard getMarketBoard(){
        return this.marketBoard;
    }

    /**Updates the LightMarketBoard with the current Model's MarketBoard status
     * @param market a LightDevelopmentBoard instance
     */
    public void setMarketBoard(LightMarketBoard market){
        this.marketBoard = market;
    }

}
