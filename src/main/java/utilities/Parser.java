package utilities;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class Parser {
    private DocumentBuilderFactory factory;

    public Parser() {
       factory = DocumentBuilderFactory.newInstance();

    }

    public Element convert(Node node) {
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element tagElement = (Element) node;
            return tagElement;
        }
        return null;
    }

    public NodeList getByTag(String sourceFile, String tag) throws IOException, SAXException, ParserConfigurationException {
        DocumentBuilder builder = factory.newDocumentBuilder();
        //object parsing the XML
        Document discountXML = builder.parse(sourceFile);
        //list of all nodes by tag <discount>
        return discountXML.getElementsByTagName(tag);
    }


}