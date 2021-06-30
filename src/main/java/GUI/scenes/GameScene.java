package GUI.scenes;

import GUI.FXMLLoaderCustom;
import GUI.GUI;
import clientModel.cards.LightCardSlots;
import clientModel.cards.LightDevelopmentCard;
import clientModel.cards.LightLeaderCard;
import clientModel.colour.LightColour;
import clientModel.marbles.LightMarble;
import clientModel.resources.LightResource;
import clientModel.strongbox.LightStrongbox;
import clientModel.table.LightDevelopmentBoard;
import clientModel.table.LightFaithBox;
import clientModel.table.LightFaithTrack;
import clientModel.table.LightMarketBoard;
import clientModel.warehouse.LightWarehouseDepot;
import javafx.beans.binding.ObjectBinding;
import javafx.css.Style;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Glow;
import javafx.scene.effect.Light;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import model.cards.DevelopmentCard;
import model.table.DevelopmentBoard;

import java.awt.*;
import java.util.ArrayList;
import java.util.Optional;

public class GameScene implements GenericScene{




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
    ArrayList<Button> playerBoardsButtons;
    @FXML
    ArrayList<Pane> playerBoardsPanes;

    private int numOfPlayers;
    private ArrayList<String> playersList;
    private int myPlayerIndex;
    private boolean isMyTurn;

    //todo togliere num of players: ricavabile da playersList
    public void init(int numOfPlayers){
        this.numOfPlayers = numOfPlayers;
        this.playersList = GUI.getInstance().getController().getUsernamesList();
        for(int i = 0; i < playersList.size(); i++){
            if(playersList.get(i).equals(GUI.getInstance().getController().getUsername())) {
                myPlayerIndex = i;
                break;
            }
        }
        FXMLLoaderCustom customFL = new FXMLLoaderCustom();
        marketPane = customFL.getPage("market");
        devBoardPane = customFL.getPage("developmentBoard");
        leaderPane = customFL.getPage("leader");
        Pane marblePane = new Pane();
        marblePane.setId("marblesPane");
        marblePane.resize(265,220);
        marblePane.setLayoutX(80);
        marblePane.setLayoutY(40);
        marketPane.getChildren().add(marblePane);
        loadDevCards(devBoardPane, GUI.getInstance().getController().getDevBoard());
        loadMarbles(marketPane, GUI.getInstance().getController().getMarketBoard());
        loadMarbleSelections(marketPane);
        loadLeaderPane(leaderPane);
        //playerBoards
        playerBoardsButtons = new ArrayList<>();
        player1BTN.setId("0");
        player2BTN.setId("1");
        player3BTN.setId("2");
        player4BTN.setId("3");
        playerBoardsButtons.add(player1BTN);
        playerBoardsButtons.add(player2BTN);
        playerBoardsButtons.add(player3BTN);
        playerBoardsButtons.add(player4BTN);
        playerBoardsPanes = new ArrayList<>();
        for(int i = 4; i > numOfPlayers; i--){
            playerBoardsButtons.get(i-1).setVisible(false);
            playerBoardsButtons.remove(i-1);
        }
        for(int i = 0; i < numOfPlayers; i++){
            Pane plPane = customFL.getPage("playerboard"+(i+1));
            plPane.setId(i+"");
            playerBoardsPanes.add(plPane);
        }
        //mettere i nomi dei player sui bottoni in alto ("ME" se è la propria playerboard)
        playerBoardsButtons.forEach((btn)->{
            int btnId = Integer.parseInt(btn.getId());
            btn.setText(playersList.get(btnId));
            if(btnId == myPlayerIndex){
                btn.setText("ME");
            }
        });
        //dividere il playerboard in 4 pane (faith, depot, strongB, prod)
        playerBoardsPanes.forEach((pbPane)->{
            Pane faithPane = new Pane();
            faithPane.resize(622, 135);
            faithPane.setId("faithPane");
            Pane depotPane = new Pane();
            depotPane.resize(111, 146);
            depotPane.setLayoutX(26);
            depotPane.setLayoutY(177);
            depotPane.setId("depotPane");
            Pane strongboxPane = new Pane();
            strongboxPane.resize(111, 80);
            strongboxPane.setLayoutX(8);
            strongboxPane.setLayoutY(310);
            strongboxPane.setId("strongboxPane");
            Pane productionPane = new Pane();
            productionPane.resize(441, 236);
            productionPane.setLayoutX(152);
            productionPane.setLayoutY(169);
            productionPane.setId("productionPane");

            pbPane.getChildren().addAll(faithPane, depotPane, strongboxPane, productionPane);
        });
        //aggiungere le varie croci nelle posizioni corrette (player 3 ha 1 faithPoint bonus)
        //loadFaith(playerBoardsPanes.get(myPlayerIndex));
        //productions
        //          !!ALWAYS EMPTY AT START!!
        //          -> when slot is clicked
        //              if (card && !Effect) -> production
        playerBoardsPanes.forEach((pbPanes)->{
            Pane prodPane = (Pane) pbPanes.lookup("#productionPane");
            recreateProdBaseImageview(prodPane, Integer.parseInt(pbPanes.getId()));
        });
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
        if(playersList.size()==1){

        }
    }

