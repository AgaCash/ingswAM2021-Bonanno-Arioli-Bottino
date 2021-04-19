package table;

import cards.LeaderCard;
import marbles.*;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import resources.Resource;
import utilities.Parser;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;


/**
 * class that contains and manages the market's marbles
 */
public class MarketBoard {
    private ArrayList<ArrayList<Marble>> marbleGrid = new ArrayList<>(3);
    private Marble freeMarble;

    public Marble instancer(String name) {
        switch (name) {
            case "BLUE" -> {
                return new BlueMarble();
            }
            case "GREY" -> {
                return new GreyMarble();
            }
            case "PURPLE" -> {
                return new PurpleMarble();
            }
            case "RED" -> {
                return new RedMarble();
            }
            case "WHITE" -> {
                return new WhiteMarble();
            }
            case "YELLOW" -> {
                return new YellowMarble();
            }
        }
        return new Marble();
    }

    public void builder(ArrayList<Marble> marbles, String docSource, String tag) throws IOException, ParserConfigurationException, SAXException {
        Parser parser = new Parser();
        NodeList marbleNodes = parser.getByTag(docSource, tag);
        for (int i = 0; i < marbleNodes.getLength(); i++) {
             marbles.add(instancer(parser.convert(marbleNodes.item(i)).getTextContent()));
        }
    }
    
    /**
     * method called at the start of the game that initializes the marble grid
     */
    public void initializeMarbleGrid() {
        ArrayList<Marble> marbles = new ArrayList<>();
        for (int n = 0; n < 4; n++)
            marbleGrid.add(new ArrayList(3));
        try {
            builder(marbles, "src/main/resources/marketBoard.xml", "marble");
            Collections.shuffle(marbles);

            int k =0;
            for(int i=0; i<3; i++)
                for(int j =0; j<4; j++){
                    marbleGrid.get(i).add(j, marbles.get(k));
                    k++;
                }
            freeMarble = marbles.get(k);
        } catch (IOException | ParserConfigurationException | SAXException e) {
            e.printStackTrace();
        }
    }

    /** method to obtain resources from the market by line
     * @param line the line of the grid requested
     * @param card eventual leader card passed to obtain bonuses
     * @return arraylist of requested resources
     */
    public ArrayList<Resource> addMarketLine (int line, LeaderCard card){
        ArrayList<Resource> resLine = new ArrayList<>(4);
        Marble toConvert;

        for(int i = 0; i<4; i++){
            toConvert = marbleGrid.get(line).get(i);
            resLine.add(toConvert.convertMarble(card));
        }
        freeMarble = marbleGrid.get(line).get(3);
        int i;
        for(i =3 ; i>0; i--)
            marbleGrid.get(line).set(i, marbleGrid.get(line).get(i-1));
        marbleGrid.get(line).set(i, freeMarble);
        return resLine;
    }

    /** method to obtain resources from the market by column
     * @param col the column of the grid requested
     * @param card eventual leader card passed to obtain bonuses
     * @return arraylist of requested resources
     */
    public ArrayList<Resource> addMarketColumn (int col, LeaderCard card){
        ArrayList<Resource> resColumn = new ArrayList<>(3);
        Marble toConvert;
        for(int i = 0; i<3; i++){
            toConvert = marbleGrid.get(i).get(col);
            resColumn.add(toConvert.convertMarble(card));
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
}

