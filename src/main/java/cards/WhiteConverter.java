package cards;
import resources.*;
import java.util.ArrayList;

public class WhiteConverter extends LeaderCard{
	private Resource resource;
	private ArrayList<DevelopmentCard> requires;
	private static int victoryPoints = 5;

	public WhiteConverter(int id, boolean en, ArrayList<DevelopmentCard> req, Resource res){
		this.id = id;
		this.resource = res;
		this.isEnabled = en;
		this.requires = req;
	}
	@Override
	public Resource whichResource(){
		return this.resource;
	}
	@Override
	public boolean isEnabled(){
		return this.isEnabled;
	}
	@Override
	public Resource whichDiscount(){
		return null;
	}
}