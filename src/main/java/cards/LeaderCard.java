package cards;
import resources.*;

public class LeaderCard extends Card{
	protected boolean isEnabled=true;
	protected Resource whichResource;

	public boolean isEnabled(){
		return this.isEnabled;
	}

	public Resource whichResource(){
		return this.whichResource;
	}
}