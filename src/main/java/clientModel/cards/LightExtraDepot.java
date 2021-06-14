package clientModel.cards;

import clientModel.colour.LightColour;
import clientModel.resources.LightResource;

import java.util.ArrayList;
import java.util.TreeMap;

public class LightExtraDepot extends LightLeaderCard {
    private ArrayList<LightResource> requiredResource;
    private static int victoryPoints = 3;
    private ArrayList<LightResource> extraDepotResource;
    private TreeMap<LightResource, Integer> extraWarehouseDepot = new TreeMap<>();

    public LightExtraDepot(int id, boolean en, ArrayList<LightResource> req, ArrayList<LightResource> extra){
        this.id = id;
        this.isEnabled = en;
        this.requiredResource = req;
        this.extraDepotResource = extra;
        for(LightResource ptr : extraDepotResource)
            extraWarehouseDepot.put(ptr, 0);
        super.type = LightLeaderCardType.EXTRADEPOT;
    }
/*
    @Override
    public boolean addResource(LightResource tmp){
        if(isEnabled())
            if(extraWarehouseDepot.containsKey(tmp)) {
                int dim = extraWarehouseDepot.get(tmp);
                if (dim < extraDepotResource.size()) {
                    extraWarehouseDepot.put(tmp, dim+1);
                    return true;
                }
            }
        return false;
    }

    @Override
    public boolean removeResource(LightResource tmp){
        if(isEnabled())
            if(extraWarehouseDepot.containsKey(tmp)){
                int dim = extraWarehouseDepot.get(tmp);
                if(dim > 0){
                    extraWarehouseDepot.put(tmp, dim-1);
                    return true;
                }
            }
        return false;
    }


*/
    @Override
    public boolean isExtraDepot(){
        return true;
    }
/*
    @Override
    public ArrayList<LightResource> getRequiredResources(){
        return this.requiredResource;
    }*/
    @Override
    public String toString(){
        String s = new String();
        LightColour colour ;
        if(!isEnabled)
            colour = LightColour.RED;
        else
            colour =LightColour.GREEN;
        s += colour.toString()+ "\n______________________";
        s+=     "\n|      EXTRA DEPOT     |";
        s+=     "\n|Requires:             |\n\t";
        for (LightResource r:requiredResource) {
            s+=r.toColoredString()+ " ";
        }
        s+= colour.toString()+  "\n|Extra resources:      |\n\t";
        for(LightResource r: extraDepotResource ){
            s+=r.toColoredString()+" ";
        }
        s+= colour.toString()+   "\n|Victory Points: "+victoryPoints;
        s+="\n______________________\n"+LightColour.WHITE;
        return s;
    }
}
