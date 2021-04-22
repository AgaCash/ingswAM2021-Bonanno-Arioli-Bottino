package warehouse;

import cards.LeaderCard;
import resources.Resource;

import java.util.ArrayList;

/**
 *  class that stores the resources obtained from the marketboard
 */
public class WarehouseDepot {
    private ArrayList<Resource> depot = new ArrayList<Resource>(6);
    private ArrayList<LeaderCard> extra = new ArrayList<LeaderCard>(2);

    /** this method checks if is possible to add the requested resource, then adds it
     * @param resource the resource to add
     */
    public void addResource(Resource resource) {
        boolean check;
        check = checkArray(resource);
        if(!check)
            depot.add(resource);
        else
            System.out.println("cannot add the resource in the warehouse");
    }

    /** method to remove the requested resource from the depot's arraylist
     * @param resource the resource to remove
     */
    public void removeResource (Resource resource){
        boolean result;
        result=depot.remove(resource);
        if(!result)
            System.out.println("resource not found");
    }

    /** method called by addResource to check if is possible to add a new resource to the depot
     * @param resource the resource to add
     * @return boolean false if the check is correct ( error = false), true otherwise
     */
    public boolean checkArray(Resource resource) {
        boolean triplet = false;
        boolean couple = false;
        boolean single = false;
        boolean error = false;
        Resource top;
        Resource elem;
        ArrayList<Resource> clonedList =new ArrayList<>(6);
        for(int i=0; i < depot.size(); i++)
            clonedList.add(depot.get(i));
        if(clonedList.size() < 6)
            clonedList.add(resource);
        else
            error = true;
        int count = 1;
        while (!clonedList.isEmpty()&&(!error)) {
            top = clonedList.get(0);
            clonedList.remove(0);
            count = 1;
            for (int position = 0; position < clonedList.size(); position++) {
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

    /** method to check if the arraylist contains the requested resource
     * @param res the resource to check
     * @return boolean true if the arraylist contains the resource, otherwise false
     */
    public boolean isPresent (ArrayList<Resource> res){
        ArrayList<Resource> clonedList =new ArrayList<Resource>();
        boolean isPresent = true;
        for(int i=0; i < depot.size(); i++)
            clonedList.add(depot.get(i));
        // clonedList = (ArrayList<Resource>) strongbox.clone();
        for(int pos = 0; pos<res.size(); pos++){
            if(clonedList.contains(res.get(pos))) {
                int num = clonedList.indexOf(res.get(pos));
                clonedList.remove(num);
            }
            else
                isPresent = false;
        }
        return isPresent;
    }

    /** getter method for the depot
     * @param whichOne arraylist's position requested for the get
     * @return the resource at the requested position
     */
    public Resource getResource (int whichOne){
        Resource res;
        res = depot.get(whichOne);
        return res;
    }

}
