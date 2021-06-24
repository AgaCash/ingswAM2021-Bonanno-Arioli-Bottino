package GUI.scenes;

import javafx.geometry.Insets;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

public class JoinAndWaitScene {
    public JoinAndWaitScene(){
        ListView playersList = new ListView<>();
        playersList.getItems().addAll("LOBBY 1", "P1","P2","P3");
        ListView<String> playersList1 = new ListView<>();
        playersList1.getItems().addAll("LOBBY 2", "P1","P2","P3");
        ListView<String> playersList2 = new ListView<>();
        playersList2.getItems().addAll("Mario", "gerryscotti00","AGACASH","theodore");
        ListView<String> playersList3 = new ListView<>();
        playersList3.getItems().addAll("Mario", "gerryscotti00","AGACASH","theodore");

        VBox lobbyAPane = new VBox();
        ScrollPane lobbyPane = new ScrollPane();
        VBox lobbiesBox = new VBox();

        lobbyAPane.setPrefSize(839,600);
        lobbyAPane.setPadding(new Insets(70,270,70,269));
        playersList.setPrefHeight(120);
        //playersList.setOnMouseClicked(e->playersList.getItems().add(usernameField.getText()));
        lobbiesBox.setSpacing(30);
        lobbiesBox.setPrefWidth(300);
        lobbiesBox.getChildren().addAll(playersList,playersList1,playersList2,playersList3);
        lobbyPane.setPrefSize(300,600);
        lobbyPane.setContent(lobbiesBox);
        lobbyAPane.getChildren().add(lobbyPane);
    }
}
