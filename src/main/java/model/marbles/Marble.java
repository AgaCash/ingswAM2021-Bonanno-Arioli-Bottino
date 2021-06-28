package model.marbles;

import model.resources.Resource;

/**Enum representing marbles in Game. It has 6 values
 * BLUE, GREY, PURPLE, RED, WHITE, YELLOW
 */
public enum Marble{
	BLUE,
	GREY,
	PURPLE,
	RED,
	WHITE,
	YELLOW;

	/**Converts the Marble in its corresponding Resource
	 * @param convertWhiteMarble the WhiteMarble corresponding Resource. It it's null, WhiteMarble will be converted in nothing
	 * @returna a Resource instance
	 */
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
