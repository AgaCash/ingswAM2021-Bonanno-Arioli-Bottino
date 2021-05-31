package model.warehouse;

import clientModel.resources.LightResource;
import clientModel.warehouse.LightWarehouseDepot;
import exceptions.FullWarehouseException;
import exceptions.ResourceNotFoundException;
import exceptions.UnusableCardException;
import model.cards.ExtraDepot;
import model.resources.Resource;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class WarehouseDepot {
    private TreeMap<Resource, Integer> warehouse = new TreeMap<>();
    private ArrayList<ExtraDepot> cards = new ArrayList<>();
    //just 4 tests
    private ArrayList<Resource> trash = new ArrayList<>();


    public void addNewExtraDepot(ExtraDepot card) throws UnusableCardException {
        if(card.isEnabled() && card.isExtraDepot())
            cards.add(card);
        else
            throw new UnusableCardException();
    }

    public void addResource(Resource tmp) throws FullWarehouseException {
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
                else
                    throw new FullWarehouseException("Resource"+tmp+"can't be added to Warehouse");
            } else if (warehouse.containsValue(1) && warehouse.containsValue(2) && warehouse.containsValue(3))
                throw new FullWarehouseException("Resource"+tmp+"can't be added to Warehouse");
            else
                warehouse.put(tmp, 1);
        }
    }

    public Resource removeResource(Resource tmp) throws ResourceNotFoundException {
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
            throw new ResourceNotFoundException();
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

    public LightWarehouseDepot convert(){
       ArrayList<LightResource> image = new ArrayList<>();
       status().forEach(e -> image.add(LightResource.valueOf(e.toString())));

        LightWarehouseDepot warehouseDepot = new LightWarehouseDepot();
        warehouseDepot.setWarehouse(image);
        return warehouseDepot;
    }












    //just 4 tests
    public ArrayList<Resource> status(){
        ArrayList<Resource> image = new ArrayList<>();
        ArrayList<Map.Entry<Resource, Integer>> orderedWarehouse = new ArrayList<>(warehouse.entrySet());
        orderedWarehouse.sort(Map.Entry.comparingByValue());
        for (Map.Entry<Resource, Integer> entry : orderedWarehouse) {
            for(int i = 0; i<entry.getValue(); i++)
                image.add(entry.getKey());
        }
        return image;
    }

}