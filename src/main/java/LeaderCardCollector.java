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
    public ArrayList<LeaderCard> leaderCardDeck = new ArrayList();

    public void discountConstructor() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        ArrayList<DevelopmentCard> requirements = new ArrayList<>();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document discountXML = builder.parse("src/main/resources/discount.xml");
            NodeList discountNodes = discountXML.getElementsByTagName("discount");
            for (int i = 0; i < discountNodes.getLength(); i++) {
                Node discountNode = discountNodes.item(i);
                if (discountNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element discountElement = (Element) discountNode;
                    NodeList requirementsNodes = discountElement.getChildNodes();
                    for (int j = 0; j < requirementsNodes.getLength(); j++) {
                        Node reqNode = requirementsNodes.item(j);
                        if (reqNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element reqDevElement = (Element) reqNode;
                            requirements.add(new DevelopmentCard(Colour.valueOf(reqDevElement.getAttribute("colour")),
                                    Integer.parseInt(reqDevElement.getAttribute("level"))));
                        }
                    }
                    leaderCardDeck.add(new Discount(Integer.parseInt(discountElement.getAttribute("id")),
                            Boolean.parseBoolean((discountElement.getAttribute("isEnabled"))),
                            requirements, Resource.valueOf(discountElement.getAttribute("resource"))));

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

    public void extraDepotConstructor() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        ArrayList<Resource> requirements = new ArrayList<>();
        ArrayList<Resource> discountResources = new ArrayList<>();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document extraDepotXML = builder.parse("src/main/resources/extraDepot.xml");
            NodeList extraDepotNodes = extraDepotXML.getElementsByTagName("extraDepot");
            for (int i = 0; i < extraDepotNodes.getLength(); i++) {
                Node extraDepotNode = extraDepotNodes.item(i);
                if (extraDepotNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element extraDepotElement = (Element) extraDepotNode;
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

    public void whiteConverterConstructor() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        ArrayList<DevelopmentCard> requirements = new ArrayList<>();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document whiteConverterXML = builder.parse("src/main/resources/whiteConverter.xml");
            NodeList whiteConverterNodes = whiteConverterXML.getElementsByTagName("whiteConverter");
            for (int i = 0; i < whiteConverterNodes.getLength(); i++) {
                Node whiteConverterNode = whiteConverterNodes.item(i);
                if (whiteConverterNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element whiteConverterElement = (Element) whiteConverterNode;
                    NodeList requirementsNodes = whiteConverterElement.getChildNodes();
                    for (int j = 0; j < requirementsNodes.getLength(); j++) {
                        Node reqNode = requirementsNodes.item(j);
                        if (reqNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element reqDevElement = (Element) reqNode;
                            requirements.add(new DevelopmentCard(Colour.valueOf(reqDevElement.getAttribute("colour")),
                                    Integer.parseInt(reqDevElement.getAttribute("level"))));
                        }
                    }
                    leaderCardDeck.add(new WhiteConverter(Integer.parseInt(whiteConverterElement.getAttribute("id")),
                            Boolean.parseBoolean((whiteConverterElement.getAttribute("isEnabled"))),
                            requirements,
                            Resource.valueOf(whiteConverterElement.getAttribute("resource"))));

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

    public void extraProdConstructor() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DevelopmentCard requirement = null;
        Resource prodResource = null;
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document extraProdXML = builder.parse("src/main/resources/extraProd.xml");
            NodeList extraProdNodes = extraProdXML.getElementsByTagName("extraProd");
            for (int i = 0; i < extraProdNodes.getLength(); i++) {
                Node extraProdNode = extraProdNodes.item(i);
                if (extraProdNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element extraProdElement = (Element) extraProdNode;
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

    public ArrayList<LeaderCard> build() {
        discountConstructor();
        extraDepotConstructor();
        whiteConverterConstructor();
        extraProdConstructor();
        return this.leaderCardDeck;
    }
}