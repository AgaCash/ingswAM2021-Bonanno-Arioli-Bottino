package cards;
import resources.Resource;

import java.util.ArrayList;

abstract public class LeaderCard extends Card{
	public boolean isEnabled;
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
	public ArrayList<Resource> whichExtra(){
		return null;
	}

}