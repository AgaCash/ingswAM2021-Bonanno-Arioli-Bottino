package model.cards;

import clientModel.cards.LightExtraDepot;
import clientModel.cards.LightLeaderCard;
import clientModel.resources.LightResource;
import model.resources.Resource;

import java.util.ArrayList;

public class ExtraDepot extends LeaderCard{
    private ArrayList<Resource> requiredResource;
    private static int victoryPoints = 3;
    private ArrayList<Resource> extraDepotResource;
    private transient ArrayList<Resource> extraWarehouseDepot;

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
                 if(this.extraWarehouseDepot.size()<this.extraDepotResource.size() ||
                            this.extraWarehouseDepot.isEmpty()) {
                    extraWarehouseDepot.add(tmp);
                    return true;
                }
        return false;
    }
    @Override
    public boolean removeResource(Resource tmp){
        if(isEnabled())
            if(extraWarehouseDepot.remove(tmp))
                    return true;
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

    public ArrayList<Resource> getExtraWarehouse(){
        return (ArrayList<Resource>) this.extraWarehouseDepot.clone();
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
