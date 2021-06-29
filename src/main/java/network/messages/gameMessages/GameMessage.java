package network.messages.gameMessages;

import clientController.LightController;
import controller.Controller;
import network.messages.Message;
import network.messages.MessageType;
import view.VirtualClient;

/** Class implementing all the Requests and Responses in the Game session.
 * It directly calls Controller and LightController methods
 */
public abstract class GameMessage extends Message {
    /**GameMessage constructor
     * @param username the Sender's username
     * @param messageType a MessageType instance
     */
    public GameMessage(String username, MessageType messageType) {
        super(username, messageType);
    }

    /**This method runs the LightController call to methods to update LightModel after the GameMessage's request success (or fail)
     * @param controller the LightController in Client
     */
    public void executeCommand(LightController controller){
        //System.out.println("default executeCommand CONTROLLER");
        //this is there where be LightModel updates or error showing
    }

    /**This method runs the Controller call to methods to execute the action requested by GameMessage
     * @param controller the Controller in Server
     * @param client the VirtualClient corresponding to Player who Response will be sent
     */
    public void executeCommand(Controller controller, VirtualClient client){
        //this will be executed by server
    }




}
