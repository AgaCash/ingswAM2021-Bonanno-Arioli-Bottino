package model.cards;

import clientModel.cards.LightDevelopmentCard;
import clientModel.cards.LightExtraProd;
import clientModel.cards.LightLeaderCard;
import clientModel.resources.LightResource;
import exceptions.UnusableCardException;
import model.resources.Resource;

import java.util.ArrayList;

public class ExtraProd extends LeaderCard{
    private ArrayList<DevelopmentCard> requires;
    private static int victoryPoints = 4;
    private Resource input;
    private Resource output = Resource.FAITH;
    private Resource chosenOutput;

    public ExtraProd(int id, boolean en, ArrayList<DevelopmentCard> req, Resource input){
        this.id=id;
        this.isEnabled=en;
        this.requires=req;
        this.input=input;
        super.type = LeaderCardType.EXTRAPROD;

    }

    @Override
    public Resource getExtraProdInput() throws UnusableCardException {
        if(!isEnabled())
            throw new UnusableCardException("Extra Prod card is not active!");
        return this.input;
    }

    public void setChosenOutput(Resource chosenOutput){
        this.chosenOutput = chosenOutput;
    }

    @Override
    public ArrayList<Resource> production(){
        if(isEnabled()) {
            ArrayList<Resource> products = new ArrayList<>();
            products.add(this.output);
            products.add(chosenOutput);
            return products;
        }
        return null;
    }

    @Override
    public boolean isExtraProd(){
        return true;
    }

    @Override
    public ArrayList<DevelopmentCard> getRequiredCards(){
        return (ArrayList<DevelopmentCard>) this.requires.clone();
    }

    /**
     * for tests
     * @return
     */
    @Override
    public String toString(){
        String s = "\nEXTRA PROD";
        s+= "\nID: "+id;
        s+= "\nRequires: ";
        s+="\n\t Input: "+input;
        s+="\nProduce: \n\t"+output+" and a chosen resource";
        s+= "\nVictory Points: "+victoryPoints;
        s+= "\nIs Enabled: "+ isEnabled;
        return s;
    }

    @Override
    public LightLeaderCard convert(){
        ArrayList<LightDevelopmentCard> requires = new ArrayList<>();
        this.requires.forEach(element -> requires.add(element.convert()));
        System.out.println(this.input);
        LightResource input = LightResource.valueOf(this.input.toString());

        return new LightExtraProd(this.id,
                this.isEnabled,
                requires,
               input);
    }

}
