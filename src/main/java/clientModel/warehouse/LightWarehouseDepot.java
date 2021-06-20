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
