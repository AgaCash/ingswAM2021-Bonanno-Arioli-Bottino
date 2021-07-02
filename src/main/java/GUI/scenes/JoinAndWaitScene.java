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

/**
 * the active lobbies list showed to the players who clicks join multiplayer
 */
public class JoinAndWaitScene {

    @FXML
    ListView<Lobby> lobbyList;

    /** handles the user click on a lobby to join it
     * @param contextMenuEvent the clicking event made on the list view
     */
    @FXML
    public void lobbyElementClicked(MouseEvent contextMenuEvent) {
        Lobby lobby = lobbyList.getSelectionModel().getSelectedItem();
        if(lobby != null){
            GUI.getInstance().getController().joinLobbyById(lobby.getId());
        }
    }

    /** loads the lobbies to show to the players
     * @param lobbies the list of lobbies to show on to the list view
     */
    public void loadLobbies(ArrayList<Lobby> lobbies){
        lobbyList.getItems().addAll(lobbies);
    }
}
