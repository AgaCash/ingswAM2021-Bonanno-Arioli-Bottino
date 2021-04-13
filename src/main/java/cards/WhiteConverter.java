package cards;
import resources.*;
import java.util.ArrayList;

public class WhiteConverter extends LeaderCard{
	private Resource resource;
	private ArrayList<DevelopmentCard> requirements;
	private static int victoryPoints = 5;

	public WhiteConverter(Resource res, boolean en, ArrayList<DevelopmentCard> req){
		this.resource = res;
		this.isEnabled = en;
		this.requirements = req;
	}
	@Override
	public Resource whichResource(){
		return this.resource;
	}
}