package model.strongbox;

import exceptions.ResourceNotFoundException;
import model.resources.Resource;

import java.util.ArrayList;

public class Strongbox {
    /**
     * class that stores all the model.resources created by productions
     */

    private ArrayList<Resource> strongbox = new ArrayList<Resource>();
    private ArrayList<Resource> tmpStrongbox = new ArrayList<>();

    /** this method adds a new resource to the model.strongbox arraylist
     * @param resource is the resource added to the arraylist
     */
    public void addResource (Resource resource){
        tmpStrongbox.add(resource);
    }

    /** this method checks if the model.strongbox contains the demanded resource, then removes it
     * @param resource is the resource to remove
     */
    public void removeResource (Resource resource) throws ResourceNotFoundException {
        boolean result;
        result=strongbox.remove(resource);
        if(!result)
            throw new ResourceNotFoundException("Resource not found!");
    }

    /** getter method for the model.strongbox
     * @param whichOne arraylist's position requested for the get
     * @return the resource at the requested position
     */
    public Resource getResource (int whichOne){
        Resource res;
        res = strongbox.get(whichOne);
        return res;
    }

    /** method to check if the arraylist contains a resource
     * @param res the resource to check
     * @return boolean true if is present, otherwise false
     */
    public boolean isPresent (ArrayList<Resource> res){
        ArrayList<Resource> clonedStrongbox = (ArrayList<Resource>) strongbox.clone();
        for(Resource r : res) {
            boolean found = false;
            for (int i = 0; i < clonedStrongbox.size(); i++)
                if (clonedStrongbox.get(i) == r) {
                    clonedStrongbox.remove(i);
                    found = true;
                    break;
                }
            if(!found)
                return false;
        }
        return true;
    }

    public void updateStrongbox(){
        strongbox.addAll(tmpStrongbox);
    }

    //only 4 tests
    public ArrayList<Resource> status(){
        return this.strongbox;
    }
}
