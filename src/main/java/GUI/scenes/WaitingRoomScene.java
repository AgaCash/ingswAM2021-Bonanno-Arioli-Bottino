package GUI.scenes;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.util.ArrayList;

public class WaitingRoomScene {
    @FXML
    ListView<String> playerList;

    public void addPlayers(ArrayList<String> usernames){
        playerList.getItems().addAll(usernames);
    }
}
