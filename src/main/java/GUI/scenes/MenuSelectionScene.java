package GUI.scenes;

import GUI.GUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

/** Class that graphically handles the buttons on the menu selection scene
 * in which the player has to choose the game mode
 */
public class MenuSelectionScene {

    @FXML
    Button createMultiBTN;
    @FXML
    Button joinMultiBTN;
    @FXML
    Button singleBTN;

    /** it creates a multiplayer lobby after the button click
     * @param event the button click event
     */
    @FXML
    private void createMultiLobby(ActionEvent event){
        GUI.getInstance().getController().createMultiLobby();
    }

    /** it shows the active lobbies list to the player who clicked the join button
     * @param event the button click event
     */
    @FXML
    private void joinMultiLobby(ActionEvent event){
        GUI.getInstance().getController().getLobbyList();
    }

    /** it starts the single player game mode situation showing the selection leader and resources scene
     * @param event the button click event
     */
    @FXML
    private void singlePlayerGame(ActionEvent event){
        GUI.getInstance().getController().createSinglePlayerLobby();
    }

}
