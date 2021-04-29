package cards;

import resources.Resource;

import java.util.ArrayList;

public class ExtraProd extends LeaderCard{
    private DevelopmentCard requires;
    private static int victoryPoints = 4;
    private Resource input;
    private Resource output = Resource.FAITH;
    private Resource chosenOutput;

    public ExtraProd(int id, boolean en, DevelopmentCard req, Resource input){
        this.id=id;
        this.isEnabled=en;
        this.requires=req;
        this.input=input;

    }

    @Override
    public Resource getRequirement(){
        if(isEnabled())
            return this.input;
        return null;
    }

    public void setChosenOutput(Resource chosenOutput){
        this.chosenOutput = chosenOutput;
    }

    @Override
    public ArrayList<Resource> production(){
        if(isEnabled()) {
            ArrayList products = new ArrayList();
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

}
