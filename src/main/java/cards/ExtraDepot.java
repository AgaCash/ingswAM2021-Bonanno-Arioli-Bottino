package cards;

import resources.Resource;
import java.util.ArrayList;

public class ExtraDepot extends LeaderCard{
    private ArrayList<Resource> requires;
    private static int victoryPoints = 3;
    private ArrayList<Resource> extraResource;

    public ExtraDepot(int id, boolean en, ArrayList<Resource> req, ArrayList<Resource> extra){
        this.id = id;
        this.isEnabled = en;
        this.requires = req;
        this.extraResource = extra;
    }

    @Override
    public Resource whichResource(){
        return null;
    }
    @Override
    public Resource whichDiscount(){
        return null;
    }
    @Override
    public boolean isEnabled(){
        return this.isEnabled;
    }
}
