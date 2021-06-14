package GUI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.File;

public class SceneConstructor {
    Gui gui;

    public SceneConstructor(Gui gui) {
        this.gui = gui;
        //Loading images
        String basePath = "file:src/main/resources";
        Image image1 = new Image(basePath+ "/images/background.jpg");
        Image image2 = new Image(basePath+ "/images/market.png");
        Image image3 = new Image(basePath+ "/images/playerboard.jpg");
        Image image4 = new Image(basePath+ "/images/marketIcon.jpg");
        //Images-->ImageViews
        ImageView backgroundIMG = new ImageView(image1);
        ImageView marketIMG = new ImageView(image2);
        ImageView playerboardIMG = new ImageView(image3);
        ImageView playerboard2IMG = new ImageView(image3);
        ImageView playerboard3IMG = new ImageView(image3);
        ImageView playerboard4IMG = new ImageView(image3);
        ImageView marketiconIMG = new ImageView(image4);
        //Setting IMGViews
        backgroundIMG.setFitWidth(839);
        backgroundIMG.setFitHeight(600);
        //backgroundIMG.fitWidthProperty().bind(window.widthProperty());
        //backgroundIMG.fitHeightProperty().bind(window.heightProperty());
        playerboardIMG.setFitWidth(622);
        playerboardIMG.setFitHeight(445);
        marketIMG.setFitWidth(622);
        marketIMG.setFitHeight(445);
        marketiconIMG.setFitWidth(130);
        marketiconIMG.setFitHeight(130);
        playerboard2IMG.setFitWidth(622);
        playerboard2IMG.setFitHeight(445);
        playerboard3IMG.setFitWidth(622);
        playerboard3IMG.setFitHeight(445);
        playerboard4IMG.setFitWidth(622);
        playerboard4IMG.setFitHeight(445);
        //Creating Buttons
        gui.sendBTN = new Button("SEND");
        gui.singleBTN = new Button("Single Player");
        gui.createBTN = new Button("Create Multi Player Lobby");
        gui.joinBTN = new Button("Join Multi Player Lobby");
        gui.player1BTN = new Button();
        gui.player2BTN = new Button();
        gui.player3BTN = new Button();
        gui.player4BTN = new Button();
        gui.marketBTN = new Button();
        gui.developmentBTN = new Button();
        gui.leaderBTN = new Button();
        gui.endTurnBTN = new Button();
        //Setting Buttons
        gui.player1BTN.setPrefWidth(75);
        gui.player2BTN.setPrefWidth(75);
        gui.player3BTN.setPrefWidth(75);
        gui.player4BTN.setPrefWidth(75);
        gui.marketBTN.setPrefSize(150,150);
        gui.marketBTN.setGraphic(marketiconIMG);
        gui.developmentBTN.setPrefSize(150,150);
        gui.leaderBTN.setPrefSize(150,100);
        gui.endTurnBTN.setPrefSize(75,50);
        gui.sendBTN.setPrefSize(150,75);
        gui.singleBTN.setPrefSize(175,75);
        gui.createBTN.setPrefSize(175,75);
        gui.joinBTN.setPrefSize(175,75);
        //leaderBTN.setDisable(true);
        //Setting Labels
        gui.title = new Label("Maestri del Rinascimento");
        gui.title.setFont(Font.font("Monotype Corsiva", 36));
        gui.insert = new Label("Insert your Username");
        //Setting TextField
        gui.usernameField = new TextField();
        //Setting ListViews
        gui.playersList = new ListView<>();
        gui.playersList.getItems().addAll("Mario", "gerryscotti00","AGACASH","theodore");
        ListView<String> playersList1 = new ListView<>();
        playersList1.getItems().addAll("Mario", "gerryscotti00","AGACASH","theodore");
        ListView<String> playersList2 = new ListView<>();
        playersList2.getItems().addAll("Mario", "gerryscotti00","AGACASH","theodore");
        ListView<String> playersList3 = new ListView<>();
        playersList3.getItems().addAll("Mario", "gerryscotti00","AGACASH","theodore");
        //Setting Panes
        gui.mainPane = new AnchorPane();
        gui.bPane = new BorderPane();
        gui.vbox = new VBox();
        gui.hboxN = new HBox();
        gui.hboxS = new HBox();
        gui.player1Pane = new BorderPane();
        gui.marketPane = new AnchorPane();
        gui.leaderPane = new AnchorPane();
        gui.loginBox = new VBox();
        gui.gameSelBox = new VBox();
        gui.lobbyAPane = new VBox();
        gui.lobbyPane = new ScrollPane();
        gui.lobbiesBox = new VBox();
        gui.developmentPane = new AnchorPane();
        gui.player2Pane = new AnchorPane();
        gui.player3Pane = new AnchorPane();
        gui.player4Pane = new AnchorPane();
        //Setting Canvas
        gui.marbleCanvas = new Canvas(600,400);
        gui.leaderCanvas = new Canvas(600,400);
        gui.devCanvas = new Canvas(600,440);
        gui.player1Canvas = new Canvas(600,400);
        //Canvas drawings experiments
        gui.gc1 = gui.leaderCanvas.getGraphicsContext2D();
        gui.gc1.drawImage(IMGLoader.getImage(basePath+"/LEADERS/LeaderD1.png"), -100,30,500, 375);
        gui.gc1.drawImage(IMGLoader.getImage(basePath+"/LEADERS/LeaderD1.png"), 165,30,500, 375);
        gui.gc2 = gui.devCanvas.getGraphicsContext2D();
        gui.gc2.drawImage(IMGLoader.getImage(basePath+"/DEVBOARD/Blue5.png"),0,-17,245,185);
        gui.gc2.drawImage(IMGLoader.getImage(basePath+"/DEVBOARD/Blue5.png"),0,130,245,185);
        gui.gc2.drawImage(IMGLoader.getImage(basePath+"/DEVBOARD/Blue5.png"),0,278,245,185);
        gui.gc2.drawImage(IMGLoader.getImage(basePath+"/DEVBOARD/Blue5.png"),130,278,245,185);
        gui.gc2.drawImage(IMGLoader.getImage(basePath+"/DEVBOARD/Blue5.png"),260,278,245,185);
        gui.gc2.drawImage(IMGLoader.getImage(basePath+"/DEVBOARD/Blue5.png"),390,278,245,185);
        gui.gc = gui.marbleCanvas.getGraphicsContext2D();
        gui.gc.drawImage(IMGLoader.getImage(basePath+"/MARBLES/greyMarble.png"),105,100,100,100);
        gui.gc.drawImage(IMGLoader.getImage(basePath+"/MARBLES/blueMarble.png"), 165, 100, 100, 100);
        gui.gc.drawImage(IMGLoader.getImage(basePath+"/MARBLES/whiteMarble.png"), 105, 155, 100, 100);
        gui.gc.drawImage(IMGLoader.getImage(basePath+"/MARBLES/greyMarble.png"), 105, 210, 100, 100);
        gui.gc.drawImage(IMGLoader.getImage(basePath+"/MARBLES/redMarble.png"), 225, 100, 100, 100);
        gui.gc.drawImage(IMGLoader.getImage(basePath+"/MARBLES/purpleMarble.png"), 165, 155, 100, 100);
        gui.gc.drawImage(IMGLoader.getImage(basePath+"/MARBLES/yellowMarble.png"), 225, 155, 100, 100);
        gui.gc.drawImage(IMGLoader.getImage(basePath+"/MARBLES/greyMarble.png"), 285, 100, 100, 100);
        gui.player1GC = gui.player1Canvas.getGraphicsContext2D();
        gui.player1GC.drawImage(IMGLoader.getImage(basePath+"/DEVBOARD/Blue5.png"),168,200,255,191);
        gui.player1GC.drawImage(IMGLoader.getImage(basePath+"/DEVBOARD/Blue5.png"),288,200,255,191);
        gui.player1GC.drawImage(IMGLoader.getImage(basePath+"/DEVBOARD/Blue5.png"),408,200,255,191);
        //Adding to LoginBox
        gui.loginBox.setAlignment(Pos.CENTER);
        gui.loginBox.setPadding(new Insets(0,150,0,150));
        gui.loginBox.setPrefSize(839,600);
        gui.loginBox.setSpacing(30);
        gui.loginBox.getChildren().addAll(gui.title,gui.insert,gui.usernameField,gui.sendBTN);
        //Adding to Game Selection Box
        gui.gameSelBox.setAlignment(Pos.CENTER);
        gui.gameSelBox.setPrefSize(839,600);
        gui.gameSelBox.setSpacing(30);
        gui.gameSelBox.getChildren().addAll(gui.singleBTN,gui.createBTN,gui.joinBTN);
        //Adding to Lobbies Box/Pane
        gui.lobbyAPane.setPrefSize(839,600);
        gui.lobbyAPane.setPadding(new Insets(70,270,70,269));
        gui.playersList.setPrefHeight(120);
        //playersList.setOnMouseClicked(e->playersList.getItems().add(usernameField.getText()));
        gui.lobbiesBox.setSpacing(30);
        gui.lobbiesBox.setPrefWidth(300);
        gui.lobbiesBox.getChildren().addAll(gui.playersList,playersList1,playersList2,playersList3);
        gui.lobbyPane.setPrefSize(300,600);
        gui.lobbyPane.setContent(gui.lobbiesBox);
        gui.lobbyAPane.getChildren().add(gui.lobbyPane);
        //Adding to VBOX
        gui.vbox.setPrefSize(217,446);
        gui.vbox.setAlignment(Pos.CENTER);
        gui.vbox.setSpacing(15);
        gui.vbox.getChildren().addAll(gui.marketBTN,gui.developmentBTN,gui.leaderBTN);
        //Adding to Northern HBOX
        gui.hboxN.setPrefSize(839,65);
        gui.hboxN.setSpacing(150);
        gui.hboxN.setAlignment(Pos.CENTER);
        gui.hboxN.getChildren().addAll(gui.player1BTN,gui.player2BTN,gui.player3BTN,gui.player4BTN);
        //Adding to Southern HBOX
        gui.hboxS.setPrefSize(839,90);
        gui.hboxS.setPadding(new Insets(15,0,0,700));
        gui.hboxS.getChildren().add(gui.endTurnBTN);
        //Adding to PlayerPane
        gui.player1Pane.setPrefSize(622,445);
        gui.player1Pane.getChildren().addAll(playerboardIMG, gui.player1Canvas);
        //Adding to Market Pane
        gui.marketPane.setPrefSize(622,445);
        gui.marketPane.getChildren().addAll(marketIMG, gui.marbleCanvas);
        //Adding to LeaderPane
        gui.leaderPane.getChildren().add(gui.leaderCanvas);
        //Adding to Development Pane
        gui.developmentPane.getChildren().add(gui.devCanvas);
        //Setting opponents panes
        gui.player2Pane.setPrefSize(622,445);
        gui.player3Pane.setPrefSize(622,445);
        gui.player4Pane.setPrefSize(622,445);
        gui.player2Pane.getChildren().add(playerboard2IMG);
        gui.player3Pane.getChildren().add(playerboard3IMG);
        gui.player4Pane.getChildren().add(playerboard4IMG);
        //Adding to BP
        gui.bPane.setLeft(gui.vbox);
        gui.bPane.setTop(gui.hboxN);
        gui.bPane.setBottom(gui.hboxS);
        //Adding to AP
        gui.mainPane.getChildren().addAll(backgroundIMG, gui.loginBox);
        gui.player1GC.drawImage(IMGLoader.getImage(basePath+"/RESOURCES/coin.png"),60,180,50,50);//1
        gui.player1GC.drawImage(IMGLoader.getImage(basePath+"/RESOURCES/coin.png"),40,215,50,50);//2,1
        gui.player1GC.drawImage(IMGLoader.getImage(basePath+"/RESOURCES/coin.png"),70,215,50,50);//2,2
        gui.player1GC.drawImage(IMGLoader.getImage(basePath+"/RESOURCES/coin.png"),30,255,50,50);//3,1
        gui.player1GC.drawImage(IMGLoader.getImage(basePath+"/RESOURCES/coin.png"),55,255,50,50);//3,2
        gui.player1GC.drawImage(IMGLoader.getImage(basePath+"/RESOURCES/coin.png"),80,255,50,50);//3,3
        gui.player1GC.drawImage(IMGLoader.getImage(basePath+"/RESOURCES/coin.png"),15,320,50,50);//strongbox' first
    }
}
