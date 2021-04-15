import cards.*;
import resources.*;
import colour.*;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
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
        //document BuilderFactory
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        //ArrayList of DevelopmentCards required to activate Discount card ability in the game
        ArrayList<DevelopmentCard> requirements = new ArrayList<>();
        //Resource which will be discounted from the buying in the market during the game
        Resource discountResource = null;
        try {
            //document Builder
            DocumentBuilder builder = factory.newDocumentBuilder();
            //object parsing the XML
            Document discountXML = builder.parse("src/main/resources/discount.xml");
            //list of all nodes by tag <discount>
            NodeList discountNodes = discountXML.getElementsByTagName("discount");
            for (int i = 0; i < discountNodes.getLength(); i++) {
                //each single node by tag <discount>
                Node discountNode = discountNodes.item(i);
                if (discountNode.getNodeType() == Node.ELEMENT_NODE) {
                    //Element containing the node
                    Element discountElement = (Element) discountNode;
                    //list of all <discount> node child (<requirement> and <resource>)
                    NodeList requirementsNodes = discountElement.getChildNodes();
                    for (int j = 0; j < requirementsNodes.getLength(); j++) {
                        //each single node by tag <requirement> and <resource>
                        Node reqNode = requirementsNodes.item(j);
                        if (reqNode.getNodeType() == Node.ELEMENT_NODE) {
                            //Element containing the node
                            Element reqDevElement = (Element) reqNode;
                            //if is a <requirement> node
                            if(reqDevElement.getTagName() == "requirement"){
                                //add Development card in requirements ArrayList
                                requirements.add(new DevelopmentCard(Colour.valueOf(reqDevElement.getAttribute("colour")),
                                        Integer.parseInt(reqDevElement.getAttribute("level"))));
                            }
                            //if is a <resource>
                            else if(reqDevElement.getTagName()=="resource"){
                                //parse the Resource in dicountResource
                                discountResource = Resource.valueOf(reqDevElement.getTextContent());
                            }

                        }
                    }
                    //instancing the single Discount card
                    leaderCardDeck.add(new Discount(Integer.parseInt(discountElement.getAttribute("id")),
                            Boolean.parseBoolean((discountElement.getAttribute("isEnabled"))),
                            requirements,
                            discountResource));

                }
            }
        }
        //exceptions
        catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }

    /**
     * extraDepotConstructor() adds ExtraDepot objects as item of leaderCardDeck array.
     * It reads the XML file src/main/resources/extraDepot.xml
     */
    public void extraDepotConstructor() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        //ArrayList of DevelopmentCard required to activate Discount card ability in the game
        ArrayList<Resource> requirements = new ArrayList<>();
        //ArrayList of Resource which have extra space in the WareHouse
        ArrayList<Resource> discountResources = new ArrayList<>();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document extraDepotXML = builder.parse("src/main/resources/extraDepot.xml");
            //list of all nodes by tag <extraDepot>
            NodeList extraDepotNodes = extraDepotXML.getElementsByTagName("extraDepot");
            for (int i = 0; i < extraDepotNodes.getLength(); i++) {
                Node extraDepotNode = extraDepotNodes.item(i);
                if (extraDepotNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element extraDepotElement = (Element) extraDepotNode;
                    //list of all <extraDepot> node child (<requiredResource> and <extraDepotResource>
                    NodeList requirementsNodes = extraDepotNode.getChildNodes();
                    for (int j = 0; j < requirementsNodes.getLength(); j++) {
                        Node reqNode = requirementsNodes.item(j);
                        if (reqNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element reqDevElement = (Element) reqNode;
                            if (reqDevElement.getTagName() == "requiredResource") {
                                requirements.add(Resource.valueOf(reqDevElement.getTextContent()));
                            } else if (reqDevElement.getTagName() == "extraDepotResource") {
                                discountResources.add(Resource.valueOf(reqDevElement.getTextContent()));
                            }
                        }
                    }
                    leaderCardDeck.add(new ExtraDepot(Integer.parseInt(extraDepotElement.getAttribute("id")),
                            Boolean.parseBoolean((extraDepotElement.getAttribute("isEnabled"))),
                            requirements,
                            discountResources));

                }
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
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        //ArrayList of DevelopmentCard required to activate Discount card ability in the game
        ArrayList<DevelopmentCard> requirements = new ArrayList<>();
        //Resource in which could be converted WhiteMarble
        Resource convertResource = null;
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document whiteConverterXML = builder.parse("src/main/resources/whiteConverter.xml");
            //list of all nodes by tag <whiteConverter>
            NodeList whiteConverterNodes = whiteConverterXML.getElementsByTagName("whiteConverter");
            for (int i = 0; i < whiteConverterNodes.getLength(); i++) {
                Node whiteConverterNode = whiteConverterNodes.item(i);
                if (whiteConverterNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element whiteConverterElement = (Element) whiteConverterNode;
                    //list of all <whiteConverter> node child (<requirement> and <resource>)
                    NodeList requirementsNodes = whiteConverterElement.getChildNodes();
                    for (int j = 0; j < requirementsNodes.getLength(); j++) {
                        Node reqNode = requirementsNodes.item(j);
                        if (reqNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element reqDevElement = (Element) reqNode;
                            if(reqDevElement.getTagName() == "requirement"){
                                requirements.add(new DevelopmentCard(Colour.valueOf(reqDevElement.getAttribute("colour")),
                                        Integer.parseInt(reqDevElement.getAttribute("level"))));
                            }
                            else if(reqDevElement.getTagName()=="resource"){
                                convertResource = Resource.valueOf(reqDevElement.getTextContent());
                            }


                        }
                    }
                    leaderCardDeck.add(new WhiteConverter(Integer.parseInt(whiteConverterElement.getAttribute("id")),
                            Boolean.parseBoolean((whiteConverterElement.getAttribute("isEnabled"))),
                            requirements,
                            convertResource));

                }
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
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        //DevelopmentCard required to activate Discount card ability in the game
        DevelopmentCard requirement = null;
        //Resource which could be produced in surplus
        Resource prodResource = null;
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document extraProdXML = builder.parse("src/main/resources/extraProd.xml");
            //list of all nodes by tag <extraProd>
            NodeList extraProdNodes = extraProdXML.getElementsByTagName("extraProd");
            for (int i = 0; i < extraProdNodes.getLength(); i++) {
                Node extraProdNode = extraProdNodes.item(i);
                if (extraProdNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element extraProdElement = (Element) extraProdNode;
                    //list of all <extraProd> node child (<requirement> and <prodResource>)
                    NodeList requirementsNodes = extraProdNode.getChildNodes();
                    for (int j = 0; j < requirementsNodes.getLength(); j++) {
                        Node reqNode = requirementsNodes.item(j);
                        if (reqNode.getNodeType() == Node.ELEMENT_NODE){
                            Element reqDevElement = (Element) reqNode;
                            if(reqDevElement.getTagName()=="requirement"){
                                requirement = new DevelopmentCard(Colour.valueOf(reqDevElement.getAttribute("colour")),
                                        Integer.parseInt(reqDevElement.getAttribute("level")));
                            }
                            else if(reqDevElement.getTagName()=="prodResource"){
                                prodResource = Resource.valueOf(reqDevElement.getTextContent());
                            }
                        }
                    }
                    leaderCardDeck.add(new ExtraProd(Integer.parseInt(extraProdElement.getAttribute("id")),
                            Boolean.parseBoolean((extraProdElement.getAttribute("isEnabled"))),
                            requirement,
                            prodResource));

                }
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