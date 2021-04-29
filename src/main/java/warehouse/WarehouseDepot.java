package warehouse;

import cards.ExtraDepot;
import resources.Resource;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class WarehouseDepot {
    TreeMap<Resource, Integer> warehouse = new TreeMap<>();
    ArrayList<ExtraDepot> cards = new ArrayList<>();

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
                /*if ((level == 2 && !warehouse.containsValue(3))
                    || (level == 1 &&
                        (!warehouse.containsValue(2) || !warehouse.containsValue(3))))*/
                if(level < 3 && (!warehouse.containsValue(level+1) || (!warehouse.containsValue(level+2))))
                    warehouse.put(tmp, level + 1);
                else
                    throwResource();
            } else if (warehouse.containsValue(1) && warehouse.containsValue(2) && warehouse.containsValue(3))
                throwResource();
            else
                warehouse.put(tmp, 1);
        }
    }

    private void throwResource(){
        //notifica al controller
        //System.out.println("pienooooo");
    }

    public Resource removeResource(Resource tmp){
        for(ExtraDepot card : cards)
                if(card.removeResource(tmp))
                    return tmp;
        if(warehouse.containsKey(tmp)){
            int i = warehouse.get(tmp);
            if(i == 1)
                warehouse.remove(tmp);
            else
                warehouse.put(tmp, i-1);
            return tmp;
        }
        else {
            //notifica al controller
            //System.out.println("non presente");
            return null;
        }

    }

    public boolean isPresent(ArrayList<Resource> res){
        TreeMap<Resource, Integer> clonedWarehouse = (TreeMap<Resource, Integer>) warehouse.clone();
        ArrayList<ExtraDepot> clonedCards = (ArrayList<ExtraDepot>) this.cards.clone();
        for(Resource ptr: res){
            if(!clonedWarehouse.containsKey(ptr)) {
                boolean found = false;
                for(ExtraDepot card : clonedCards) {
                    if (card.removeResource(ptr)) {
                        found = true;
                        break;
                    }
                }
                if(!found)
                    return false;
            }
            else{
                int count = clonedWarehouse.get(ptr);
                if(count == 1)
                    clonedWarehouse.remove(ptr);
                else
                    clonedWarehouse.put(ptr, count-1);
            }
        }

        return true;
    }

    public void print(){
        ArrayList<Map.Entry<Resource, Integer>> orderedWarehouse = new ArrayList<>(warehouse.entrySet());
        orderedWarehouse.sort(Map.Entry.comparingByValue());
        //orderedWarehouse.forEach(System.out::println);
        //System.out.println("--------------------------");
        //notifica al controller
    }
}