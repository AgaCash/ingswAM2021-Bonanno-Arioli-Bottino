package network.messages;

/**Class that implements the Message communication. It contains the 2 fundamental information:
 * - Username: the Sender's username
 * - MessageType: a Class implementing the Type of Message. By the type of Message, Server does different actions.
 * Message it's specialized in various subclasses contained in the messages package
 */
public abstract class Message {
    private String username;
    private MessageType messageType;

    /**Message constructor
     * @param username the Sender username
     * @param messageType the type of Message
     */
    public Message(String username, MessageType messageType) {
        this.username = username;
        this.messageType = messageType;
    }

    /**Returns the Sender username
     * @return a String
     */
    public String getUsername() {
        return username;
    }

    /**Returns the type of Message
     * @return a MessageType instance
     */
    public MessageType getMessageType() {
        return messageType;
    }

    /**for testing
     * @return a String
     */
    @Override
    public String toString() {
        return "Message{" +
                "username='" + username + '\'' +
                ", messageType=" + messageType +
                '}';
    }
}
