package clientModel.table;

public class LightTable {
    private LightDevelopmentBoard developmentBoard;
    private LightMarketBoard marketBoard;

    public LightDevelopmentBoard getDevBoard(){
        return this.developmentBoard;
    }
    public void setDevelopmentBoard(LightDevelopmentBoard devBoard){ this.developmentBoard = devBoard; }

    public LightMarketBoard getMarketBoard(){
        return this.marketBoard;
    }
    public void setMarketBoard(LightMarketBoard market){
        this.marketBoard = market;
    }

}
