package cards;

import resources.Resource;
import java.util.ArrayList;

public class ExtraDepot extends LeaderCard{
    private ArrayList<Resource> requiredResource;
    private static int victoryPoints = 3;
    private ArrayList<Resource> extraDepotResource;

    public ExtraDepot(int id, boolean en, ArrayList<Resource> req, ArrayList<Resource> extra){
        this.id = id;
        this.isEnabled = en;
        this.requiredResource = req;
        this.extraDepotResource = extra;
    }

    @Override
    public ArrayList<Resource> whichExtra(){
        return this.extraDepotResource;
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
        s+="\nExtra resources: ";
        for(Resource r: extraDepotResource ){
            s+="\n\t "+r;
        }
        s+= "\nVictory Points: "+victoryPoints;
        s+= "\nIs Enabled: "+ isEnabled;
        return s;
    }
}
