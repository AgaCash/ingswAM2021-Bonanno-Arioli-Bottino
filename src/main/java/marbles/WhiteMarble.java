package marbles;
import resources.*;
import cards.*;
import java.util.ArrayList;
import java.util.Scanner;

public class WhiteMarble extends Marble{
	private Resource extraResource = null;

	private Resource chooseResource(ArrayList<Resource> res){
		//method to choose what resource will be converted by white marble
		//temporary method
		int count = 0;
		int tmp;
		Scanner console = new Scanner(System.in);

		System.out.println("Choose resource from 0 to "+res.size()+"\n0 to no activate ability\n");
		for(Resource r : res){
			count++;
			System.out.println(count+" to "+r.getClass().getSimpleName()+"\n");
		}
		tmp = console.nextInt();
		if(tmp>0 && tmp<=res.size())
			return res.get(--tmp);
		return null;
	}


	private void checkExtraAbility(ArrayList<LeaderCard> couple){
		ArrayList<Resource> resDisp = new ArrayList<>();

		for(LeaderCard card : couple)
			if(card.isEnabled() && (card instanceof WhiteConverter)){
				resDisp.add(card.whichResource());
			}
		if(resDisp.size()>0)
			extraResource = 	chooseResource(resDisp);
	}

	public Resource convertMarble(ArrayList<LeaderCard> cardCouple){
			extraResource=null;
			checkExtraAbility(cardCouple);
			if(extraResource!=null)
				extraResource.create();
			return extraResource;
	}
}