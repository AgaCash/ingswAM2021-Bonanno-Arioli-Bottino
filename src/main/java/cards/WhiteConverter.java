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
	public void activate() {
		this.isEnabled = true;
	}

	@Override
	public Resource whichResource(){
		if(isEnabled())
			return this.resource;
		return null;
	}

	@Override
	public boolean isWhiteConverter(){
		return true;
	}

	@Override
	public ArrayList<DevelopmentCard> getRequiredCards(){
		return this.requires;
	}

	/**
	 * for tests
	 * @return
	 */
	@Override
	public String toString(){
		String s = "\nWHITE CONVERTER";
		s+= "\nID: "+id;
		s+= "\nRequires: ";
		for (DevelopmentCard d:requires) {
			s+="\n\t "+d;
		}
		s+= "\nConvert: " +resource;
		s+= "\nVictory Points: "+victoryPoints;
		s+= "\nIs Enabled: "+ isEnabled;
		return s;
	}

}