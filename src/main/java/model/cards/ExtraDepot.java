package model.cards;

import model.resources.Resource;

import java.util.ArrayList;
import java.util.TreeMap;

public class ExtraDepot extends LeaderCard{
    private ArrayList<Resource> requiredResource;
    private static int victoryPoints = 3;
    private ArrayList<Resource> extraDepotResource;
    private TreeMap<Resource, Integer> extraWarehouseDepot = new TreeMap<>();

    public ExtraDepot(int id, boolean en, ArrayList<Resource> req, ArrayList<Resource> extra){
        this.id = id;
        this.isEnabled = en;
        this.requiredResource = req;
        this.extraDepotResource = extra;
        for(Resource ptr : extraDepotResource)
            extraWarehouseDepot.put(ptr, 0);
        super.type = LeaderCardType.EXTRADEPOT;
    }

    @Override
    public boolean addResource(Resource tmp){
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
    public boolean removeResource(Resource tmp){
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

    @Override
    public boolean isExtraDepot(){
        return true;
    }

    @Override
    public ArrayList<Resource> getRequiredResources(){
        return this.requiredResource;
    }
    /**
     * for tests
     * @return
     */
    @Override
    public String toString(){
        String s = "\nEXTRA DEPOT";
        s+= "\nID: "+id;
        s+= "\nRequires: ";
        for (Resource r:requiredResource) {
            s+="\n\t "+r;
        }
        s+="\nExtra model.resources: ";
        for(Resource r: extraDepotResource ){
            s+="\n\t "+r;
        }
        s+= "\nVictory Points: "+victoryPoints;
        s+= "\nIs Enabled: "+ isEnabled;
        return s;
    }
}