    public void enableTurn(){
        this.isMyTurn = true;
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
        marketPane.getChildren().forEach((marbleImage)->{
            if(marbleImage instanceof ImageView){
                enableImage((ImageView) marbleImage);
            }
        });
        Pane mp = (Pane) marketPane.lookup("#marblesPane");
        mp.getChildren().forEach((imageView)->{
            enableImage((ImageView) imageView);
        });
            //end turn button
        gamePane.lookup("#endTurnBTN").setDisable(false);
    }

    public void disableTurn(){
        this.isMyTurn = false;
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
            // market board
        marketPane.getChildren().forEach((marbleImage)->{
            if(marbleImage instanceof ImageView){
                disableImage((ImageView) marbleImage);
            }
        });
        Pane mp = (Pane) marketPane.lookup("#marblesPane");
        mp.getChildren().forEach((imageView)->{
            disableImage((ImageView) imageView);
        });
            //end turn button
        gamePane.lookup("#endTurnBTN").setDisable(true);
    }

    //|----------|
    //|  UPDATES |
    //|----------|

    public void updateFaithTrack(String username, LightFaithTrack faithTrack) {
        playerBoardsPanes.forEach((pBoardPane)->{
            if(playersList.get(Integer.parseInt(pBoardPane.getId())).equals(username)){
                Pane faithPane = (Pane) pBoardPane.lookup("#faithPane");
                faithPane.getChildren().clear();
                ImageView imageView = new ImageView("/images/RESOURCES/redCross.png");
                imageView.setPreserveRatio(true);
                imageView.fitHeightProperty().bind(pBoardPane.heightProperty().divide(8.5));
                double offsetX = -35;
                boolean isStart = true;
                for(LightFaithBox fb : faithTrack.getBox()){
                    if(fb.getActualPos()&& isStart)
                        imageView.relocate(-10,80);
                    else if (fb.getActualPos())
                        imageView.relocate(offsetX,45);
                    offsetX += 25;
                    isStart = false;
                    }
                    faithPane.getChildren().add(imageView);
                }
        });
    }

    public void updateStrongBox(String username, LightStrongbox strongbox){
        playerBoardsPanes.forEach((pBoardPane)->{
            if(playersList.get(Integer.parseInt(pBoardPane.getId())).equals(username)){
                Pane strongboxPane = (Pane) pBoardPane.lookup("#strongboxPane");
                strongboxPane.getChildren().clear();
                double offsetX = 0;
                double offsetY = 0;
                int count = 0;
                for(LightResource r:strongbox.getStrongbox()){
                    ImageView imageView = new ImageView("/images/RESOURCES/"+r.name().toLowerCase()+"-min.png");
                    imageView.setPreserveRatio(true);
                    //imageView.fitWidthProperty().bind(strongboxPane.widthProperty().divide(4));
                    imageView.relocate(offsetX, offsetY);
                    offsetX+=10;
                    count++;
                    if(count == 10){
                        count = 0;
                        offsetX = 0;
                        offsetY += 25;
                    }
                    strongboxPane.getChildren().add(imageView);
                }
            }
        });
    }

