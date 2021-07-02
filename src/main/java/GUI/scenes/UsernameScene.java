package GUI.scenes;

import GUI.GUI;
import GUI.GuiStart;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

/**
 * the graphic scene that asks and gets the username to connect them to the server
 */
public class UsernameScene {

    @FXML
    Button sendUserBTN;
    @FXML
    TextField username;

    /** the graphic getting of the username to send to the server
     * @param event the button click event that sends the parameters
     */
    @FXML
    private void sendUser(ActionEvent event){
        String usernameString;
        usernameString = username.getText();
        GUI.getInstance().getController().setUsername(usernameString);
    }
}
