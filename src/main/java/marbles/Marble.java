package marbles;

import cards.LeaderCard;
import resources.Resource;

public enum Marble{
	BLUE,
	GREY,
	PURPLE,
	RED,
	WHITE,
	YELLOW;

	public Resource convertMarble(LeaderCard card){
		switch(this){
			case WHITE : {
				Resource extraResource= null;
				if(card!=null && card.isWhiteConverter()) {
					extraResource = card.whichResource();
				}
				return extraResource;
			}
			case BLUE: return Resource.SHIELD;
			case GREY: return Resource.STONE;
			case PURPLE: return Resource.SERVANT;
			case RED: return Resource.FAITH;
			case YELLOW: return Resource.COIN;
		}
		return null;
	}



}
