package marbles;
import cards.LeaderCard;
import resources.*;

import java.util.ArrayList;

public class PurpleMarble extends Marble{

	public Servant convertMarble(ArrayList<LeaderCard> cardCouple){
		Servant ser = new Servant();
		ser.create();
		return ser;
	}
}