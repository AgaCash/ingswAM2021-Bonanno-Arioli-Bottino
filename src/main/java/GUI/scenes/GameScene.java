package GUI.scenes;

import GUI.FXMLLoaderCustom;
import GUI.GUI;
import clientModel.cards.LightDevelopmentCard;
import clientModel.cards.LightLeaderCard;
import clientModel.colour.LightColour;
import clientModel.marbles.LightMarble;
import clientModel.table.LightDevelopmentBoard;
import clientModel.table.LightMarketBoard;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.effect.ColorAdjust;
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

    private int numOfPlayers;

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
        loadMarbles(marketPane);
        loadMarbleSelections(marketPane);
        loadLeaderPane(leaderPane);
   }

    @FXML
    private void goToMarket(ActionEvent event){
       gamePane.setCenter(marketPane);
   }
    @FXML
    private void goToDevBoard(ActionEvent event) {
       gamePane.setCenter(devBoardPane);
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
        //per attivare i cheat (da togliere)
        clickCount = 0;
        GUI.getInstance().getController().sendEndTurnRequest();
    }

    public void enableTurn(){
        //ENABLE ALL THE THINGS THAT USER CAN INTERACT WITH
            // development board
        devBoardPane.getChildren().forEach((imageView)->{
            enableImage((ImageView) imageView);
        });
            // leader cards
        leaderPane.getChildren().forEach((thing)->{
            if(thing instanceof ImageView){
                enableImage((ImageView) thing);
            }else{
                thing.setDisable(false);
            }
        });
        marketPane.getChildren().forEach((thing)->{
            if(thing instanceof ImageView)
                enableImage((ImageView) thing);

        });
        marketPane.getChildren().forEach((marbleImage)->{
            if(marbleImage instanceof ImageView){
                enableImage((ImageView) marbleImage);
            }
        });
    }

    public void disableTurn(){
        //DISABLE ALL THE THINGS THAT USER CAN INTERACT WITH
            // development board
        devBoardPane.getChildren().forEach((imageView)->{
            disableImage((ImageView) imageView);
        });
            // leader cards
        leaderPane.getChildren().forEach((thing)->{
            if(thing instanceof ImageView){
                disableImage((ImageView) thing);
            }else{
                thing.setDisable(true);
            }
        });
        marketPane.getChildren().forEach((thing)->{
            if(thing instanceof ImageView)
                disableImage((ImageView) thing);

        });
        marketPane.getChildren().forEach((marbleImage)->{
            if(marbleImage instanceof ImageView){
                disableImage((ImageView) marbleImage);
            }
        });
    }

    //helper

    private void disableImage(ImageView imageView){
        ColorAdjust monochrome = new ColorAdjust();
        monochrome.setSaturation(-0.8);
        imageView.setEffect(monochrome);
    }
    private void enableImage(ImageView imageView){
        imageView.setEffect(null);
    }

    private void loadDevCards(Pane pane){
        LightDevelopmentBoard developmentBoard = GUI.getInstance().getController().getDevBoard();
        ArrayList<ImageView> ims = new ArrayList<>();
        double x = 30;
        double y = 0;
        for (int i = 0, j = 0; i < developmentBoard.getDecksSize() ; i++, j++){
            LightDevelopmentCard c = developmentBoard.getTopCardFromDeck(i);
            String fileName = c.getColour().name().substring(0, 1).toUpperCase()+c.getColour().name().substring(1).toLowerCase();
            ImageView im = new ImageView("/images/DEVBOARD/"+fileName+c.getId()+".png");
            im.setPreserveRatio(true);
            im.fitWidthProperty().bind(pane.widthProperty().divide(4));
            im.fitHeightProperty().bind(pane.heightProperty().divide(3).subtract(10));
            im.setId(Integer.toString(i));
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
        int deck = Integer.parseInt(currImage.getId());

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
                GUI.getInstance().getController().showError("Card not bought");
                return;
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

            if(leaderUse && GUI.getInstance().getController().getPlayerBoard().getLeaderSlot().size() == 0){
                GUI.getInstance().getController().showError("You don't have any Leader Card");
                return;
            }

            if(leaderUse){
                ArrayList<LightLeaderCard> cards = GUI.getInstance().getController().getPlayerBoard().getLeaderSlot();
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("LeaderCards choosing");
                alert.setHeaderText("Choose a leader card");
                alert.setContentText("(one left, two right)");
                Pane p = new Pane();

                buttonTypeOne = new ButtonType("One");
                buttonTypeTwo = new ButtonType("Two");
                buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

                ImageView im1 = new ImageView("/images/LEADERS/Leader"+cards.get(0).getId()+".png");
                im1.fitHeightProperty().bind(alert.heightProperty().divide(4));
                im1.setPreserveRatio(true);
                p.getChildren().add(im1);

                if(cards.size() != 1) {
                    ImageView im2 = new ImageView("/images/LEADERS/Leader" + cards.get(1).getId() + ".png");
                    im2.fitHeightProperty().bind(alert.heightProperty().divide(4));
                    im2.relocate(200, 0);
                    im2.setPreserveRatio(true);
                    p.getChildren().add(im2);
                    alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo, buttonTypeCancel);
                }else{
                    alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeCancel);
                }

                alert.setGraphic(p);

                alert.setX(150);
                alert.setY(120);
                result = alert.showAndWait();
                if (result.get() == buttonTypeOne){
                    GUI.getInstance().getController().sendBuyDevCardRequest(deck, slot-1, cards.get(0));
                } else if (result.get() == buttonTypeTwo) {
                    GUI.getInstance().getController().sendBuyDevCardRequest(deck, slot-1, cards.get(1));
                }else{
                    GUI.getInstance().getController().showError("Card not bought");
                    return;
                }
            }else{
                GUI.getInstance().getController().sendBuyDevCardRequest(deck, slot-1, null);
            }

            //ALERT_BOXES  /\   (askSlot & askLeader)
            //             ||
            //-----------------------------

        }
    }
    private void loadMarbles(Pane pane) {
        LightMarketBoard marketBoard = GUI.getInstance().getController().getMarketBoard();
        ArrayList<ImageView> ims = new ArrayList<>();
        double x = 80;
        double y = 85;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                LightMarble m = marketBoard.getMarble(i, j);
                String fileName = m.getFileName();
                ImageView im = new ImageView("/images/MARBLES/" + fileName);
                im.setPreserveRatio(true);
                im.fitHeightProperty().bind(pane.heightProperty().divide(4));
                int id = j + (i*4);
                im.setId(Integer.toString(id));
                im.relocate(x, y);
                x += 65;
                ims.add(im);
            }
            x = 80;
            y += 65;
        }
        LightMarble freeM = marketBoard.getFreeMarble();
        String freeName = freeM.getFileName();
        ImageView im = new ImageView("/images/MARBLES/" + freeName);
        im.setPreserveRatio(true);
        im.fitHeightProperty().bind(pane.heightProperty().divide(5));
        im.relocate(260,42);
        ims.add(im);
        pane.getChildren().addAll(ims);
    }

    private void loadMarbleSelections(Pane pane){
        ArrayList<ImageView> ims = new ArrayList<>();
        double x = 80;
        double y = 300;
        for(int i=0; i<4; i++){
            ImageView im = new ImageView("/images/TOKENS/blueToken.png");
            im.fitHeightProperty().bind(pane.heightProperty().divide(5));
            im.setPreserveRatio(true);
            im.relocate(x,y);
            im.setId(Integer.toString(i +3));
            x += 65;
            im.setOnMouseClicked(this::marketClick);
            ims.add(im);
        }
        x = 400;
        y = 85;
        for(int i=0;i<3;i++){
            ImageView im = new ImageView("/images/TOKENS/blueToken.png");
            im.fitHeightProperty().bind(pane.heightProperty().divide(5));
            im.setPreserveRatio(true);
            im.relocate(x,y);
            im.setId(Integer.toString(i));
            y += 65;
            im.setOnMouseClicked(this::marketClick);
            ims.add(im);
        }
        pane.getChildren().addAll(ims);
    }

    private void marketClick(MouseEvent mouseEvent) {
        boolean leaderUse;
        ImageView currImage = (ImageView) mouseEvent.getTarget();
        boolean line;
        int whichOne;
        if (currImage.getEffect() == null) {
            if (Integer.parseInt(currImage.getId()) < 4) {
                line = true;
                whichOne = Integer.parseInt(currImage.getId());
            } else {
                line = false;
                whichOne = Integer.parseInt(currImage.getId()) - 3;
            }
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("LeaderCard Activation");
            alert.setHeaderText("Do you want to use a leader card?");
            alert.setContentText("");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK) {
                leaderUse = true;
            } else {
                leaderUse = false;
            }
            if (leaderUse && GUI.getInstance().getController().getPlayerBoard().getLeaderSlot().size() == 0) {
                GUI.getInstance().getController().showError("You don't have any Leader Card");
                return;
            }
            if (leaderUse) {
                ArrayList<LightLeaderCard> cards = GUI.getInstance().getController().getPlayerBoard().getLeaderSlot();
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("LeaderCards choosing");
                alert.setHeaderText("Choose a leader card");
                alert.setContentText("(one left, two right)");
                Pane p = new Pane();

                ButtonType buttonTypeOne = new ButtonType("One");
                ButtonType buttonTypeTwo = new ButtonType("Two");
                ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

                ImageView im1 = new ImageView("/images/LEADERS/Leader" + cards.get(0).getId() + ".png");
                im1.fitHeightProperty().bind(alert.heightProperty().divide(4));
                im1.setPreserveRatio(true);
                p.getChildren().add(im1);

                if (cards.size() != 1) {
                    ImageView im2 = new ImageView("/images/LEADERS/Leader" + cards.get(1).getId() + ".png");
                    im2.fitHeightProperty().bind(alert.heightProperty().divide(4));
                    im2.relocate(200, 0);
                    im2.setPreserveRatio(true);
                    p.getChildren().add(im2);
                    alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo, buttonTypeCancel);
                } else {
                    alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeCancel);
                }

                alert.setGraphic(p);

                alert.setX(150);
                alert.setY(120);
                result = alert.showAndWait();
                if (result.get() == buttonTypeOne){
                    GUI.getInstance().getController().sendBuyResourceRequest(line, whichOne, cards.get(0));
                } else if (result.get() == buttonTypeTwo) {
                    GUI.getInstance().getController().sendBuyResourceRequest(line, whichOne, cards.get(1));
                }else{
                    GUI.getInstance().getController().showError("Resources not bought");
                    return;
                }
            }else{
                GUI.getInstance().getController().sendBuyResourceRequest(line, whichOne, null);
            }
            loadMarbles(marketPane);
        }
    }

    private void loadLeaderPane(Pane pane){
       // 2 leaderCard con sotto 4 bottoni (2 a testa: attiva e scarta)
        String basePath = "/images/LEADERS/Leader";
        ArrayList<LightLeaderCard> lCards = GUI.getInstance().getController().getPlayerBoard().getLeaderSlot();
        ArrayList<ImageView> im = new ArrayList<>();
        im.add(new ImageView(basePath+lCards.get(0).getId()+".png"));
        im.add(new ImageView(basePath+lCards.get(1).getId()+".png"));
        int x = 130;
        int y = 50;
        int i = 0;
        for(ImageView imageView: im){
            imageView.fitHeightProperty().bind(pane.heightProperty().divide(2));
            imageView.fitWidthProperty().bind(pane.widthProperty().divide(2));
            imageView.setPreserveRatio(true);
            imageView.relocate(x, y);
            x+=200;
        }
        x = 175;
        y += 250;
        ArrayList<Button> activateButtons = new ArrayList<>();
        activateButtons.add(new Button("Activate"));
        activateButtons.add(new Button("Activate"));
        ArrayList<Button> dropButtons = new ArrayList<>();
        dropButtons.add(new Button("Drop"));
        dropButtons.add(new Button("Drop"));
        for(Button b: activateButtons){
            //set position
            b.relocate(x, y);
            x+=200;
            //add code
            b.setId(i+"");
            b.setOnAction((mouseEvent)->{
                ArrayList<LightLeaderCard> lc = GUI.getInstance().getController().getPlayerBoard().getLeaderSlot();
                int btnId = Integer.parseInt(((Button)mouseEvent.getTarget()).getId());
                //if(!lCards.get(btnId).isEnabled())
                GUI.getInstance().getController().sendLeaderCardActivationRequest(lc.get(btnId));
            });
            i++;
        }
        x = 180;
        y+=50;
        i = 0;

        for(Button b: dropButtons){
            //set position
            b.relocate(x, y);
            x+=200;
            //add code
            b.setId(i+"");
            b.setOnAction((mouseEvent)->{
                ArrayList<LightLeaderCard> lc = GUI.getInstance().getController().getPlayerBoard().getLeaderSlot();
                int btnId = Integer.parseInt(((Button)mouseEvent.getTarget()).getId());
                if(!lc.get(btnId).isEnabled())
                    GUI.getInstance().getController().sendLeaderCardThrowRequest(lc.get(btnId));
                else
                    GUI.getInstance().getController().showError("You can't drop an active leader card");
            });
            i++;
        }

        pane.getChildren().addAll(im);
        pane.getChildren().addAll(activateButtons);
        pane.getChildren().addAll(dropButtons);
    }


    //CHEATS (da togliere)
    @FXML
    HBox cheatHbox;
    private int clickCount = 0;
    public void cheatActivation(MouseEvent mouseEvent) {
        clickCount++;
        System.out.println(clickCount);
        if(clickCount>5){
            clickCount = 0;
            if(mouseEvent.isShiftDown()) {
                GUI.getInstance().getController().sendCheat(2);
                System.out.println("Cheat FAITH SENT");
            }
            else {
                GUI.getInstance().getController().sendCheat(1);
                System.out.println("Cheat RES SENT");
            }
        }
    }
}
