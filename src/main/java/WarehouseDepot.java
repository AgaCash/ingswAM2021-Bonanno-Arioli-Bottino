import cards.LeaderCard;
import resources.Resource;

import java.util.ArrayList;

//
public class WarehouseDepot {
    private ArrayList<Resource> depot = new ArrayList<Resource>(6);
    private ArrayList<LeaderCard> extra = new ArrayList<LeaderCard>(2);

    public void addResource(Resource resource) {
        boolean check;
        check = checkArray(depot, resource);
        if(!check)
            depot.add(resource);
        else
            System.out.println("cannot add the resource in the warehouse");
    }
    public void removeResource (Resource resource){
        boolean result;
        result=depot.remove(resource);
        if(!result)
            System.out.println("resource not found");
    }

    public boolean checkArray(ArrayList<Resource> res, Resource resource) {
        boolean triplet = false;
        boolean couple = false;
        boolean single = false;
        boolean error = false;
        Resource top;
        Resource elem;
        ArrayList<Resource> clonedList = (ArrayList<Resource>) res.clone();
        if(clonedList.size()>=5)
            clonedList.add(resource);
        else
            error = true;
        int count = 1;
        while (!clonedList.isEmpty()&&(!error)) {
            top = clonedList.get(0);
            clonedList.remove(0);
            count = 1;
            for (int position = 0; position < 5 && !clonedList.isEmpty(); position++) {
                elem = clonedList.get(position);
                if (top.equals(elem)) {
                    count++;
                    clonedList.remove(position);
                    position--;
                }
            }
                if (count == 1){
                    if (!single)
                        single = true;
                    else if (!couple)
                        couple = true;
                    else if (!triplet)
                        triplet = true;
                    else{
                        error = true;
                        break;
                    }
                }
                if (count == 2){
                    if(!couple)
                        couple = true;
                    else if (!triplet)
                        triplet = true;
                    else{
                        error = true;
                        break;
                    }
                }
                if (count == 3){
                    if(!triplet)
                        triplet = true;
                    else{
                        error = true;
                        break;
                    }
                }
        }
        return error;
    }
}
