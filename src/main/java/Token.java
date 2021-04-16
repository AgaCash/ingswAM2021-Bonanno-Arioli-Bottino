import colour.Colour;

public class Token {
    private int tokenID;
    private Colour colour;
    private int num;

    public int getTokenID() {
        return tokenID;
    }

    public void setTokenID(int tokenID) {
        this.tokenID = tokenID;
    }

    public Colour getColour() {
        return colour;
    }

    public void setColour(Colour colour) {
        this.colour = colour;
    }

    public void execute(){

        // executes the action declared by the token, when picked

    }
}
