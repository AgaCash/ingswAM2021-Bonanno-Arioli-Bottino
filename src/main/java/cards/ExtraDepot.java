package cards;

import resources.Resource;
import java.util.ArrayList;

public class ExtraDepot extends LeaderCard{
    private ArrayList<Resource> requirements;
    private static int victoryPoints = 3;
    private ArrayList<Resource> extraResource;

    @Override
    public Resource whichResource(){
        return null;
    }

    public ExtraDepot(int id, boolean en, ArrayList<Resource> req, ArrayList<Resource> extra){
        this.cardId = id;
        this.isEnabled = en;
        this.requirements = req;
        this.extraResource = extra;
    }
}
