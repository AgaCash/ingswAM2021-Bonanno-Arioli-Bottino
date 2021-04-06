package marbles;
import resources.Resource;
import cards.LeaderCard;
import java.util.ArrayList;

abstract public class Marble{
	abstract public Resource convertMarble(ArrayList<LeaderCard> cardCouple);
}
