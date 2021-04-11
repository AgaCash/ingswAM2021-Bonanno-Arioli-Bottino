package marbles;
import cards.LeaderCard;
import resources.Faith;

public class RedMarble extends Marble{
	/**
	 * RedMarble Class extends the abstract class Marble
	 * It has an only the convertMarble method to return a Faith after the picked up from the Market Board.
	 * @param card ignored in this Marble
	 */
	public Faith convertMarble(LeaderCard card){
		return new Faith();
	}
}