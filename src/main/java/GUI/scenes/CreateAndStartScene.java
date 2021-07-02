package GUI.scenes;

import GUI.GUI;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import model.player.Player;

import java.util.ArrayList;

/**
 * the graphic scene to show the lobby waiting room to the lobby creator, before his decision to start the game
 */
public class CreateAndStartScene implements GenericScene{


    @FXML
    Button startButton;
    @FXML
    Label titleLabel;
    @FXML
    ListView<String> playersList;

    /** handles the start game button click and the starting of the game
     * @param actionEvent the startButton click
     */
    @FXML
    public void sendStartSignal(ActionEvent actionEvent) {
        Platform.runLater(()->{
            GUI.getInstance().getController().sendSignalMultiPlayerGame();
        });
    }

    /** adds a player graphically into the lobby
     * @param username player's username to show
     */
    public void addPlayer(String username){
        playersList.getItems().add(username);
    }

    /** adds the player into the listview
     * @param usernames the player's usernames to show
     */
    public void addPlayers(ArrayList<String> usernames){
        playersList.getItems().addAll(usernames);
    }

    /**set the title text of the waiting scene
     * @param text the text to show in the title
     */
    public void setTitleLabel(String text) {
        this.titleLabel.setText(text);
    }

    /** handles the start button visibility, in order to show it only to the lobby creator
     * @param isVisible boolean to express if the starting button will be visible
     */
    public void setButtonVisibility(boolean isVisible){
        startButton.setVisible(isVisible);
    }
}
