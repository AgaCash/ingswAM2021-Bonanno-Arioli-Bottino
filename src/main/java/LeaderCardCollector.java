import cards.*;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import resources.Resource;
import utilities.Instancer;
import utilities.Parser;

import javax.xml.parsers.DocumentBuilderFactory;
import java.util.ArrayList;

public class LeaderCardCollector {
    /**
     * LeaderCardCollector is the class deputed in instancing and initializing LeaderCard objects representing
     * leader cards. It's called at the starting of the game and return an ArrayList of all leader cards.
     * It reads all the construction parameters from XML files (each for every LeaderCard's subclass)
     *
     * @param leaderCardDeck the ArrayList initialized and instanced
     * @return leaderCardDeck ArrayList
     */
    private ArrayList<LeaderCard> leaderCardDeck = new ArrayList();

    private void constructor(String sourceFile, String tag) {
        //document BuilderFactory
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            Parser parser = new Parser();

            NodeList nodes = parser.getByTag(sourceFile, tag);
            for (int i = 0; i < nodes.getLength(); i++) {
                    //Element containing the node
                    Element element = parser.convert(nodes.item(i));
                    //list of all <discount> node child (<requirement> and <resource>)
                    NodeList requirementsNodes = element.getChildNodes();
                    Instancer inst = new Instancer();
                    if(tag == "discount") {
                            ArrayList<DevelopmentCard> requirements = inst.instance(requirementsNodes);
                            Resource discountResource = inst.instance(requirementsNodes, "resource").get(0);
                            //instancing the single Discount card
                            leaderCardDeck.add(new Discount(Integer.parseInt(element.getAttribute("id")),
                                    Boolean.parseBoolean((element.getAttribute("isEnabled"))),
                                    requirements,
                                    discountResource));
                        }
                        else if (tag == "extraDepot" ) {
                            ArrayList<Resource> requirementResources = inst.instance(requirementsNodes, "requiredResource");
                            ArrayList<Resource> discountResources = inst.instance(requirementsNodes, "extraDepotResource");
                            leaderCardDeck.add(new ExtraDepot(Integer.parseInt(element.getAttribute("id")),
                                    Boolean.parseBoolean((element.getAttribute("isEnabled"))),
                                    requirementResources,
                                    discountResources));
                        }
                        else if(tag == "whiteConverter" ) {
                            ArrayList<DevelopmentCard> requirements = inst.instance(requirementsNodes);
                            Resource convertResource = inst.instance(requirementsNodes, "resource").get(0);
                            leaderCardDeck.add(new WhiteConverter(Integer.parseInt(element.getAttribute("id")),
                                    Boolean.parseBoolean((element.getAttribute("isEnabled"))),
                                    requirements,
                                    convertResource));

                        }
                        else if( tag == "extraProd" ){
                            DevelopmentCard requirement = inst.instance(requirementsNodes).get(0);
                            Resource prodResource = inst.instance(requirementsNodes, "prodResource").get(0);
                            leaderCardDeck.add(new ExtraProd(Integer.parseInt(element.getAttribute("id")),
                                    Boolean.parseBoolean((element.getAttribute("isEnabled"))),
                                    requirement,
                                    prodResource));
                        }



                }

        }
        //exceptions
        catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * build() call all the LeaderCard instancing method
     *
     * @return leaderCardDeck ArrayList
     */

    public ArrayList<LeaderCard> build() {
        constructor("src/main/resources/discount.xml", "discount");
        constructor("src/main/resources/extraDepot.xml", "extraDepot");
        constructor("src/main/resources/whiteConverter.xml", "whiteConverter");
        constructor("src/main/resources/extraProd.xml", "extraProd");
        return this.leaderCardDeck;
    }

    /*public static void main(String[] args) {
        LeaderCardCollector collector = new LeaderCardCollector();
        ArrayList<LeaderCard> list = new ArrayList<>();

        list = collector.build();
        for(LeaderCard c : list)
            System.out.println(c.isEnabled()+" "+c.getClass().getSimpleName());
    }*/
}