package utilities;

import cards.DevelopmentCard;
import colour.Colour;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import resources.Resource;

import java.util.ArrayList;

public class Instancer {

    public ArrayList<DevelopmentCard> instance(NodeList requirementsNodes){
        ArrayList<DevelopmentCard> requirements = new ArrayList<>();
        Parser parser = new Parser();
        for (int j = 0; j < requirementsNodes.getLength(); j++) {
            //each single node by tag <requirement> and <resource>
            Node reqNode = requirementsNodes.item(j);
            if (reqNode.getNodeType() == Node.ELEMENT_NODE) {
                //Element containing the node
                Element reqDevElement = parser.convert(requirementsNodes.item(j));
                //if is a <requirement> node
                if (reqDevElement.getTagName() == "requirement") {
                    //add Development card in requirements ArrayList
                    requirements.add(new DevelopmentCard(Colour.valueOf(reqDevElement.getAttribute("colour")),
                            Integer.parseInt(reqDevElement.getAttribute("level"))));
                }
            }
        }
        return requirements;
    }

    public ArrayList<Resource> instance(NodeList requirementsNodes, String tag){
        ArrayList<Resource> resources = new ArrayList<>();
        for (int j = 0; j < requirementsNodes.getLength(); j++) {
            //each single node by tag <requirement> and <resource>
            Node reqNode = requirementsNodes.item(j);
            if (reqNode.getNodeType() == Node.ELEMENT_NODE) {
                //Element containing the node
                Element reqDevElement = (Element) reqNode;
                //if is a <resource>
                if (reqDevElement.getTagName().equals(tag)) {
                    resources.add(Resource.valueOf(reqDevElement.getTextContent()));
                }

            }
        }
        return resources;
    }
}
