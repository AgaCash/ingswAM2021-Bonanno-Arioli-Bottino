package GUI;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

public class FXMLLoaderCustom {
    private Pane view;

    public Pane getPage(String fileName){
        try {
            //URL fileURL = GuiStart.class.getResource("/FXMLFiles/" + fileName + ".fxml");
            //FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXMLFiles/" + fileName + ".fxml"));
            view = FXMLLoader.load(getClass().getResource("/FXMLFiles/" + fileName + ".fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return view;
    }

}



/*
        //Loading images
        Image image1 = new Image("background.jpg");
        Image image2 = new Image("market.png");
        Image image3 = new Image("playerboard1.jpg");
        Image image4 = new Image("marketIcon.jpg");
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
        sendBTN = new Button("SEND");
        singleBTN = new Button("Single Player");
        createBTN = new Button("Create Multi Player Lobby");
        joinBTN = new Button("Join Multi Player Lobby");
        player1BTN = new Button();
        player2BTN = new Button();
        player3BTN = new Button();
        player4BTN = new Button();
        marketBTN = new Button();
        developmentBTN = new Button();
        leaderBTN = new Button();
        endTurnBTN = new Button();
        //Setting Buttons
        player1BTN.setPrefWidth(75);
        player2BTN.setPrefWidth(75);
        player3BTN.setPrefWidth(75);
        player4BTN.setPrefWidth(75);
        marketBTN.setPrefSize(150,150);
        marketBTN.setGraphic(marketiconIMG);
        developmentBTN.setPrefSize(150,150);
        leaderBTN.setPrefSize(150,100);
        endTurnBTN.setPrefSize(75,50);
        sendBTN.setPrefSize(150,75);
        singleBTN.setPrefSize(175,75);
        createBTN.setPrefSize(175,75);
        joinBTN.setPrefSize(175,75);
        //leaderBTN.setDisable(true);
        //Setting Labels
        title = new Label("Maestri del Rinascimento");
        title.setFont(Font.font("Monotype Corsiva", 36));
        insert = new Label("Insert your Username");
        //Setting TextField
        TextField usernameField = new TextField();
        //Setting ListViews
        ListView<String> playersList = new ListView<>();
        playersList.getItems().addAll("Mario", "gerryscotti00","AGACASH","theodore");
        ListView<String> playersList1 = new ListView<>();
        playersList1.getItems().addAll("Mario", "gerryscotti00","AGACASH","theodore");
        ListView<String> playersList2 = new ListView<>();
        playersList2.getItems().addAll("Mario", "gerryscotti00","AGACASH","theodore");
        ListView<String> playersList3 = new ListView<>();
        playersList3.getItems().addAll("Mario", "gerryscotti00","AGACASH","theodore");
        //Setting Panes
        mainPane = new AnchorPane();
        bPane = new BorderPane();
        vbox = new VBox();
        hboxN = new HBox();
        hboxS = new HBox();
        player1Pane = new BorderPane();
        marketPane = new AnchorPane();
        leaderPane = new AnchorPane();
        loginBox = new VBox();
        gameSelBox = new VBox();
        lobbyAPane = new VBox();
        lobbyPane = new ScrollPane();
        lobbiesBox = new VBox();
        developmentPane = new AnchorPane();
        player2Pane = new AnchorPane();
        player3Pane = new AnchorPane();
        player4Pane = new AnchorPane();
        //Setting Canvas
        marbleCanvas = new Canvas(600,400);
        leaderCanvas = new Canvas(600,400);
        devCanvas = new Canvas(600,440);
        player1Canvas = new Canvas(600,400);
        //Canvas drawings experiments
        gc1 = leaderCanvas.getGraphicsContext2D();
        gc1.drawImage(IMGLoader.getImage("/LEADERS/Leader1.png"), -100,30,500, 375);
        gc1.drawImage(IMGLoader.getImage("/LEADERS/Leader1.png"), 165,30,500, 375);
        gc2 = devCanvas.getGraphicsContext2D();
        gc2.drawImage(IMGLoader.getImage("/DEVBOARD/Blue5.png"),0,-17,245,185);
        gc2.drawImage(IMGLoader.getImage("/DEVBOARD/Blue5.png"),0,130,245,185);
        gc2.drawImage(IMGLoader.getImage("/DEVBOARD/Blue5.png"),0,278,245,185);
        gc2.drawImage(IMGLoader.getImage("/DEVBOARD/Blue5.png"),130,278,245,185);
        gc2.drawImage(IMGLoader.getImage("/DEVBOARD/Blue5.png"),260,278,245,185);
        gc2.drawImage(IMGLoader.getImage("/DEVBOARD/Blue5.png"),390,278,245,185);
        gc = marbleCanvas.getGraphicsContext2D();
        gc.drawImage(IMGLoader.getImage("/MARBLES/greyMarble.png"),105,100,100,100);
        gc.drawImage(IMGLoader.getImage("/MARBLES/blueMarble.png"), 165, 100, 100, 100);
        gc.drawImage(IMGLoader.getImage("/MARBLES/whiteMarble.png"), 105, 155, 100, 100);
        gc.drawImage(IMGLoader.getImage("/MARBLES/greyMarble.png"), 105, 210, 100, 100);
        gc.drawImage(IMGLoader.getImage("/MARBLES/redMarble.png"), 225, 100, 100, 100);
        gc.drawImage(IMGLoader.getImage("/MARBLES/purpleMarble.png"), 165, 155, 100, 100);
        gc.drawImage(IMGLoader.getImage("/MARBLES/yellowMarble.png"), 225, 155, 100, 100);
        gc.drawImage(IMGLoader.getImage("/MARBLES/greyMarble.png"), 285, 100, 100, 100);
        player1GC = player1Canvas.getGraphicsContext2D();
        player1GC.drawImage(IMGLoader.getImage("/DEVBOARD/Blue5.png"),168,200,255,191);
        player1GC.drawImage(IMGLoader.getImage("/DEVBOARD/Blue5.png"),288,200,255,191);
        player1GC.drawImage(IMGLoader.getImage("/DEVBOARD/Blue5.png"),408,200,255,191);
        //Adding to LoginBox
        loginBox.setAlignment(Pos.CENTER);
        loginBox.setPadding(new Insets(0,150,0,150));
        loginBox.setPrefSize(839,600);
        loginBox.setSpacing(30);
        loginBox.getChildren().addAll(title,insert,usernameField,sendBTN);
        //Adding to Game Selection Box
        gameSelBox.setAlignment(Pos.CENTER);
        gameSelBox.setPrefSize(839,600);
        gameSelBox.setSpacing(30);
        gameSelBox.getChildren().addAll(singleBTN,createBTN,joinBTN);
        //Adding to Lobbies Box/Pane
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
        //Adding to VBOX
        vbox.setPrefSize(217,446);
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(15);
        vbox.getChildren().addAll(marketBTN,developmentBTN,leaderBTN);
        //Adding to Northern HBOX
        hboxN.setPrefSize(839,65);
        hboxN.setSpacing(150);
        hboxN.setAlignment(Pos.CENTER);
        hboxN.getChildren().addAll(player1BTN,player2BTN,player3BTN,player4BTN);
        //Adding to Southern HBOX
        hboxS.setPrefSize(839,90);
        hboxS.setPadding(new Insets(15,0,0,700));
        hboxS.getChildren().add(endTurnBTN);
        //Adding to PlayerPane
        player1Pane.setPrefSize(622,445);
        player1Pane.getChildren().addAll(playerboardIMG, player1Canvas);
        //Adding to Market Pane
        marketPane.setPrefSize(622,445);
        marketPane.getChildren().addAll(marketIMG, marbleCanvas);
        //Adding to LeaderPane
        leaderPane.getChildren().add(leaderCanvas);
        //Adding to Development Pane
        developmentPane.getChildren().add(devCanvas);
        //Setting opponents panes
        player2Pane.setPrefSize(622,445);
        player3Pane.setPrefSize(622,445);
        player4Pane.setPrefSize(622,445);
        player2Pane.getChildren().add(playerboard2IMG);
        player3Pane.getChildren().add(playerboard3IMG);
        player4Pane.getChildren().add(playerboard4IMG);
        //Adding to BP
        bPane.setLeft(vbox);
        bPane.setTop(hboxN);
        bPane.setBottom(hboxS);
        //Adding to AP
        //mainPane.getChildren().addAll(backgroundIMG, loginBox);
 */
