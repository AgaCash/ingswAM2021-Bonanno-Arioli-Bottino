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
            System.out.println("card: "+card.getId()+" "+card.getExtraWarehouse());
            if (card.addResource(tmp)) {
                added = true;
                break;
            }
        }
        if (!added) {
            if(warehouse.containsKey(tmp)){
                int level = warehouse.get(tmp);
                if(level == 1) {
                    warehouse.remove(tmp);
                    if (!(warehouse.containsValue(2) && warehouse.containsValue(3)) &&
                            !(warehouse.containsValue(2) && !warehouse.containsValue(1) && warehouse.size()==2))
                        warehouse.put(tmp, level + 1);
                    else {
                        warehouse.put(tmp, level);
                        threw(tmp);
                    }
                }
                else if(level == 2)
                    if(!warehouse.containsValue(3))
                        warehouse.put(tmp, level+1);
                    else
                        threw(tmp);
                else
                    threw(tmp);
            }else
            if(warehouse.size()<3)
                warehouse.put(tmp, 1);
            else
                threw(tmp);
        }


    }

    private void threw(Resource tmp) throws FullWarehouseException {
        threwResources.add(tmp);
        throw new FullWarehouseException("Resource" + tmp + "can't be added to Warehouse: full warehouse");

    }

    public void removeResource(Resource tmp) throws ResourceNotFoundException {
        for(ExtraDepot card : cards)
            if(card.removeResource(tmp)) {
                return;
            }
        if(warehouse.containsKey(tmp)){
            int i = warehouse.get(tmp);
            if(i == 1)
                warehouse.remove(tmp);
            else
                warehouse.put(tmp, i-1);
            return;
        }
        else {
            throw new ResourceNotFoundException();
        }

    }

    public boolean isPresent(ArrayList<Resource> res){
        TreeMap<Resource, Integer> clonedWarehouse = (TreeMap<Resource, Integer>) warehouse.clone();

        for(Resource ptr: res){
            if(!clonedWarehouse.containsKey(ptr)) {
                boolean found = false;
                for(ExtraDepot card : this.cards) {
                    if (card.getExtraWarehouse().contains(ptr)) {
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
        ArrayList<LightResource> extraImage = new ArrayList<>();

        toArray().forEach(e-> image.add(LightResource.valueOf(e.toString())));
        for(ExtraDepot card: cards)
            card.getExtraWarehouse().forEach(e -> extraImage.add(LightResource.valueOf(e.toString())));

        LightWarehouseDepot warehouseDepot = new LightWarehouseDepot();
        warehouseDepot.setWarehouse(image);
        if(!extraImage.isEmpty())
            warehouseDepot.setExtraWarehouse(extraImage);

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
        threwResources.clear();
        return cloned;
    }

    public ArrayList<Resource> status(){
        ArrayList<Resource> image = toArray();
        for(ExtraDepot card: cards){
            if(card.isEnabled())
                image.addAll(card.getExtraWarehouse());
        }
        return image;
    }

    private ArrayList<Resource> toArray(){
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