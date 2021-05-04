package model.table;

import model.cards.LeaderCard;
import model.marbles.Marble;
import model.resources.Resource;
import model.utilities.JsonParser;

import java.util.ArrayList;
import java.util.Collections;


/**
 * class that contains and manages the market's model.marbles
 */
public class MarketBoard {
    private ArrayList<ArrayList<Marble>> marbleGrid = new ArrayList<>(3);
    private Marble freeMarble;

    /*private static MarketBoard instance = null;

    private MarketBoard(){
       initializeMarbleGrid();
       }

    public static MarketBoard getMarketInstance(){
        if (instance == null)
            //synchronized(MarketBoard.class){
            //    if(instance ==null)
            //        instance = new MarketBoard();
            //    }   double-checked locking
            instance = new MarketBoard();
        return instance;
    }  singleton initialization for MarketBoard
*/


    /**
     * method called at the start of the game that initializes the marble grid
     */
    public void initializeMarbleGrid() {
        ArrayList<Marble> marbles =  new JsonParser("src/main/resources/marketBoard.json").getMarbles();
        for (int n = 0; n < 4; n++)
            marbleGrid.add(new ArrayList(3));
        Collections.shuffle(marbles);

        int k =0;
        for(int i=0; i<3; i++)
            for(int j =0; j<4; j++){
                marbleGrid.get(i).add(j, marbles.get(k));
                k++;
            }
        freeMarble = marbles.get(k);
    }

    /** method to obtain model.resources from the market by line
     * @param line the line of the grid requested
     * @param card eventual leader card passed to obtain bonuses
     * @return arraylist of requested model.resources
     */
    public ArrayList<Resource> addMarketLine (int line, LeaderCard card){
        Resource convertResource = checkWhiteConverter(card);
        ArrayList<Resource> resLine = new ArrayList<>(4);
        Marble toConvert;

        for(int i = 0; i<4; i++){
            toConvert = marbleGrid.get(line).get(i);
            resLine.add(toConvert.convertMarble(convertResource));
        }
        freeMarble = marbleGrid.get(line).get(3);
        int i;
        for(i =3 ; i>0; i--)
            marbleGrid.get(line).set(i, marbleGrid.get(line).get(i-1));
        marbleGrid.get(line).set(i, freeMarble);
        return resLine;
    }

    /** method to obtain model.resources from the market by column
     * @param col the column of the grid requested
     * @param card eventual leader card passed to obtain bonuses
     * @return arraylist of requested model.resources
     */
    public ArrayList<Resource> addMarketColumn (int col, LeaderCard card){
        Resource convertResource = checkWhiteConverter(card);
        ArrayList<Resource> resColumn = new ArrayList<>(3);
        Marble toConvert;
        for(int i = 0; i<3; i++){
            toConvert = marbleGrid.get(i).get(col);
            resColumn.add(toConvert.convertMarble(convertResource));
        }
        freeMarble = marbleGrid.get(2).get(col);
        marbleGrid.get(1).set(col, marbleGrid.get(0).get(col));
        marbleGrid.get(0).set(col, freeMarble);
        return resColumn;
    }

    /** getter method for the market board
     * @param whichOneLine arraylist's line requested for the get
     * @param whichOneCol arraylist's column requested for the get
     * @return marble
     */
    public Marble getMarble (int whichOneLine, int whichOneCol){
        Marble mar;
        mar = marbleGrid.get(whichOneLine).get(whichOneCol);
        return mar;
    }

    private Resource checkWhiteConverter(LeaderCard card) {
        if (card != null && card.isWhiteConverter())
            return card.whichResource();
        return null;
    }

}

