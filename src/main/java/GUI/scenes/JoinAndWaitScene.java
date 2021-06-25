package GUI.scenes;

import GUI.GUI;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import network.server.Lobby;

import java.awt.event.ActionEvent;
import java.net.Socket;
import java.util.ArrayList;

public class JoinAndWaitScene {

    @FXML
    ListView<Lobby> lobbyList;

    @FXML
    public void lobbyElementClicked(MouseEvent contextMenuEvent) {
        Lobby lobby = lobbyList.getSelectionModel().getSelectedItem();
        if(lobby != null){
            GUI.getInstance().getController().joinLobbyById(lobby.getId());
        }
    }

    public void loadLobbies(ArrayList<Lobby> lobbies){
        lobbyList.getItems().addAll(lobbies);
    }
}
