package clientModel.cards;

abstract public class LightLeaderCard{
    protected int id;
    protected boolean isEnabled;
    protected int victoryPoints;
    protected LightLeaderCardType type;
    //universal methods
    //public void activate(){this.isEnabled = true;};
    //consult methods
    //public boolean isEnabled(){ return isEnabled; }
    //public ArrayList<LightDevelopmentCard> getRequiredCards(){return null;}
    //public ArrayList<LightResource> getRequiredResources(){return null;}
    //identification methods
    public boolean isDiscount(){
        return false;
    }
    public boolean isExtraDepot() {
        return false;
    }
    public boolean isExtraProd(){
        return false;
    }
    public boolean isWhiteConverter(){
        return false;
    }
    public boolean isEnabled(){ return  isEnabled; }
    //subclasses specific methods
    //public LightResource whichResource() throws UnusableCardException { return null; }
    //public LightResource whichDiscount() throws UnusableCardException { return null; }
    //public boolean addResource(LightResource tmp){return false;}
    //public boolean removeResource(LightResource tmp){return false;}
    //public LightResource getExtraProdInput() throws UnusableCardException { return null;}
    //public ArrayList<LightResource> production(){return null;}
    //public void setChosenOutput(LightResource chosenOutput){}

}
