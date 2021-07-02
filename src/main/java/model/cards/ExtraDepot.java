package model.cards;

import clientModel.cards.LightExtraDepot;
import clientModel.cards.LightLeaderCard;
import clientModel.resources.LightResource;
import model.resources.Resource;

import java.util.ArrayList;

/**
 * ExtraDepot represents a LeaderCard whose ability is adding an extra Depot to Warehouse for a couple of specific Resource type
 */
public class ExtraDepot extends LeaderCard{
    private ArrayList<Resource> requiredResource;
    private static int victoryPoints = 3;
    private ArrayList<Resource> extraDepotResource;
    private transient ArrayList<Resource> extraWarehouseDepot;

    /**Constructor
     * @param id the unique ID
     * @param en true if it's enabled, false if not
     * @param req the Resource ArrayList required to activate the Card
     * @param extra the Extra Resource ArrayList that could be added to the depot
     */
    public ExtraDepot(int id, boolean en, ArrayList<Resource> req, ArrayList<Resource> extra){
        this.id = id;
        this.isEnabled = en;
        this.requiredResource = req;
        this.extraDepotResource = extra;
        super.type = LeaderCardType.EXTRADEPOT;
    }

    @Override
    public void activate(){
        this.isEnabled = true;
        this.extraWarehouseDepot = new ArrayList<>();
    }

    @Override
    public boolean addResource(Resource tmp){
        if(isEnabled())
            if(tmp.equals(this.extraDepotResource.get(0)))
                 if(this.extraWarehouseDepot.size()<this.extraDepotResource.size()) {
                    extraWarehouseDepot.add(tmp);
                    return true;
                }
        return false;
    }
    @Override
    public boolean removeResource(Resource tmp){
        if(isEnabled())
            return extraWarehouseDepot.remove(tmp);
        return false;
    }

    @Override
    public boolean isExtraDepot(){
        return true;
    }

    @Override
    public ArrayList<Resource> getRequiredResources(){
        return (ArrayList<Resource>) this.requiredResource.clone();
    }

    @Override
    public ArrayList<Resource> getExtraWarehouse(){
        return (ArrayList<Resource>) this.extraWarehouseDepot.clone();
    }
    /**
     * for tests
     * @return a String
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

    @Override
    public LightLeaderCard convert(){
        ArrayList<LightResource> required = new ArrayList<>();
        ArrayList<LightResource> extra = new ArrayList<>();
        this.requiredResource.forEach(element -> required.add(LightResource.valueOf(element.toString())));
        this.extraDepotResource.forEach(element -> extra.add(LightResource.valueOf(element.toString())));

        return new LightExtraDepot(this.id,
                this.isEnabled,
                required,
                extra);
    }
}
