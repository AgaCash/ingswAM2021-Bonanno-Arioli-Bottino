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

public class UsernameScene {

    @FXML
    Button sendUserBTN;
    @FXML
    TextField username;

    @FXML
    private void sendUser(ActionEvent event){
        String usernameString;
        usernameString = username.getText();
        GUI.getInstance().getController().setUsername(usernameString);
    }



    /*
    public UsernameScene() {
        Button sendBTN = new Button("SEND");
        sendBTN.setPrefSize(150,75);
        //Setting TextField
        TextField usernameField = new TextField();
        //Setting Labels
        Label title = new Label("Maestri del Rinascimento");
        title.setFont(Font.font("Monotype Corsiva", 36));
        Label insert = new Label("Insert your Username");
        //Adding to LoginBox
        VBox loginBox = new VBox();
        loginBox.setAlignment(Pos.CENTER);
        loginBox.setPadding(new Insets(0, 150, 0, 150));
        loginBox.setPrefSize(839, 600);
        loginBox.setSpacing(30);
        loginBox.getChildren().addAll(title, insert, usernameField, sendBTN);


    }

     */
}
