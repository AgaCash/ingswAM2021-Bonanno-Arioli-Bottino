package clientModel.warehouse;

import clientModel.resources.LightResource;

import java.util.ArrayList;

/**
 * LightModel copy of Player's WarehouseDepot in model
 */
public class LightWarehouseDepot {

    private ArrayList<LightResource> warehouse = new ArrayList<>();
    private ArrayList<LightResource> extraWarehouse = new ArrayList<>();

    /**Updates the LightWarehouseDepot with the current Model's WarehouseDepot status
     * @param image a LightResource ArrayList
     */
    public void setWarehouse(ArrayList<LightResource> image){
        //this.warehouse.clear();
        this.warehouse = image;
    }

    /**Updates the LightWarehouseDepot extra Resources (ExtraDepot Card ability) with the current Model's WarehouseDepot extra Resources
     * @param image a LightResource ArrayList
     */
    public void setExtraWarehouse(ArrayList<LightResource> image){
        //this.extraWarehouse.clear();
        this.extraWarehouse = image;
    }

    /**Returns the current LightWarehouseDepot
     * @return a LightWarehouseDepot instance
     */
    public ArrayList<LightResource> getWarehouse() {
        return warehouse;
    }

    /**Method to print LightMarketBoard in CLI
     * @return a String
     */
    @Override
    public String toString(){
        String s = "\nWAREHOUSE: \n";
        for(int i=0; i<warehouse.size(); i++) {
            s +=warehouse.get(i).toColoredString() + " ";
            if (i<warehouse.size()-1 && !warehouse.get(i+1).toString().equals(warehouse.get(i).toString()))
               s+="\n";
        }
        if(!this.extraWarehouse.isEmpty()) {
            s += "\n\t\tEXTRA: ";
            for (LightResource res : extraWarehouse)
                s += res.toColoredString() + " ";
        }
        return s;
    }
}
