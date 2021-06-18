package clientModel.warehouse;

import clientModel.resources.LightResource;

import java.util.ArrayList;

public class LightWarehouseDepot {

    private ArrayList<LightResource> warehouse = new ArrayList<>();
    private ArrayList<LightResource> extraWarehouse = new ArrayList<>();

    public void setWarehouse(ArrayList<LightResource> image){
        this.warehouse.clear();
        this.warehouse = image;
    }

    public void setExtraWarehouse(ArrayList<LightResource> image){
        this.extraWarehouse.clear();
        this.extraWarehouse = image;
    }

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
    @Override
    public String toString(){
        String s = "\nWAREHOUSE: \n";
        for(LightResource res: warehouse)
            s+=res.toColoredString()+" ";
        if(!this.extraWarehouse.isEmpty()) {
            s += "\n\t\tEXTRA: ";
            for (LightResource res : extraWarehouse)
                s += res.toColoredString() + " ";
        }
        return s;
    }
}
