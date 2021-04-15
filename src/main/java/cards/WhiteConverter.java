package cards;
import resources.*;
import java.util.ArrayList;

public class WhiteConverter extends LeaderCard{
	private Resource resource;
	private ArrayList<DevelopmentCard> requirements;
	private static int victoryPoints = 5;

	public WhiteConverter(int id, boolean en, ArrayList<DevelopmentCard> req, Resource res){
		this.cardId = id;
		this.resource = res;
		this.isEnabled = en;
		this.requirements = req;
	}
	@Override
	public Resource whichResource(){
		return this.resource;
	}
	@Override
	public boolean isEnabled(){
		return this.isEnabled;
	}
}