package marbles;
import cards.LeaderCard;
import resources.*;

import java.util.ArrayList;

public class BlueMarble extends Marble{

	public Shield convertMarble(ArrayList<LeaderCard> cardCouple){
		Shield sh = new Shield();
		sh.create();
		return sh;
	}
}