package model.cards;
import model.resources.Resource;

import java.util.ArrayList;

abstract public class LeaderCard extends Card{
	protected boolean isEnabled;
	protected int victoryPoints;
	//universal methods
	public void activate(){this.isEnabled = true;};
	//consult methods
	public boolean isEnabled(){
		return isEnabled;
	}
	public ArrayList<DevelopmentCard> getRequiredCards(){return null;}
	public ArrayList<Resource> getRequiredResources(){return null;}
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
	//subclasses specific methods
	public Resource whichResource(){
		return null;
	}
	public Resource whichDiscount(){
		return null;
	}
	public boolean addResource(Resource tmp){return false;}
	public boolean removeResource(Resource tmp){return false;}
	public Resource getExtraProdInput(){ return null;}
	public ArrayList<Resource> production(){return null;}
	public void setChosenOutput(Resource chosenOutput){}

}