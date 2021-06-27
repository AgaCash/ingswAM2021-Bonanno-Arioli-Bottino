package GUI.scenes;

import GUI.FXMLLoaderCustom;
import GUI.GUI;
import clientModel.cards.LightDevelopmentCard;
import clientModel.cards.LightLeaderCard;
import clientModel.colour.LightColour;
import clientModel.table.LightDevelopmentBoard;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.Optional;

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

       loadDevCards(devBoardPane);
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
       double x = 30;
       double y = 0;
       for (int i = 0, j = 0; i < developmentBoard.getDecksSize() ; i++, j++){
           LightDevelopmentCard c = developmentBoard.getTopCardFromDeck(i);
           String fileName = c.getColour().name().substring(0, 1).toUpperCase()+c.getColour().name().substring(1).toLowerCase();
           System.out.println(fileName+c.getId());
           ImageView im = new ImageView("/images/DEVBOARD/"+fileName+c.getId()+".png");
           im.setPreserveRatio(true);
           im.fitWidthProperty().bind(pane.widthProperty().divide(4));
           im.fitHeightProperty().bind(pane.heightProperty().divide(3).subtract(10));
           im.setId(Integer.toString(c.getId()));
           im.relocate(x, y);
           im.setOnMouseClicked(this::devCardClick);
           x += 150;
           if(j == 3){
               j = -1;
               x = 30;
               y += 150;
           }
           ims.add(im);
       }
       pane.getChildren().addAll(ims);
   }

   private void devCardClick(MouseEvent mouseEvent){
       int slot;
       boolean leaderUse;
       ImageView currImage = (ImageView) mouseEvent.getTarget();
       if(currImage.getEffect() == null){
           //compra la devCard
           //ALERT_BOXES  ||
           //             \/
           Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
           alert.setTitle("Asking for slot");
           alert.setHeaderText("In which slot do you want to add the card?");
           alert.setContentText("Choose your slot.");
           ButtonType buttonTypeOne = new ButtonType("One");
           ButtonType buttonTypeTwo = new ButtonType("Two");
           ButtonType buttonTypeThree = new ButtonType("Three");
           ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
           alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo, buttonTypeThree, buttonTypeCancel);
           Optional<ButtonType> result = alert.showAndWait();
           if (result.get() == buttonTypeOne){
               slot = 1;
           } else if (result.get() == buttonTypeTwo) {
               slot = 2;
           } else if (result.get() == buttonTypeThree) {
               slot = 3;
           } else {
               slot = -1;
           }

           alert = new Alert(Alert.AlertType.CONFIRMATION);
           alert.setTitle("LeaderCard Activation");
           alert.setHeaderText("Do you want to use a leader card?");
           alert.setContentText("");

           result = alert.showAndWait();
           if (result.get() == ButtonType.OK){
               leaderUse = true;
           } else {
               leaderUse = false;
           }

           if(leaderUse){
               ArrayList<LightLeaderCard> cards = GUI.getInstance().getController().getPlayerBoard().getLeaderSlot();
               alert = new Alert(Alert.AlertType.CONFIRMATION);
               alert.setTitle("LeaderCards choosing");
               alert.setHeaderText("Choose a leader card");
               alert.setContentText("(one left, two right)");
               Pane p = new Pane();

               ImageView im1 = new ImageView("/images/LEADERS/Leader"+cards.get(0).getId()+".png");
               ImageView im2 = new ImageView("/images/LEADERS/Leader"+cards.get(1).getId()+".png");
               im1.fitHeightProperty().bind(alert.heightProperty().divide(4));
               im2.fitHeightProperty().bind(alert.heightProperty().divide(4));
               im2.relocate(200, 0);
               im1.setPreserveRatio(true);
               im2.setPreserveRatio(true);
               p.getChildren().add(im1);
               p.getChildren().add(im2);
               alert.setGraphic(p);

               buttonTypeOne = new ButtonType("One");
               buttonTypeTwo = new ButtonType("Two");
               buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

               alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo, buttonTypeCancel);
               alert.setX(150);
               alert.setY(120);
               result = alert.showAndWait();
               if (result.get() == buttonTypeOne){
                   // ... user chose "One"
               } else if (result.get() == buttonTypeTwo) {
                   // ... user chose "Two"
               } else {
                   // ... user chose CANCEL or closed the dialog
               }
           }

           //ALERT_BOXES  /\   (askSlot & askLeader)
           //             ||
           //-----------------------------
           //                  ||
           //                  \/


       }
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
