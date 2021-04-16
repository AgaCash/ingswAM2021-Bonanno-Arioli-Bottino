import cards.LeaderCard;
import marbles.*;
import resources.Resource;

import java.util.ArrayList;
import java.util.Collections;


/**
 * class that contains and manages the market's marbles
 */
public class MarketBoard {
    private ArrayList<ArrayList<Marble>> marbleGrid = new ArrayList<>(3);
    private Marble freeMarble;
    private BlueMarble blue1 = new BlueMarble();
    private BlueMarble blue2 = new BlueMarble();
    private PurpleMarble purple1 = new PurpleMarble();
    private PurpleMarble purple2 = new PurpleMarble();
    private GreyMarble grey1 = new GreyMarble();
    private GreyMarble grey2 = new GreyMarble();
    private YellowMarble yellow1 = new YellowMarble();
    private YellowMarble yellow2  = new YellowMarble();
    private WhiteMarble white1 = new WhiteMarble();
    private WhiteMarble white2 = new WhiteMarble();
    private WhiteMarble white3 = new WhiteMarble();
    private WhiteMarble white4 = new WhiteMarble();
    private RedMarble red = new RedMarble();


    /**
     * method called at the start of the game that initializes the marble grid
     */
    public void initializeMarbleGrid() {
        for (int n = 0; n < 4; n++)
            marbleGrid.add(new ArrayList(3));

        ArrayList<Marble> mar = new ArrayList<>(13);
        mar.add(blue1);
        mar.add(blue2);
        mar.add(grey1);
        mar.add(grey2);
        mar.add(purple1);
        mar.add(purple2);
        mar.add(yellow1);
        mar.add(yellow2);
        mar.add(red);
        mar.add(white1);
        mar.add(white2);
        mar.add(white3);
        mar.add(white4);

        Collections.shuffle(mar);

        freeMarble = mar.get(12);

                marbleGrid.get(0).add(0, mar.get(0));
                marbleGrid.get(0).add(1, mar.get(1));
                marbleGrid.get(0).add(2, mar.get(2));
                marbleGrid.get(0).add(3, mar.get(3));
                marbleGrid.get(1).add(0, mar.get(4));
                marbleGrid.get(1).add(1, mar.get(5));
                marbleGrid.get(1).add(2, mar.get(6));
                marbleGrid.get(1).add(3, mar.get(7));
                marbleGrid.get(2).add(0, mar.get(8));
                marbleGrid.get(2).add(1, mar.get(9));
                marbleGrid.get(2).add(2, mar.get(10));
                marbleGrid.get(2).add(3, mar.get(11));
    }

    /** method to obtain resources from the market by line
     * @param line the line of the grid requested
     * @param card eventual leader card passed to obtain bonuses
     * @return arraylist of requested resources
     */
    public ArrayList<Resource> addMarketLine (int line, LeaderCard card){
        ArrayList<Resource> resLine = new ArrayList<>(4);
        Marble m;
        Marble toConvert;
        m = freeMarble;
        for(int i = 0; i<4; i++){
            toConvert = marbleGrid.get(line).get(i);
            resLine.add(toConvert.convertMarble(card));
        }
        freeMarble = marbleGrid.get(line).get(3);
        marbleGrid.get(line).set(3, marbleGrid.get(line).get(2));
        marbleGrid.get(line).set(2, marbleGrid.get(line).get(1));
        marbleGrid.get(line).set(1, marbleGrid.get(line).get(0));
        marbleGrid.get(line).set(0, m);
        return resLine;
    }

    /** method to obtain resources from the market by column
     * @param col the column of the grid requested
     * @param card eventual leader card passed to obtain bonuses
     * @return arraylist of requested resources
     */
    public ArrayList<Resource> addMarketColumn (int col, LeaderCard card){
        ArrayList<Resource> resColumn = new ArrayList<>(3);
        Marble m;
        Marble toConvert;
        m = freeMarble;
        for(int i = 0; i<3; i++){
            toConvert = marbleGrid.get(i).get(col);
            resColumn.add(toConvert.convertMarble(card));
        }
        freeMarble = marbleGrid.get(2).get(col);
        marbleGrid.get(1).set(col, marbleGrid.get(0).get(col));
        marbleGrid.get(0).set(col, m);
        return resColumn;
    }

    /** getter method for the market board
     * @param whichOneLine arraylist's line requested for the get
     * @param whichOneCol arraylist's column requested for the get
     * @return
     */
    public Marble getMarble (int whichOneLine, int whichOneCol){
        Marble mar;
        mar = marbleGrid.get(whichOneLine).get(whichOneCol);
        return mar;
    }
}

