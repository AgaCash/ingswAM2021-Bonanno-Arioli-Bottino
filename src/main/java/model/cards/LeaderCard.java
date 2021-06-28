package model.cards;
import clientModel.cards.LightLeaderCard;
import exceptions.UnusableCardException;
import model.resources.Resource;

import java.util.ArrayList;

/**
 * Abstract class representing the Leader Cards (also a Card subclass). It has 4 subclasses (Discount, ExtraDepot, ExtraProd, WhiteConvert)
 * each one with its ability, requirements for activation and specific methods
 * Each card has an unique ID
 */
 abstract public class LeaderCard extends Card{
	protected boolean isEnabled;
	protected int victoryPoints;
	protected LeaderCardType type;

	 /**Enables the LeaderCard
	  */
	public void activate(){this.isEnabled = true;}

	 /**Returns a boolean flag for enabling info
	  * @return true if Card it's enabled, false if not
	  */
	public boolean isEnabled(){
		return isEnabled;
	}

	 /**Returns a required DevelopmentCards for enabling copy for checks
	  * @return a Resource ArrayList, null if it's not referenced to that LeaderCard subclass
	  */
	public ArrayList<DevelopmentCard> getRequiredCards(){return null;}

	 /**Returns a required Resources for enabling copy for checks
	  * @return a Resource ArrayList, null if it's not referenced to that LeaderCard subclass
	  */
	public ArrayList<Resource> getRequiredResources(){return null;}

	 /**Returns the LeaderCard victory points
	  * @return an int
	  */
	 public int getVictoryPoints(){ return this.victoryPoints;}

	 /**Returns a boolean flag about LeaderCard subclass
	  * @return true if instance is a Discount, false if not
	  */
	public boolean isDiscount(){
		return false;
	}

	 /**Returns a boolean flag about LeaderCard subclass
	  * @return true if instance is a ExtraDepot, false if not
	  */
	public boolean isExtraDepot() {
		return false;
	}

	 /**Returns a boolean flag about LeaderCard subclass
	  * @return true if instance is a ExtraProd, false if not
	  */
	public boolean isExtraProd(){
		return false;
	}

	 /**Returns a boolean flag about LeaderCard subclass
	  * @return true if instance is a WhiteConverter, false if not
	  */
	public boolean isWhiteConverter(){
		return false;
	}

	 /**Returns the Resource that will be obtained by WhiteMarble conversion thanks to WhiteConvert ability
	  * @return a Resource instance if LeaderCard is a WhiteConverter, null if it's another type
	  * @throws UnusableCardException if WhiteConverter is not enabled
	  */
	public Resource whichResource() throws UnusableCardException {
		return null;
	}

	 /**Returns the discounted Resource from DevelopmentCard purchase thanks to Discount ability
	  * @return a Resource instance if LeaderCard is a Discount, null if not
	  * @throws UnusableCardException if Discount is not enabled
	  */
	public Resource whichDiscount() throws UnusableCardException {
		return null;
	}

	 /**Adds a Resource to ExtraDepot
	  * @param tmp a Resource instance
	  * @return true if Resource it's of the required type, false if not or ExtraDepot's depot it's full
	  * @throws UnusableCardException if ExtraDepot is not enabled
	  */
	public boolean addResource(Resource tmp) throws UnusableCardException{return false;}

	 /**Removes a Resource from ExtraDepot's depot
	  * @param tmp a Resource instance
	  * @return true if Resource has been removed, false if not or it's not of the ExtraDepot's Resource type
	  * @throws UnusableCardException if ExtraDepot is not enabled
	  */
	public boolean removeResource(Resource tmp) throws UnusableCardException {return false;}

	 /**Returns the ExtraProd input Resource to do production
	  * @return a Resource ArrayList, null if it's not ExtraProd
	  * @throws UnusableCardException if ExtraProd is not active
	  */
	public Resource getExtraProdInput() throws UnusableCardException { return null;}

	 /**Returns the ExtraProd production
	  * @return a Resource ArrayList, null if it's not ExtraProd
	  */
	public ArrayList<Resource> production(){return null;}

	 /**Sets the free choice Resource in ExtraProd's production
	  * @param chosenOutput a Resource
	  */
	public void setChosenOutput(Resource chosenOutput){}

	/**Returns a ExtraDepot's depot copy
	 * @return a Resource ArrayList if Card is ExtraDepot, null if not
	 */
	public ArrayList<Resource> getExtraWarehouse(){return null;}

	/**Converts the LeaderCard in a LightLeaderCard copy for LightModel
	  * @return a LightLeaderCard instance
	  */
	 abstract public LightLeaderCard convert();

}