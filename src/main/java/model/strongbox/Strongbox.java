package model.strongbox;

import clientModel.resources.LightResource;
import clientModel.strongbox.LightStrongbox;
import exceptions.ResourceNotFoundException;
import model.resources.Resource;

import java.util.ArrayList;
/**
 * Class that stores all the Resources created by productions
 */
public class Strongbox {
    private ArrayList<Resource> strongbox = new ArrayList<>();
    private ArrayList<Resource> tmpStrongbox = new ArrayList<>();

    /** this method adds a new resource to the model.strongbox arraylist
     * @param resource is the resource added to the arraylist
     */
    public void addResource (Resource resource){
        tmpStrongbox.add(resource);
    }

    /** this method checks if the strongbox contains the demanded resource, then removes it
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


    /** method to check if the arraylist contains a list of resources
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

    /**
     * Updates Strongbox at the end of the turn adding the tmpStrongbox containment in strongbox
     */
    public void updateStrongbox(){
        strongbox.addAll(tmpStrongbox);
        tmpStrongbox.clear();
    }

    /**Returns the current Strongbox state as an ArrayList
     * @return a Resource ArrayList
     */
    public ArrayList<Resource> status(){
        return this.strongbox;
    }

    /**Converts the current Strongbox state in a LightStrongbox instance for LightModel
     * @return a LightStrongbox instance
     */
    public LightStrongbox convert(){
        LightStrongbox strongbox = new LightStrongbox();
        ArrayList<LightResource> newStrongbox = new ArrayList<>();
        ArrayList<LightResource> newTmpStrongbox = new ArrayList<>();

        this.strongbox.forEach(element -> newStrongbox.add(LightResource.valueOf(element.toString())));
        this.tmpStrongbox.forEach(element -> newTmpStrongbox.add(LightResource.valueOf(element.toString())));
        strongbox.setTmpStrongbox(newTmpStrongbox);
        strongbox.setStrongbox(newStrongbox);

        return strongbox;
    }
}
