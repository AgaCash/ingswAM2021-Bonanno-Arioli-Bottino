package GUI.scenes;

import GUI.GUI;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import model.player.Player;

public class CreateAndStartScene implements GenericScene{

    @FXML
    ListView<String> playersList;

    @FXML
    public void sendStartSignal(ActionEvent actionEvent) {
        Platform.runLater(()->{
            GUI.getInstance().getController().sendSignalMultiPlayerGame();
        });
    }

    public void addPlayer(String username){
        playersList.getItems().add(username);
    }
}
