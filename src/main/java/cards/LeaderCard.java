package cards;
import resources.Resource;

import java.util.ArrayList;

abstract public class LeaderCard extends Card{
	protected boolean isEnabled;
	protected int victoryPoints;
	protected ArrayList<Object> requirements;

	public Resource whichResource(){
		return null;
	}
	public boolean isEnabled(){
		return isEnabled;
	}
	public Resource whichDiscount(){
		return null;
	}
	public boolean addResource(Resource tmp){return false;}
	public boolean removeResource(Resource tmp){return false;}
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
	public Resource getRequirement(){ return null;}
	public ArrayList<Resource> production(){return null;}
	public void setChosenOutput(Resource chosenOutput){}

}