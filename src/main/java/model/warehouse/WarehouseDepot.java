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
    private ArrayList<Resource> threwResources = new ArrayList<>();


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
                System.out.println("riga 34");
                break;
            }
        }
        if (!added) {
            if (warehouse.containsKey(tmp)) {
                int level = warehouse.get(tmp);
                /*if (warehouse.size() < 3) {
                    if (level != 3) {
                        warehouse.put(tmp, level + 1);
                        System.out.println("tmp" + level + 1);
                    }
                    else {
                        threwResources.add(tmp);
                        System.out.println("Resource" + tmp + "can't be added to Warehouse: full level");
                        throw new FullWarehouseException("Resource" + tmp + "can't be added to Warehouse: full level");
                    }
                } else if (warehouse.size() == 3) {*/
                if ((level == 1 && (!warehouse.containsValue(2) || !warehouse.containsValue(3)))
                        || level == 2 && (!warehouse.containsValue(3)))
                        warehouse.put(tmp, level + 1);
                else {
                    threwResources.add(tmp);
                    System.out.println("Resource" + tmp + "can't be added to Warehouse: full level");
                    throw new FullWarehouseException("Resource" + tmp + "can't be added to Warehouse: full level");
                }
            }
            else{
                if(warehouse.size()<3)
                    warehouse.put(tmp, 1);
                else if(warehouse.size() == 3){
                    threwResources.add(tmp);
                    System.out.println("Resource" + tmp + "can't be added to Warehouse: full warehouse");
                    throw new FullWarehouseException("Resource" + tmp + "can't be added to Warehouse: full warehouse");
                }

            }
        }
                /*
                if ((level == 2 && !warehouse.containsValue(3))
                        || (level == 1 &&
                        (!warehouse.containsValue(2) || !warehouse.containsValue(3))
                        || (level ==2 && !warehouse.containsValue(1)))) {
                    warehouse.put(tmp, level + 1);
                    System.out.println(tmp + "riga 44");
                } else {
                    threwResources.add(tmp);
                    System.out.println(tmp + "riga 48");
                    System.out.println("Resource" + tmp + "can't be added to Warehouse");
                    throw new FullWarehouseException("Resource" + tmp + "can't be added to Warehouse");
                }
            } else if (warehouse.size() < 3) {
                warehouse.put(tmp, 1);

            } else {
                threwResources.add(tmp);
                System.out.println(tmp + "riga 54");
                System.out.println("Resource" + tmp + "can't be added to Warehouse");
                throw new FullWarehouseException("Resource" + tmp + "can't be added to Warehouse");
            }

                 */
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

    public ArrayList<LightResource> getThrewResources(){
        ArrayList<LightResource> cloned = new ArrayList<>();
        System.out.println(this.threwResources+ "threwRes riga 116");
        this.threwResources.forEach(e-> cloned.add(LightResource.valueOf(e.toString())));
        /*for(Resource res: threwResources)
            for(Player player: players)
                if(player!=currentPlayer)
                    player.getPlayerBoard().getFaithTrack().faithAdvance(player.getPlayerBoard().getFaithBox(), player.getPlayerBoard().getFaithTrack());

             */
        System.out.println(cloned+ "riga 123");
        threwResources.clear();
        return cloned;
    }












    //just 4 tests
    public ArrayList<Resource> status(){
        ArrayList<Resource> image = new ArrayList<>();
        ArrayList<Map.Entry<Resource, Integer>> orderedWarehouse = new ArrayList<>(warehouse.entrySet());
        orderedWarehouse.sort(Map.Entry.comparingByValue());
        System.out.println(orderedWarehouse);
        for (Map.Entry<Resource, Integer> entry : orderedWarehouse) {
            for(int i = 0; i<entry.getValue(); i++)
                image.add(entry.getKey());
        }
        return image;
    }

}