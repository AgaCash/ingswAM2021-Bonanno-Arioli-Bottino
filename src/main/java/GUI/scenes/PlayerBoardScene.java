package GUI.scenes;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class PlayerBoardScene {

    @FXML
    Pane pane;

    @FXML
    private void getClick(MouseEvent event){
        double x = event.getX();
        double y = event.getY();
    }
}
