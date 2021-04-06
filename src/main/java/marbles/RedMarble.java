package marbles;
import cards.LeaderCard;
import resources.*;

import java.util.ArrayList;

public class RedMarble extends Marble{

	public Faith convertMarble(ArrayList<LeaderCard> cardCouple){
		Faith fa = new Faith();
		fa.create();
		return fa;
	}
}