package network.messages.notifies;

import network.messages.MessageType;

public class FailedActionNotify extends NotifyMessage{

    public FailedActionNotify(String username, String message){
        super(username, MessageType.FAILEDACTIONNOTIFY, message);
    }
}
