package clientModel.warehouse;

import exceptions.FullWarehouseException;
import model.cards.ExtraDepot;
import model.resources.Resource;

import java.util.ArrayList;
import java.util.TreeMap;

public class LightWarehouseDepot {

    private TreeMap<Resource, Integer> warehouse = new TreeMap<>();
    private ArrayList<ExtraDepot> cards = new ArrayList<>();

    /*
    public void addNewExtraDepot(ExtraDepot card){
        if(card.isEnabled() && card.isExtraDepot())
            cards.add(card);
    }

    public void addResource(Resource tmp) {
        boolean added = false;
        for (ExtraDepot card : cards) {
            if (card.addResource(tmp)) {
                added = true;
                break;
            }
        }
        if (!added){
            if (warehouse.containsKey(tmp)) {
                int level = warehouse.get(tmp);
                if ((level == 2 && !warehouse.containsValue(3))
                        || (level == 1 &&
                        (!warehouse.containsValue(2) || !warehouse.containsValue(3))))
                    warehouse.put(tmp, level + 1);
            }
            else
                warehouse.put(tmp, 1);
        }
    }

    public Resource removeResource(Resource tmp){
        for(ExtraDepot card : cards)
            if(card.removeResource(tmp)) {
                return tmp;
            }
        if(warehouse.containsKey(tmp)){
            int i = warehouse.get(tmp);
            if(i == 1)
                warehouse.remove(tmp);
            else
                warehouse.put(tmp, i-1);
            return tmp;
        }
        else {
            return null;
        }

    }

     */
}
