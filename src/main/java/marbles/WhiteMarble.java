package marbles;
import cards.LeaderCard;
import cards.WhiteConverter;
import resources.Resource;


public class WhiteMarble extends Marble{
	/**
	 * WhiteMarble Class extends the abstract class Marble
	 * It returns a new instance of a Resource only if the LeaderCard @card is enabled and implements
	 * the WhiteConverter ability, else it returns a null.
	 * @param extraResource the resource returned by the WhiteMarble
	 * @param card the LeaderCard who activate the WhiteConverter ability
	 * @param card.whichResource() it returns the extra Resource the LeaderCard
	 */
	private Resource extraResource;

	public Resource convertMarble(LeaderCard card){
		extraResource= null;
		if(card!=null && (card instanceof WhiteConverter))
			extraResource=card.whichResource();
		return extraResource;
	}
}