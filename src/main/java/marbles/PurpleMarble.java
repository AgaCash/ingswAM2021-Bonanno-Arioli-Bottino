package marbles;
import cards.LeaderCard;
import resources.Servant;

public class PurpleMarble extends Marble{
	/**
	 * PurpleMarble Class extends the abstract class Marble
	 * It has an only the convertMarble method to return a Servant after the picked up from the Market Board.
	 * @param card ignored in this Marble
	 */
	public Servant convertMarble(LeaderCard card){
		return new Servant();
	}
}