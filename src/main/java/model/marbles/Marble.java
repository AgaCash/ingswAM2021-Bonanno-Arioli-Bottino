package model.marbles;

import model.resources.Resource;

public enum Marble{
	BLUE,
	GREY,
	PURPLE,
	RED,
	WHITE,
	YELLOW;

	public Resource convertMarble(Resource convertWhiteMarble){
		switch(this){
			case BLUE: return Resource.SHIELD;
			case GREY: return Resource.STONE;
			case PURPLE: return Resource.SERVANT;
			case RED: return Resource.FAITH;
			case YELLOW: return Resource.COIN;
			//final reachable case
			case WHITE : {
				if(convertWhiteMarble != null)
					return convertWhiteMarble;
			}
		}
		return null;
	}



}