    public void updateCardSlots(String username, ArrayList<LightDevelopmentCard> cardSlots){
        playerBoardsPanes.forEach((pBoardPane)->{
            if(playersList.get(Integer.parseInt(pBoardPane.getId())).equals(username)){
                Pane developmentPane = (Pane) pBoardPane.lookup("#productionPane");
                developmentPane.getChildren().clear();
                recreateProdBaseImageview(developmentPane, Integer.parseInt(pBoardPane.getId()));
                double offsetX;
                int count = -1;
                for(LightDevelopmentCard c: cardSlots){
                    count++;
                    if(c.getColour()!=null){
                        String fileName = c.getColour().name().substring(0, 1).toUpperCase()+c.getColour().name().substring(1).toLowerCase();
                        ImageView imageView = new ImageView("/images/DEVBOARD/"+fileName+c.getId()+".png");
                        imageView.setPreserveRatio(true);
                        imageView.fitWidthProperty().bind(developmentPane.widthProperty().divide(3.7));
                        offsetX= 82 +120*count;
                        imageView.relocate(offsetX, 40);
                        developmentPane.getChildren().add(imageView);
                    }
                }
            }
        });
    }

    public void updateDevBoard(LightDevelopmentBoard board){
        loadDevCards(devBoardPane, board);
    }

    public void updateMarketBoard(LightMarketBoard market){
        loadMarbles(marketPane, market);
    }

    //todo fix dimensions
    public void updateWarehouseDepot(String username, LightWarehouseDepot warehouseDepot){
        playerBoardsPanes.forEach((pbPane)->{
            Pane depotPane = (Pane) pbPane.lookup("#depotPane");
            if(playersList.get(myPlayerIndex).equals(username)){
                depotPane.getChildren().clear();
                ArrayList<LightResource> resS = warehouseDepot.getWarehouse();
                double offsetX = 20;
                double offsetY = 0;
                for(int i=0; i<resS.size(); i++) {
                    LightResource r = resS.get(i);
                    ImageView im = new ImageView("/images/RESOURCES/"+r.name()+".png");
                    im.setPreserveRatio(true);
                    depotPane.getChildren().add(im);
                    im.setX(offsetX);
                    im.setY(offsetY);
                    im.fitWidthProperty().bind(depotPane.widthProperty().divide(1.6));
                    //im.relocate(offsetX, offsetY);
                    if (i<resS.size()-1 && !resS.get(i+1).equals(resS.get(i))) {
                        offsetX = offsetX - 25;
                        offsetY = offsetY + 40;
                    }else {
                        offsetX = offsetX + 25;
                    }
                }
            }
        });
    }

    //helper
    //
    //
    //
    //
    //
    //

    private void disableImage(ImageView imageView){
        ColorAdjust monochrome = new ColorAdjust();
        monochrome.setSaturation(-0.8);
        imageView.setEffect(monochrome);
    }

    private void enableImage(ImageView imageView){
        imageView.setEffect(null);
    }

    private void loadFaith(Pane pane){
        /*LightFaithTrack ft = GUI.getInstance().getController().getPlayer().getPlayerBoard().getFaithTrack();
        Pane faithPane = (Pane) pane.lookup("#faith");
        ImageView im = new ImageView("/images/RESOURCES/redCross.png");
        im.setPreserveRatio(true);
        im.fitHeightProperty().bind(pane.heightProperty().divide(8.5));
        int x = -35;
        boolean isStart = true;
        for(LightFaithBox fb : ft.getBox()){
            if(fb.getActualPos()&& isStart)
                im.relocate(-10,80);
            else if (fb.getActualPos())
                im.relocate(x,45);
            x += 25;
            isStart = false;
        }
        faithPane.getChildren().add(im);

         */
    }


