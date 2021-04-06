package marbles;
import cards.LeaderCard;
import resources.*;

import java.util.ArrayList;

public class GreyMarble extends Marble{

	public Stone convertMarble(ArrayList<LeaderCard> cardCouple){
		Stone st = new Stone();
		st.create();
		return st;
	}
}