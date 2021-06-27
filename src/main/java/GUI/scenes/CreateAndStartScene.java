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

public class CreateAndStartScene implements GenericScene{


    @FXML
    Button startButton;
    @FXML
    Label titleLabel;
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

    public void addPlayers(ArrayList<String> usernames){
        playersList.getItems().addAll(usernames);
    }

    public void setTitleLabel(String text) {
        this.titleLabel.setText(text);
    }

    public void setButtonVisibility(boolean isVisible){
        startButton.setVisible(isVisible);
    }
}
