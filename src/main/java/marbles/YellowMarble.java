package marbles;
import cards.LeaderCard;
import resources.*;

import java.util.ArrayList;

public class YellowMarble extends Marble{

	public Coin convertMarble(ArrayList<LeaderCard> cardCouple){
		Coin co = new Coin();
		co.create();
		return co;
	}
}