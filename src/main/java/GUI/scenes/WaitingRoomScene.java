package GUI.scenes;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.util.ArrayList;

/**
 * GUI controller for the FXML file to sshow in the waiting room scene
 */
public class WaitingRoomScene {
    @FXML
    ListView<String> playerList;

    public void addPlayers(ArrayList<String> usernames){
        playerList.getItems().addAll(usernames);
    }
}
