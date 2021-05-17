package network.messages.gameMessages;

import network.messages.MessageType;

public class LeaderCardActivationResponse extends GameMessage{

        public LeaderCardActivationResponse(String username) {
            super(username, MessageType.LEADERCARDUPDATE);
        }
        //TODO: da fare

}