    private void loadDevCards(Pane pane, LightDevelopmentBoard developmentBoard){
        ArrayList<ImageView> ims = new ArrayList<>();
        double x = 30;
        double y = 0;
        for (int i = 0, j = 0; i < developmentBoard.getDecksSize() ; i++, j++){
            LightDevelopmentCard c = developmentBoard.getTopCardFromDeck(i);
            ImageView im;
            if(c.getColour() != null){
                String fileName = c.getColour().name().substring(0, 1).toUpperCase()+c.getColour().name().substring(1).toLowerCase();
                im = new ImageView("/images/DEVBOARD/"+fileName+c.getId()+".png");
            }else{
                im = new ImageView();
            }
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
            if(!isMyTurn)
                disableImage(im);
            ims.add(im);
        }
        pane.getChildren().addAll(ims);
    }

    private void devCardClick(MouseEvent mouseEvent){
        if(!isMyTurn)
            return;
        if(!askConfirmation("Buy this development card?"))
            return;
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

    public void loadMarbles(Pane pane, LightMarketBoard marketBoard) {
        //LightMarketBoard marketBoard = GUI.getInstance().getController().getMarketBoard();
        Pane marbles = (Pane) pane.lookup("#marblesPane");
        marbles.getChildren().clear();
        ArrayList<ImageView> ims = new ArrayList<>();
        double x = 0;
        double y = 50;
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
            x = 0;
            y += 65;
        }
        LightMarble freeM = marketBoard.getFreeMarble();
        String freeName = freeM.getFileName();
        ImageView im = new ImageView("/images/MARBLES/" + freeName);
        im.setPreserveRatio(true);
        im.fitHeightProperty().bind(pane.heightProperty().divide(5));
        im.relocate(180,0);
        ims.add(im);
        marbles.getChildren().addAll(ims);
    }

