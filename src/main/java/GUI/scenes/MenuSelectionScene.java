package GUI.scenes;

import GUI.GUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class MenuSelectionScene {

    @FXML
    Button createMultiBTN;
    @FXML
    Button joinMultiBTN;
    @FXML
    Button singleBTN;

    @FXML
    private void createMultiLobby(ActionEvent event){
        GUI.getInstance().getController().createMultiLobby();
    }

    @FXML
    private void joinMultiLobby(ActionEvent event){
        GUI.getInstance().getController().getLobbyList();
    }

    @FXML
    private void singlePlayerGame(ActionEvent event){
        GUI.getInstance().getController().createSinglePlayerLobby();
    }

}
