package clientModel.table;

import clientModel.marbles.LightMarble;

import java.util.ArrayList;

/**
 * LightModel's copy of MarketBoard in Model.
 */
public class LightMarketBoard {

    private ArrayList<ArrayList<LightMarble>> marbleGrid = new ArrayList<>(3);
    private LightMarble freeMarble;

    /**Updates the LightMarketBoard with the new Model's MarketBoard status
     * @marbles a LightMarble ArrayList representing the Model's MarketBoard status
     */
    public void setMarketBoard (ArrayList<LightMarble> marbles){
        for (int n = 0; n < 3; n++)
            marbleGrid.add(new ArrayList(4));

        int k =0;
        for(int i=0; i<3; i++)
            for(int j =0; j<4; j++){
                marbleGrid.get(i).add(j, marbles.get(k));
                k++;
            }
        freeMarble = marbles.get(k);
    }

    /**Returns the current free Marble
     * @return a LightMarble instance
     */
    public LightMarble getFreeMarble (){
        return freeMarble;
    }

    /**Returns the coordinates corresponding LightMarble
     * @param whichOneLine the number of Line
     * @param whichOneCol the number of Column
     * @return a LightMarble instance
     */
    public LightMarble getMarble (int whichOneLine, int whichOneCol){
        LightMarble mar;
        mar = marbleGrid.get(whichOneLine).get(whichOneCol);
        return mar;
    }

    /**Method to print LightMarketBoard in CLI
     * @return a String
     */
    @Override
    public String toString(){
        String s = "______________________\n";
        for(int i=0; i<3; i++){
            s+="|";
            for(int j =0; j<4; j++){
                s+="| "+marbleGrid.get(i).get(j)+" |";
            }
            s+="|\n";
        }
        s+="|| "+freeMarble+" ||\n";
        s+="______________________";
        return s;
    }

}
