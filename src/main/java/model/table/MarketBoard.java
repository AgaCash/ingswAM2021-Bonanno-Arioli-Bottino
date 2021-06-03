package model.table;

import clientModel.marbles.LightMarble;
import clientModel.table.LightMarketBoard;
import exceptions.UnusableCardException;
import model.cards.WhiteConverter;
import model.marbles.Marble;
import model.resources.Resource;
import utilities.JsonParser;

import java.util.ArrayList;
import java.util.Collections;


/**
 * class that contains and manages the market's model.marbles
 */
public class MarketBoard {
    private ArrayList<ArrayList<Marble>> marbleGrid = new ArrayList<>(3);
    private Marble freeMarble;
    //private static MarketBoard instance = null;

    /*private MarketBoard(){
       initializeMarbleGrid();
       }

    public static MarketBoard getMarketInstance(){
        if (instance == null)
            //synchronized(MarketBoard.class){
            //    if(instance ==null)
            //        instance = new MarketBoard();
            //    }   double-checked locking
            //marketBoard è solo sul server no?
            instance = new MarketBoard();
        return instance;
    }

    public void deleteInstance(){
        instance = null;
    }*/

    public MarketBoard(){
        initializeMarbleGrid();
    }

    /**
     * method called at the start of the game that initializes the marble grid
     */
    public void initializeMarbleGrid() {
        ArrayList<Marble> marbles =  new JsonParser("src/main/resources/marketBoard.json").getMarbles();
        for (int n = 0; n < 3; n++)
            marbleGrid.add(new ArrayList(4));
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
    public ArrayList<Resource> addMarketLine (int line, WhiteConverter card) throws UnusableCardException{

        Resource convertResource;
        try {
            convertResource = card.whichResource();
        }catch(NullPointerException e){
            convertResource = null;
        }

        ArrayList<Resource> resLine = new ArrayList<>(4);
        Marble toConvert;

        for(int i = 0; i<4; i++){
            toConvert = marbleGrid.get(line).get(i);
            if(toConvert.convertMarble(convertResource)!=null)
                resLine.add(toConvert.convertMarble(convertResource));
        }
        System.out.println("resLine: "+resLine);
        Marble tmp = freeMarble;
        freeMarble = marbleGrid.get(line).get(0);

        int i;
        for(i =3 ; i>0; i--)
            marbleGrid.get(line).set(i, marbleGrid.get(line).get(i-1));
        marbleGrid.get(line).set(3, tmp);
        System.out.println("new grid:  "+marbleGrid);

        return resLine;
    }

    /** method to obtain model.resources from the market by column
     * @param col the column of the grid requested
     * @param card eventual leader card passed to obtain bonuses
     * @return arraylist of requested model.resources
     */
    public ArrayList<Resource> addMarketColumn (int col, WhiteConverter card) throws UnusableCardException{

        Resource convertResource;
        try {
             convertResource = card.whichResource();
        }catch(NullPointerException e){
            convertResource = null;
        }
        ArrayList<Resource> resColumn = new ArrayList<>(3);
        Marble toConvert;
        for(int i = 0; i<3; i++){
            toConvert = marbleGrid.get(i).get(col);
            if(toConvert.convertMarble(convertResource)!=null)
                resColumn.add(toConvert.convertMarble(convertResource));
        }
        Marble tmp = freeMarble;
        freeMarble = marbleGrid.get(0).get(col);

        //marbleGrid.get(1).set(col, marbleGrid.get(0).get(col));
        //marbleGrid.get(0).set(col, tmp);
        int i;
        for(i =1 ; i<=2; i++)
          marbleGrid.get(i-1).set(col, marbleGrid.get(i).get(col));
        marbleGrid.get(2).set(col, tmp);
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

    public LightMarketBoard convert(){
        LightMarketBoard market = new LightMarketBoard();
        ArrayList<LightMarble> marketCopy = new ArrayList<>();
        for(int i=0; i<3; i++)
            for(int j =0; j<4; j++)
                marketCopy.add(LightMarble.valueOf(this.marbleGrid.get(i).get(j).toString()));
        marketCopy.add(LightMarble.valueOf(freeMarble.toString()));
        market.setMarketBoard(marketCopy);
        return market;
    }
}

