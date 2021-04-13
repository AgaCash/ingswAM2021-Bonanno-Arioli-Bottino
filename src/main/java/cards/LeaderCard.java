package cards;
import resources.Resource;

import java.util.ArrayList;
//this is a provisional class
abstract public class LeaderCard extends Card{
	public boolean isEnabled;
	protected int victoryPoints;
	protected ArrayList<Object> requirements;

	abstract public Resource whichResource();

	//abstract public boolean isEnabled();

	//public Resource whichResource(){
	//	return this.whichResource;
	//}
}