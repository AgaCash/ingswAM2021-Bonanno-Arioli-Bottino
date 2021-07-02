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

/**WarehouseDepot is the class representing the Warehouse, the tool in the Game where added the new purchased Resources from the MarketBoard
 * It's structure is a three levels Resource container, where for each level:
 *  - cannot be the same Resources for more than one level at same time
 *  - first level can contain one Resource instance, second two, third three.
 *  It can be expanded by an ExtraDepot LeaderCard (see ExtraDepot documentation)
 */
public class WarehouseDepot {
    private TreeMap<Resource, Integer> warehouse = new TreeMap<>();
    private ArrayList<ExtraDepot> cards = new ArrayList<>();
    private ArrayList<Resource> threwResources = new ArrayList<>();


    /**Adds a new ExtraDepot active card to Warehouse
     * @param card the ExtraDepot instance
     * @throws UnusableCardException  if the ExtraDepot can't be used (non-active Card or insufficient requirements to benefit of its leader ability)
     */
    public void addNewExtraDepot(ExtraDepot card) throws UnusableCardException {
        if(card.isEnabled() && card.isExtraDepot())
            cards.add(card);
        else
            throw new UnusableCardException();
    }

    /**Adds a new Resource instance to Warehouse
     * @param tmp the new Resource that will be added (if possible)
     * @throws FullWarehouseException if WarehouseDepot can't contain @tmp because of a violation in the levels rule
     */
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

    /**Implements the addResource method Exception throwing
     * @param tmp the Resource instance can't be added
     * @throws FullWarehouseException always in this method
     */
    private void threw(Resource tmp) throws FullWarehouseException {
        threwResources.add(tmp);
        throw new FullWarehouseException("Resource" + tmp + "can't be added to Warehouse: full warehouse");

    }

    /**Removes a Resource instance of @tmp type
     * @param tmp the Resource type will be removed
     * @throws ResourceNotFoundException if there's not a same tmp Resource instance in WarehouseDepot
     */
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
        }
        else {
            throw new ResourceNotFoundException();
        }

    }


    /**Checks if a Resource ArrayList is present in the Warehouse
     * @param res a Resource ArrayList
     * @return true if all @res members are present, false if at least one is not present
     */
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

    /**Converts WarehouseDepot state in a new LightWarehouseDepot instance for LightModel
     * @return a LightWarehouseDepot instance
     */
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

    /**Returns the Resource list that were not added to the WarehouseDepot after a MarketBoard purchase
     * @return a Resource ArrayList
     */
    public ArrayList<LightResource> getThrewResources(){
        ArrayList<LightResource> cloned = new ArrayList<>();
        this.threwResources.forEach(e-> cloned.add(LightResource.valueOf(e.toString())));
        threwResources.clear();
        return cloned;
    }

    /**Returns a Resource list of WarehouseDepot containment
     * @return a Resource ArrayList
     */
    public ArrayList<Resource> status(){
        ArrayList<Resource> image = toArray();
        for(ExtraDepot card: cards){
            if(card.isEnabled())
                image.addAll(card.getExtraWarehouse());
        }
        return image;
    }

    /**Implements the WarehouseDepot's TreeMap to ArrayList conversion
     * @return a LightWarehouseDepot instance
     */
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