package GUI;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AlertBox {
    public static void lobbyAlert(Gui gui, int i){
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        Label label = new Label("Do You Want to join LOBBY " + i + "?");
        Button confirm = new Button("Yes");
        Button deny = new Button("No");
        HBox hbox = new HBox();
        VBox vbox = new VBox();
        hbox.getChildren().addAll(confirm,deny);
        vbox.getChildren().addAll(label,hbox);

        deny.setOnAction(e-> window.close());
        confirm.setOnAction(e ->{
            gui.mainPane.getChildren().remove(gui.lobbyAPane);
            gui.mainPane.getChildren().add(gui.bPane);
            window.close();
        });

        Scene scene = new Scene(vbox);
        window.setScene(scene);
        window.showAndWait();
    }

    public void illegalAlert(Gui gui){
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        Label label = new Label("You cannot do this action, please retry");
        Button gotcha = new Button("Ok");
        VBox vbox = new VBox();
        vbox.getChildren().addAll(label,gotcha);

        gotcha.setOnAction(e-> window.close());
        Scene scene = new Scene(vbox);
        window.setScene(scene);
        window.showAndWait();
    }
}
