package GUI;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;

public class Gui extends Application {

    Stage window;
    Scene mainScene;
    AnchorPane mainPane;
    BorderPane bPane;
    VBox vbox;
    HBox hboxN;
    HBox hboxS;
    BorderPane player1Pane;
    AnchorPane marketPane;
    AnchorPane leaderPane;
    VBox loginBox;
    VBox gameSelBox;
    VBox lobbyAPane;
    ScrollPane lobbyPane;
    VBox lobbiesBox;
    AnchorPane developmentPane;
    AnchorPane player2Pane;
    AnchorPane player3Pane;
    AnchorPane player4Pane;
    GuiController controller;
    SceneConstructor sc;
    Drawer drawer;
    Button sendBTN;
    Button singleBTN;
    Button createBTN;
    Button joinBTN;
    Button player1BTN;
    Button player2BTN;
    Button player3BTN;
    Button player4BTN;
    Button marketBTN;
    Button developmentBTN;
    Button leaderBTN;
    Button endTurnBTN;
    Label title;
    Label insert;
    Canvas marbleCanvas;
    Canvas leaderCanvas;
    Canvas devCanvas;
    Canvas player1Canvas;
    GraphicsContext gc;
    GraphicsContext gc1;
    GraphicsContext gc2;
    GraphicsContext player1GC;
    TextField usernameField;
    ListView<String> playersList;

    public Gui(){
        this.controller = new GuiController(this);
        this.sc = new SceneConstructor(this);
        this.drawer = new Drawer(this);
    }

    @Override
    public void start(Stage stage) throws IOException{
        Gui gui = new Gui();
        window = stage;

        stage.setResizable(false);
        Button button = new Button();
        button.setOnAction((actionEvent)->{
            System.out.println(actionEvent.getEventType());
        });

        //Button Actions
        player1BTN.setOnAction( e -> bPane.setCenter(player1Pane));
        marketBTN.setOnAction(e -> bPane.setCenter(marketPane));
        developmentBTN.setOnAction(e->bPane.setCenter(developmentPane));
        leaderBTN.setOnAction( e-> bPane.setCenter(leaderPane));
        player2BTN.setOnAction(e->bPane.setCenter(player2Pane));
        player3BTN.setOnAction(e->bPane.setCenter(player3Pane));
        player4BTN.setOnAction(e->bPane.setCenter(player4Pane));
        //Setting Actions
        sendBTN.setOnAction(actionEvent -> {
            //System.out.println(actionEvent);
            mainPane.getChildren().remove(loginBox);
            mainPane.getChildren().add(gameSelBox);
            player1BTN.setText(usernameField.getText());
        });
        singleBTN.setOnAction(e-> controller.buttonEvent("single"));
        joinBTN.setOnAction(e-> controller.buttonEvent("join"));
        playersList.setOnMouseClicked(e->AlertBox.lobbyAlert(this,1));

        player1Pane.addEventFilter(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            System.out.println(mouseEvent.getX() + ", "+ mouseEvent.getY());
            double x = mouseEvent.getX();
            double y = mouseEvent.getY();
            controller.playerEvent(x,y,player1GC);
        });
        marketPane.addEventFilter(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            double x = mouseEvent.getX();
            double y = mouseEvent.getY();
            controller.marketAction(x, y, gc);
        });
        developmentPane.addEventFilter(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            double x = mouseEvent.getX();
            double y = mouseEvent.getY();
           controller.devEvent(x,y,gc2);
        });
        leaderPane.addEventFilter(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            double x = mouseEvent.getX();
            double y = mouseEvent.getY();
            controller.leaderEvent(x,y,gc1);
        });
        //Setting scene
        mainScene = new Scene (mainPane, 839,600);
        window.setScene(mainScene);
        stage.show();
    }

    //TODO drawImages liv2,3 nel playerboard
    //TODO creare le finestre pop-up per uscita gioco e illegal choices
    //TODO collegare tutto

    public static void main(String[]args) {
        launch(args);
    }
}
