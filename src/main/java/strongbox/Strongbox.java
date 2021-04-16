package strongbox;

import resources.Resource;

import java.util.ArrayList;

public class Strongbox {
    /**
     * class that stores all the resources created by productions
     */

    private ArrayList<Resource> strongbox = new ArrayList<Resource>();

    /** this method adds a new resource to the strongbox arraylist
     * @param resource is the resource added to the arraylist
     */
    public void addResource (Resource resource){
        strongbox.add(resource);
    }

    /** this method checks if the strongbox contains the demanded resource, then removes it
     * @param resource is the resource to remove
     */
    public void removeResource (Resource resource){
        boolean result;
        result=strongbox.remove(resource);
        if(!result)
            System.out.println("resource not found");
    }

    /** getter method for the strongbox
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
        ArrayList<Resource> clonedList =new ArrayList<Resource>();
        boolean isPresent = true;
        for(int i=0; i < strongbox.size(); i++)
            clonedList.add(strongbox.get(i));

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
}
