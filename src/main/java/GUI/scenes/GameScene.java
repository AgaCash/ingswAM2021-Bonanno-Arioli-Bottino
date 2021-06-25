package GUI.scenes;

import GUI.FXMLLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

public class GameScene implements GenericScene{

   @FXML
   Button marketBTN;
   @FXML
   Button devBoardBTN;
   @FXML
   Button leaderBTN;
   @FXML
   Button player1BTN;
   @FXML
   Button player2BTN;
   @FXML
   Button player3BTN;
   @FXML
   Button player4BTN;
   @FXML
   Button endTurnBTN;
   @FXML
   BorderPane gamePane;


   @FXML
   private void goToMarket(ActionEvent event){
       FXMLLoader object = new FXMLLoader();
       Pane view = object.getPage("market");
       gamePane.setCenter(view);
   }
   @FXML
   private void goToDevBoard(ActionEvent event){
       FXMLLoader object = new FXMLLoader();
       Pane view = object.getPage("developmentBoard");
       gamePane.setCenter(view);
   }
   @FXML
   private void goToLeader(ActionEvent event){
       FXMLLoader object = new FXMLLoader();
       Pane view = object.getPage("leader");
       gamePane.setCenter(view);
   }
   @FXML
   private void goToPlayer1(ActionEvent event){
       FXMLLoader object = new FXMLLoader();
       Pane view = object.getPage("playerboard");
       gamePane.setCenter(view);
   }
    @FXML
    private void goToPlayer2(ActionEvent event){
        FXMLLoader object = new FXMLLoader();
        Pane view = object.getPage("playerboard2");
        gamePane.setCenter(view);
    }
    @FXML
    private void goToPlayer3(ActionEvent event){
        FXMLLoader object = new FXMLLoader();
        Pane view = object.getPage("playerboard3");
        gamePane.setCenter(view);
    }
    @FXML
    private void goToPlayer4(ActionEvent event){
        FXMLLoader object = new FXMLLoader();
        Pane view = object.getPage("playerboard4");
        gamePane.setCenter(view);
    }






   /* public GameScene(){
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
        backgroundIMG.setFitHeight(600);
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
        //Create Buttons
        Button player1BTN = new Button();
        Button player2BTN = new Button();
        Button player3BTN = new Button();
        Button player4BTN = new Button();
        Button marketBTN = new Button();
        Button developmentBTN = new Button();
        Button leaderBTN = new Button();
        Button endTurnBTN = new Button();
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
        //Create Panes
        AnchorPane mainPane = new AnchorPane();
        BorderPane bPane = new BorderPane();
        VBox vbox = new VBox();
        HBox hboxN = new HBox();
        HBox hboxS = new HBox();
        BorderPane player1Pane = new BorderPane();
        AnchorPane marketPane = new AnchorPane();
        AnchorPane leaderPane = new AnchorPane();
        AnchorPane developmentPane = new AnchorPane();
        AnchorPane player2Pane = new AnchorPane();
        AnchorPane player3Pane = new AnchorPane();
        AnchorPane player4Pane = new AnchorPane();
        //Setting Canvas
        Canvas marbleCanvas = new Canvas(600,400);
        Canvas leaderCanvas = new Canvas(600,400);
        Canvas devCanvas = new Canvas(600,440);
        Canvas player1Canvas = new Canvas(600,400);
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
    }

         */
}
