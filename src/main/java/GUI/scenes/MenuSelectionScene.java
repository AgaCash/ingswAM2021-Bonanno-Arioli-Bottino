package GUI.scenes;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class MenuSelectionScene {
    public MenuSelectionScene(){
        Button singleBTN = new Button("Single Player");
        Button createBTN = new Button("Create Multi Player Lobby");
        Button joinBTN = new Button("Join Multi Player Lobby");
        singleBTN.setPrefSize(175,75);
        createBTN.setPrefSize(175,75);
        joinBTN.setPrefSize(175,75);

        VBox gameSelBox = new VBox();
        gameSelBox.setAlignment(Pos.CENTER);
        gameSelBox.setPrefSize(839,600);
        gameSelBox.setSpacing(30);
        gameSelBox.getChildren().addAll(singleBTN,createBTN,joinBTN);
    }
}
