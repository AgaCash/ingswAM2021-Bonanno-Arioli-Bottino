package table;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import cards.DevelopmentCard;
import colour.Colour;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import resources.Resource;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

public final class DevelopmentBoard {
    /**
     * Class that load all the development cards from a configuration xml file
     */

    private ArrayList<Deck> decks = new ArrayList<>();  //12 decks

    /**
     * Default constructor that extract the data from xml and create the cards
     */

    public DevelopmentBoard() {
        try {
            String pathToXml = "src/main/resources/developmentCards.xml";
            File file = new File(pathToXml);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);
            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getElementsByTagName("developmentCard");

            Node nodeChild;
            NodeList resourcesChild;
            int tmpId = 0;
            Colour tmpColour = null;
            int tmpLevel = 0;
            int tmpVPoints = 0;
            ArrayList<Resource> tmpCost = new ArrayList<>();
            ArrayList<Resource> tmpProdIn = new ArrayList<>();
            ArrayList<Resource> tmpProdOut = new ArrayList<>();
            int nCardInDeck = 0; //andra' da 1 a 4 in loop
            ArrayList<DevelopmentCard> tmpDeck = new ArrayList<>();


            for (int i = 0; i < nodeList.getLength(); i++) {
                nCardInDeck++;
                tmpCost.clear();
                tmpProdIn.clear();
                tmpProdOut.clear();

                Node cardsNode = nodeList.item(i);
                System.out.println("Node Name :" + cardsNode.getNodeName());
                if (cardsNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element devCardElement = (Element) cardsNode;

                    tmpId = Integer.parseInt(devCardElement.getAttribute("id"));
                    tmpColour = Colour.valueOf(devCardElement.getAttribute("colour"));
                    tmpLevel = Integer.parseInt(devCardElement.getAttribute("level"));
                    tmpVPoints = Integer.parseInt(devCardElement.getAttribute("victoryPoints"));

                    NodeList insideTagChild = cardsNode.getChildNodes();
                    for (int j = 0; j < insideTagChild.getLength(); j++) {
                        Node insideTagNode = insideTagChild.item(j);
                        if (insideTagNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element insideTagElement = (Element) insideTagNode;
                            switch (insideTagElement.getNodeName()) {
                                case "cost" -> {
                                    resourcesChild = insideTagElement.getChildNodes();
                                    for (int k = 0; k < resourcesChild.getLength(); k++) {
                                        Node resourcesTagNode = resourcesChild.item(k);
                                        if (resourcesTagNode.getNodeType() == Node.ELEMENT_NODE) {
                                            Element resourcesTagElement = (Element) resourcesTagNode;
                                            //System.out.println("cost: " + resourcesTagElement.getTextContent());
                                            tmpCost.add(Resource.valueOf(resourcesTagElement.getTextContent()));
                                        }
                                    }
                                }
                                case "prodInput" -> {
                                    resourcesChild = insideTagElement.getChildNodes();
                                    for (int k = 0; k < resourcesChild.getLength(); k++) {
                                        Node resourcesTagNode = resourcesChild.item(k);
                                        if (resourcesTagNode.getNodeType() == Node.ELEMENT_NODE) {
                                            Element resourcesTagElement = (Element) resourcesTagNode;
                                            //System.out.println("pIn: " + resourcesTagElement.getTextContent());
                                            tmpProdIn.add(Resource.valueOf(resourcesTagElement.getTextContent()));
                                        }
                                    }
                                }
                                case "prodOutput" -> {
                                    resourcesChild = insideTagElement.getChildNodes();
                                    for (int k = 0; k < resourcesChild.getLength(); k++) {
                                        Node resourcesTagNode = resourcesChild.item(k);
                                        if (resourcesTagNode.getNodeType() == Node.ELEMENT_NODE) {
                                            Element resourcesTagElement = (Element) resourcesTagNode;
                                            //System.out.println("pOut: " + resourcesTagElement.getTextContent());
                                            tmpProdOut.add(Resource.valueOf(resourcesTagElement.getTextContent()));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                tmpDeck.add(new DevelopmentCard(tmpId, tmpColour, tmpLevel, tmpVPoints, tmpCost, tmpProdIn, tmpProdOut));
                if(nCardInDeck == 4) {
                    nCardInDeck = 0;
                    Deck tmpD = new Deck(tmpDeck);
                    tmpD.shuffleDeck();
                    decks.add(tmpD);
                    tmpDeck.clear();
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

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
        if(getDeck(deckNumber) != null)
            return Objects.requireNonNull(getDeck(deckNumber)).popCard();
        else
            return null;
    }
}

