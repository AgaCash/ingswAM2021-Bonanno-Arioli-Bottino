package GUI.scenes;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class WaitingRoomScene {
    @FXML
    ListView<String> playerList;

    public void addPlayer(String username){
        playerList.getItems().add(username);
    }
}
