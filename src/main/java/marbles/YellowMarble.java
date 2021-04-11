package marbles;
import cards.LeaderCard;
import resources.Coin;

public class YellowMarble extends Marble{
	/**
	 * YellowMarble Class extends the abstract class Marble
	 * It has an only the convertMarble method to return a Coin after the picked up from the Market Board.
	 * @param card ignored in this Marble
	 */
	public Coin convertMarble(LeaderCard card){
		 return new Coin();
	}
}