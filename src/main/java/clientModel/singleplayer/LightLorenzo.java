package clientModel.singleplayer;

import clientModel.table.LightDevelopmentBoard;
import clientModel.table.LightFaithBox;
import clientModel.table.LightFaithTrack;

import java.util.ArrayList;

public class LightLorenzo {
    private LightFaithTrack faithTrack = new LightFaithTrack();
    //what's this?
    private LightFaithBox faithBox = new LightFaithBox();
    public ArrayList<LightToken> tokens ;//= new ArrayList<>(6);
    private LightDevelopmentBoard developmentBoard;

    public void setFaithTrack(LightFaithTrack faithTrack){
        this.faithTrack = faithTrack;
    }

    public void setTokens(ArrayList<LightToken> tokens){
        this.tokens = tokens;
    }

    public void setDevBoard(LightDevelopmentBoard board){
        this.developmentBoard = board;
    }
}