    private void loadMarbleSelections(Pane pane){
        ArrayList<ImageView> ims = new ArrayList<>();
        double x = 120;
        double y = 310;
        for(int i=0; i<4; i++){
            ImageView im = new ImageView("/images/arrow.png");
            im.fitHeightProperty().bind(pane.heightProperty().divide(5));
            im.setPreserveRatio(true);
            im.relocate(x,y);
            im.setId(Integer.toString(i +3));
            x += 65;
            im.setOnMouseClicked(this::marketClick);
            ims.add(im);
        }
        x = 400;
        y = 95;
        for(int i=0;i<3;i++){
            ImageView im = new ImageView("/images/arrow.png");
            im.fitHeightProperty().bind(pane.heightProperty().divide(5));
            im.setRotate(270);
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
            if (Integer.parseInt(currImage.getId()) < 3) {
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
            //loadMarbles(marketPane);
        }
    }

    //todo fixare i possibili scenari
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

    private void productionClick(MouseEvent mouseEvent, int playerIndex){
        if(playerIndex!= myPlayerIndex)
            return;
        if(!askConfirmation("Activate this production?"))
            return;
        if(!isMyTurn)
            return;

        //0     - 82.0
        //82.1  - 202.0
        //202.1 - 323.0
        //323.1 - EOF
        double x = mouseEvent.getX();
        if(x > 0 && x < 82.0){
            //default prod
            defaultProd();
        }else if(x > 82.1 && x < 202.0){
            cardProd(0);
        }else if (x > 202.1 && x < 323.0){
            cardProd(1);
        }else if (x > 323.1){
            cardProd(2);
        }

    }

    private void defaultProd(){
        ArrayList<LightResource> input = new ArrayList<>();
        GUI.getInstance().showSuccess("Choose 2 resources for input and 1 for output");
        input.add(askResources());
        input.add(askResources());
        if(input.contains(null)) {
            GUI.getInstance().showError("Production aborted");
            return;
        }
        LightResource output = askResources();
        if(output == null){
            GUI.getInstance().showError("Production aborted");
            return;
        }
        LightLeaderCard leader = askLeaderCard();
        if(leader == null){
            GUI.getInstance().getController().sendDefaultProductionRequest(input, output, null, null);
        }else{
            LightResource lastRes = askResources();
            if(lastRes==null){
                GUI.getInstance().showError("Production aborted");
                return;
            }
            GUI.getInstance().getController().sendDefaultProductionRequest(input, output, leader, lastRes);

        }

    }

    //slot 0->2 compresi
    private void cardProd(int slot){
        int numSlots = GUI.getInstance().getController().getPlayerBoard().getCardSlots().getSize();
        if(numSlots == 0) {
            GUI.getInstance().showError("no slot available");
            return;
        }
        if (GUI.getInstance().getController().getPlayerBoard().getCardSlots().getCard(slot) != null){
            LightLeaderCard leader = askLeaderCard();
            if(leader== null){
                GUI.getInstance().getController().sendDevCardProductionRequest(slot, null, null);
            }else{
                LightResource lastRes = askResources();
                if(lastRes==null){
                    GUI.getInstance().showError("Production aborted");
                    return;
                }
                GUI.getInstance().getController().sendDevCardProductionRequest(slot, lastRes, leader);
            }
        }
    }

    private LightResource askResources(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Asking for resources...");
        alert.setHeaderText("Choose a resource");
        alert.setContentText("");

        ButtonType buttonTypeOne = new ButtonType("COIN");
        ButtonType buttonTypeTwo = new ButtonType("SERVANT");
        ButtonType buttonTypeThree = new ButtonType("SHIELD");
        ButtonType buttonTypeFour = new ButtonType("STONE");

        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo, buttonTypeThree, buttonTypeFour);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeOne){
            return LightResource.COIN;
        } else if (result.get() == buttonTypeTwo) {
            return LightResource.SERVANT;
        } else if (result.get() == buttonTypeThree) {
            return LightResource.SHIELD;
        } else if (result.get() == buttonTypeFour) {
            return LightResource.STONE;
        }else {
            return null;
        }
    }

    private LightLeaderCard askLeaderCard(){
        //compra la devCard
        //ALERT_BOXES  ||
        //             \/
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("LeaderCard Activation");
        alert.setHeaderText("Do you want to use a leader card?");
        alert.setContentText("");

        ButtonType buttonTypeYes = new ButtonType("Yes");
        ButtonType buttonTypeNOOOO = new ButtonType("No");

        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNOOOO);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() != buttonTypeYes)
            return null;

        if(GUI.getInstance().getController().getPlayerBoard().getLeaderSlot().size() == 0){
            GUI.getInstance().getController().showError("You don't have any Leader Card");
            return null;
        }

        ArrayList<LightLeaderCard> cards = GUI.getInstance().getController().getPlayerBoard().getLeaderSlot();
        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("LeaderCards choosing");
        alert.setHeaderText("Choose a leader card");
        alert.setContentText("(one left, two right)");
        Pane p = new Pane();

        ButtonType buttonTypeOne = new ButtonType("One");
        ButtonType buttonTypeTwo = new ButtonType("Two");
        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

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
            return cards.get(0);
        } else if (result.get() == buttonTypeTwo) {
            return cards.get(1);
        }else{
            return null;
        }
    }

    private boolean askConfirmation(String message){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Asking for confirmation");
        alert.setHeaderText(message);
        alert.setContentText("");

        ButtonType btnYes = new ButtonType("Yes");
        ButtonType btnNo = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(btnYes, btnNo);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == btnYes){
            return true;
        } else{
            return false;
        }
    }

    private void recreateProdBaseImageview(Pane prodPane, int playerIndex){
        ImageView im = new ImageView();
        im.fitHeightProperty().bind(prodPane.heightProperty());
        im.fitWidthProperty().bind(prodPane.widthProperty());
        prodPane.getChildren().add(im);
        prodPane.setOnMouseClicked((mouseEvent) ->{
            productionClick(mouseEvent, playerIndex);
        });
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
