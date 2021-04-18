import cards.*;
import colour.Colour;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import resources.Resource;
import utilities.Parser;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;

public class LeaderCardCollector{
    /**
     * LeaderCardCollector is the class deputed in instancing and initializing LeaderCard objects representing
     * leader cards. It's called at the starting of the game and return an ArrayList of all leader cards.
     * It reads all the construction parameters from XML files (each for every LeaderCard's subclass)
     * @param leaderCardDeck the ArrayList initialized and instanced
     * @return leaderCardDeck ArrayList
     */
    private ArrayList<LeaderCard> leaderCardDeck = new ArrayList();

    /**
     * discountConstructor() adds Discount objects as item of leaderCardDeck array.
     * It reads the XML file src/main/resources/discount.xml
     */
    public void discountConstructor() {
        //ArrayList of DevelopmentCards required to activate Discount card ability in the game
        ArrayList<DevelopmentCard> requirements = new ArrayList<>();
        //Resource which will be discounted from the buying in the market during the game
        Resource discountResource = null;
        Parser parser = new Parser();
        try {
            NodeList discountNodes = parser.getByTag("src/main/resources/discount.xml", "discount");
            for (int i = 0; i < discountNodes.getLength(); i++) {
                Element discountElement = parser.convert(discountNodes.item(i));
                NodeList requirementsNodes = discountElement.getChildNodes();
                for (int j = 0; j < requirementsNodes.getLength(); j++) {
                    Element reqDevElement = parser.convert(requirementsNodes.item(j));
                    if (reqDevElement.getTagName() == "requirement") {
                        //add Development card in requirements ArrayList
                        requirements.add(new DevelopmentCard(Colour.valueOf(reqDevElement.getAttribute("colour")),
                                Integer.parseInt(reqDevElement.getAttribute("level"))));
                    }
                    //if is a <resource>
                    else if (reqDevElement.getTagName() == "resource") {
                        //parse the Resource in discountResource
                        discountResource = Resource.valueOf(reqDevElement.getTextContent());
                    }

                }
                leaderCardDeck.add(new Discount(Integer.parseInt(discountElement.getAttribute("id")),
                        Boolean.parseBoolean((discountElement.getAttribute("isEnabled"))),
                        requirements,
                        discountResource));
            }
        }
        //exceptions
        catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    /**
     * extraDepotConstructor() adds ExtraDepot objects as item of leaderCardDeck array.
     * It reads the XML file src/main/resources/extraDepot.xml
     */
    public void extraDepotConstructor() {
        Parser parser = new Parser();
        //ArrayList of DevelopmentCard required to activate Discount card ability in the game
        ArrayList<Resource> requirements = new ArrayList<>();
        //ArrayList of Resource which have extra space in the WareHouse
        ArrayList<Resource> discountResources = new ArrayList<>();
        try {
            NodeList extraDepotNodes = parser.getByTag("src/main/resources/extraDepot.xml", "extraDepot");
            for (int i = 0; i < extraDepotNodes.getLength(); i++) {
                    Element extraDepotElement = parser.convert(extraDepotNodes.item(i));
                    //list of all <extraDepot> node child (<requiredResource> and <extraDepotResource>
                    NodeList requirementsNodes = extraDepotElement.getChildNodes();
                    for (int j = 0; j < requirementsNodes.getLength(); j++) {
                            Element reqDevElement = parser.convert(requirementsNodes.item(j));
                            if (reqDevElement.getTagName() == "requiredResource") {
                                requirements.add(Resource.valueOf(reqDevElement.getTextContent()));
                            } else if (reqDevElement.getTagName() == "extraDepotResource") {
                                discountResources.add(Resource.valueOf(reqDevElement.getTextContent()));
                            }
                    }
                    leaderCardDeck.add(new ExtraDepot(Integer.parseInt(extraDepotElement.getAttribute("id")),
                            Boolean.parseBoolean((extraDepotElement.getAttribute("isEnabled"))),
                            requirements,
                            discountResources));

                }

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }

    /**
     * whiteConverterConstructor() adds WhiteConverter objects as item of leaderCardDeck array.
     * It reads the XML file src/main/resources/whiteConverter.xml
     */
    public void whiteConverterConstructor() {
        Parser parser = new Parser();
        //ArrayList of DevelopmentCard required to activate Discount card ability in the game
        ArrayList<DevelopmentCard> requirements = new ArrayList<>();
        //Resource in which could be converted WhiteMarble
        Resource convertResource = null;
        try {
            NodeList whiteConverterNodes = parser.getByTag("src/main/resources/whiteConverter.xml", "whiteConverter");
            for (int i = 0; i < whiteConverterNodes.getLength(); i++) {
                    Element whiteConverterElement = parser.convert(whiteConverterNodes.item(i));
                    //list of all <whiteConverter> node child (<requirement> and <resource>)
                    NodeList requirementsNodes = whiteConverterElement.getChildNodes();
                    for (int j = 0; j < requirementsNodes.getLength(); j++) {
                            Element reqDevElement = parser.convert(requirementsNodes.item(j));
                            if(reqDevElement.getTagName() == "requirement"){
                                requirements.add(new DevelopmentCard(Colour.valueOf(reqDevElement.getAttribute("colour")),
                                        Integer.parseInt(reqDevElement.getAttribute("level"))));
                            }
                            else if(reqDevElement.getTagName()=="resource"){
                                convertResource = Resource.valueOf(reqDevElement.getTextContent());
                            }

                    }
                    leaderCardDeck.add(new WhiteConverter(Integer.parseInt(whiteConverterElement.getAttribute("id")),
                            Boolean.parseBoolean((whiteConverterElement.getAttribute("isEnabled"))),
                            requirements,
                            convertResource));


            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }

    /**
     * extraProdConstructor() adds ExtraProd objects as item of leaderCardDeck array.
     * It reads the XML file src/main/resources/extraProd.xml
     */
    public void extraProdConstructor() {
        Parser parser = new Parser();
        //DevelopmentCard required to activate Discount card ability in the game
        DevelopmentCard requirement = null;
        //Resource which could be produced in surplus
        Resource prodResource = null;
        try {
            //list of all nodes by tag <extraProd>
            NodeList extraProdNodes = parser.getByTag("src/main/resources/extraProd.xml", "extraProd");
            for (int i = 0; i < extraProdNodes.getLength(); i++) {
                    Element extraProdElement = parser.convert(extraProdNodes.item(i));
                    //list of all <extraProd> node child (<requirement> and <prodResource>)
                    NodeList requirementsNodes = extraProdElement.getChildNodes();
                    for (int j = 0; j < requirementsNodes.getLength(); j++) {
                            Element reqDevElement = parser.convert(requirementsNodes.item(j));
                            if(reqDevElement.getTagName()=="requirement"){
                                requirement = new DevelopmentCard(Colour.valueOf(reqDevElement.getAttribute("colour")),
                                        Integer.parseInt(reqDevElement.getAttribute("level")));
                            }
                            else if(reqDevElement.getTagName()=="prodResource"){
                                prodResource = Resource.valueOf(reqDevElement.getTextContent());
                            }
                    }
                    leaderCardDeck.add(new ExtraProd(Integer.parseInt(extraProdElement.getAttribute("id")),
                            Boolean.parseBoolean((extraProdElement.getAttribute("isEnabled"))),
                            requirement,
                            prodResource));


            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }

    /**
     * build() call all the LeaderCard instancing method
     * @return leaderCardDeck ArrayList
     */

    public ArrayList<LeaderCard> build() {
        discountConstructor();
        extraDepotConstructor();
        whiteConverterConstructor();
        extraProdConstructor();
        return this.leaderCardDeck;
    }
}