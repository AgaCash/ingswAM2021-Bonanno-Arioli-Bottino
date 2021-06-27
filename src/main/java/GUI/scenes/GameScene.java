package GUI.scenes;

import GUI.FXMLLoaderCustom;
import GUI.GUI;
import clientModel.table.LightDevelopmentBoard;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.util.ArrayList;

public class GameScene implements GenericScene{

    ArrayList<Button> playerBoardsButtons;
    ArrayList<Pane> playerBoardsPanes;

    @FXML
    Button marketBTN;
    Pane marketPane;
    @FXML
    Button devBoardBTN;
    Pane devBoardPane;
    @FXML
    Button leaderBTN;
    Pane leaderPane;
    @FXML
    Button player1BTN;
    Pane player1Pane;
    @FXML
    Button player2BTN;
    Pane player2Pane;
    @FXML
    Button player3BTN;
    Pane player3Pane;
    @FXML
    Button player4BTN;
    Pane player4Pane;
    @FXML
    Button endTurnBTN;
    @FXML
    BorderPane gamePane;

   public int numOfPlayers;

   public void init(int numOfPlayers){
       this.numOfPlayers = numOfPlayers;
       playerBoardsButtons = new ArrayList<>();
       playerBoardsPanes = new ArrayList<>();
       playerBoardsButtons.add(player1BTN);
       playerBoardsButtons.add(player2BTN);
       playerBoardsButtons.add(player3BTN);
       playerBoardsButtons.add(player4BTN);
       playerBoardsPanes.add(player1Pane);
       playerBoardsPanes.add(player2Pane);
       playerBoardsPanes.add(player3Pane);
       playerBoardsPanes.add(player4Pane);
       FXMLLoaderCustom customFL = new FXMLLoaderCustom();
       marketPane = customFL.getPage("market");
       devBoardPane = customFL.getPage("developmentBoard");
       leaderPane = customFL.getPage("leader");
       for(int i = 4; i > numOfPlayers; i--){
           playerBoardsButtons.get(i-1).setVisible(false);
       }
       System.out.println(playerBoardsPanes);
       for(int i = 0; i < numOfPlayers; i++){
           Pane p = playerBoardsPanes.get(i);
           System.out.println(player1Pane);
           System.out.println(p);
           p = customFL.getPage("playerboard"+ (i + 1));
           System.out.println(p);
       }//todo NON VAAAAAAAAAAAAAAAAAAAAAA
           /*
       switch (numOfPlayers){
           case 4:
               player4Pane = customFL.getPage("playerboard4");
           case 3:
               player3Pane = customFL.getPage("playerboard3");
           case 2:
               player2Pane = customFL.getPage("playerboard2");
           case 1:
               player1Pane = customFL.getPage("playerboard1");
       }
       if(!isMultiGame){
           player2BTN.setVisible(false);
           player3BTN.setVisible(false);
           player4BTN.setVisible(false);
       }

            */
   }

   @FXML
   private void goToMarket(ActionEvent event){
       gamePane.setCenter(marketPane);
   }
   @FXML
   private void goToDevBoard(ActionEvent event) {
       gamePane.setCenter(devBoardPane);
   }

   //todo caricare la DevelopmentBoard
   private void loadDevCards(Pane pane){
       LightDevelopmentBoard developmentBoard = GUI.getInstance().getController().getDevBoard();
       ArrayList<ImageView> ims = new ArrayList<>();
       for (int i = 0; i < developmentBoard.getDecksSize() ; i++){
           ImageView im = new ImageView("/images/DEVBOARD/Blue5.png");
           im.fitWidthProperty().bind(pane.widthProperty().divide(4));
           im.fitHeightProperty().bind(pane.heightProperty().divide(4));
           //im.setId();
           ims.add(im);
       }
       pane.getChildren().addAll(ims);
   }

   @FXML
   private void goToLeader(ActionEvent event){
       gamePane.setCenter(leaderPane);
   }
   @FXML
   private void goToPlayer1(ActionEvent event){
       gamePane.setCenter(playerBoardsPanes.get(0));
   }
    @FXML
    private void goToPlayer2(ActionEvent event){
        gamePane.setCenter(playerBoardsPanes.get(1));
    }
    @FXML
    private void goToPlayer3(ActionEvent event){
        gamePane.setCenter(playerBoardsPanes.get(2));
    }
    @FXML
    private void goToPlayer4(ActionEvent event){
        gamePane.setCenter(playerBoardsPanes.get(3));
    }

    public void endTurn(ActionEvent actionEvent) {
        GUI.getInstance().getController().sendEndTurnRequest();
    }

    public void enableTurn(){

    }

    public void disableTurn(){

    }
}
