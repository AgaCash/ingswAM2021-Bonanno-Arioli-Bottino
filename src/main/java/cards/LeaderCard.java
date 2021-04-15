package cards;
import resources.Resource;

import java.util.ArrayList;

abstract public class LeaderCard extends Card{
	public boolean isEnabled;
	protected int victoryPoints;
	protected ArrayList<Object> requirements;

	abstract public Resource whichResource();
	abstract public boolean isEnabled();

}