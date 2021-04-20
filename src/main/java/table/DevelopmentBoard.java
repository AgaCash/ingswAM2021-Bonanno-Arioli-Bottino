package table;


import cards.DevelopmentCard;
import utilities.JsonParser;
import java.util.ArrayList;

public final class DevelopmentBoard {
    /**
     * Class that load all the development cards from a configuration Json file
     */

    private ArrayList<Deck> decks = new ArrayList<>();  //12 decks

    /**
     * Default constructor that extract the data from Json and create the cards
     */
    public DevelopmentBoard(){
        ArrayList<DevelopmentCard> tmpCards =
                new JsonParser("src/main/resources/developmentCards.json").getDevelopmentCards();
        ArrayList<DevelopmentCard> tmpDeck = new ArrayList<>();
        int i = 1;
        for (DevelopmentCard card:tmpCards) {
            tmpDeck.add(card);
            if(i == 4){
                decks.add(new Deck((ArrayList<DevelopmentCard>) tmpDeck.clone()));
                tmpDeck.clear();
                i = 0;
            }
            i++;
        }
    }
  /*
    public DevelopmentBoard() throws NullPointerException{
        try {
            Parser parser = new Parser();
            String pathToXml = "src/main/resources/developmentCards.xml";
            NodeList nodeList = parser.getByTag(pathToXml, "developmentCard");
            Node nodeChild;
            NodeList resourcesChild;
            int tmpId;
            Colour tmpColour;
            int tmpLevel;
            int tmpVPoints;
            ArrayList<Resource> tmpCost = new ArrayList<>();
            ArrayList<Resource> tmpProdIn = new ArrayList<>();
            ArrayList<Resource> tmpProdOut = new ArrayList<>();
            int nCardInDeck = 0; //andra' da 1 a 4 in loop
            ArrayList<DevelopmentCard> tmpDeck = new ArrayList<>();

            System.out.println(nodeList.getLength());
            for (int i = 0; i < nodeList.getLength(); i++) {
                nCardInDeck++;
                tmpCost.clear();
                tmpProdIn.clear();
                tmpProdOut.clear();

                Element devCardElement = parser.convert(nodeList.item(i));
                if(devCardElement != null){
                    tmpId = Integer.parseInt(devCardElement.getAttribute("id"));
                    tmpColour = Colour.valueOf(devCardElement.getAttribute("colour"));
                    tmpLevel = Integer.parseInt(devCardElement.getAttribute("level"));
                    tmpVPoints = Integer.parseInt(devCardElement.getAttribute("victoryPoints"));

                    NodeList insideTagChild = nodeList.item(i).getChildNodes();
                    for (int j = 0; j < insideTagChild.getLength(); j++) {
                        Element insideTagElement = parser.convert(insideTagChild.item(j));
                        if(insideTagElement!=null){
                            switch (insideTagElement.getNodeName()) {
                                case "cost" -> {
                                    resourcesChild = insideTagElement.getChildNodes();
                                    for (int k = 0; k < resourcesChild.getLength(); k++) {
                                        Element resourcesTagElement = parser.convert(resourcesChild.item(k));
                                        if(resourcesTagElement != null){
                                            tmpCost.add(Resource.valueOf(resourcesTagElement.getTextContent()));
                                        }
                                    }
                                }
                                case "prodInput" -> {
                                    resourcesChild = insideTagElement.getChildNodes();
                                    for (int k = 0; k < resourcesChild.getLength(); k++) {
                                        Element resourcesTagElement = parser.convert(resourcesChild.item(k));
                                        if(resourcesTagElement != null){
                                            tmpProdIn.add(Resource.valueOf(resourcesTagElement.getTextContent()));
                                        }
                                    }
                                }
                                case "prodOutput" -> {
                                    resourcesChild = insideTagElement.getChildNodes();
                                    for (int k = 0; k < resourcesChild.getLength(); k++) {
                                        Element resourcesTagElement = parser.convert(resourcesChild.item(k));
                                        if(resourcesTagElement != null){
                                            tmpProdOut.add(Resource.valueOf(resourcesTagElement.getTextContent()));
                                        }
                                    }
                                }
                            }
                        }
                    }
                    tmpDeck.add(new DevelopmentCard(tmpId, tmpColour, tmpLevel, tmpVPoints, tmpCost, tmpProdIn, tmpProdOut));
                    if(nCardInDeck == 4) {
                        nCardInDeck = 0;
                        Deck tmpD = new Deck( (ArrayList<DevelopmentCard>) tmpDeck.clone() );
                        tmpD.shuffleDeck();
                        decks.add(tmpD);
                        tmpDeck.clear();
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
*/

    /**
     * Method getter that returns the reference to selected deck
     * @param deckNumber index of the deck (0-11)
     * @return the reference of the selected deck
     */
    public Deck getDeck(int deckNumber){
        if(deckNumber<0 || deckNumber >decks.size()-1)
            return null;
        else
            return decks.get(deckNumber);
    }

    /**
     * Method that pop the first card of the selected deck
     * @param deckNumber index of the deck (0-11)
     * @return the card on the top of the selected deck
     */

    public DevelopmentCard popCardFromDeck(int deckNumber){
        Deck d = this.getDeck(deckNumber);
        if(d == null)
            return null;
        else
            return d.popCard();
    }

}

