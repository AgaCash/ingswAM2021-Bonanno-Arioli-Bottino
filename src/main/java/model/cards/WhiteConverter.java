package model.cards;
import clientModel.cards.LightDevelopmentCard;
import clientModel.cards.LightLeaderCard;
import clientModel.cards.LightWhiteConverter;
import clientModel.resources.LightResource;
import exceptions.UnusableCardException;
import model.resources.Resource;

import java.util.ArrayList;

/**
 *WhiteConverter represents a LeaderCard type whose ability is converting the white Marble in a specific Resource type
 */
public class WhiteConverter extends LeaderCard{
	private Resource resource;
	private ArrayList<DevelopmentCard> requires;
	private static int victoryPoints = 5;

	/**Constructor
	 * @param id the unique ID
	 * @param en true if it's enabled, false if not
	 * @param req the DevelopmentCard ArrayList required to enable
	 * @param res the Resource in which white Marble will be converted
	 */
	public WhiteConverter(int id, boolean en, ArrayList<DevelopmentCard> req, Resource res){
		this.id = id;
		this.resource = res;
		this.isEnabled = en;
		this.requires = req;
		super.type = LeaderCardType.WHITECONVERTER;
	}

	@Override
	public Resource whichResource() throws UnusableCardException {
		if(!isEnabled())
			throw new UnusableCardException("Can't use this leader card!");
		return this.resource;
	}

	@Override
	public boolean isWhiteConverter(){
		return true;
	}

	@Override
	public ArrayList<DevelopmentCard> getRequiredCards(){
		return (ArrayList<DevelopmentCard>) this.requires.clone();
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

	@Override
	public LightLeaderCard convert(){
		ArrayList<LightDevelopmentCard> requires = new ArrayList<>();
		this.requires.forEach(element -> requires.add(element.convert()));

		return new LightWhiteConverter(this.id,
				this.isEnabled,
				requires,
				LightResource.valueOf(resource.toString()));
	}

}