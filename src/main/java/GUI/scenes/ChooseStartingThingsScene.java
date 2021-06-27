package GUI.scenes;

import GUI.GUI;
import clientModel.cards.LightLeaderCard;
import clientModel.resources.LightResource;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import utilities.JsonParser;

import java.util.ArrayList;

public class ChooseStartingThingsScene {

    @FXML
    Pane cardsPane;
    @FXML
    Pane selectionCardPane;
    @FXML
    Pane resPane1;
    @FXML
    Pane selectionCardPane2;
    @FXML
    Pane resPane2;
    @FXML
    Pane selectionCardPane3;
    @FXML
    public Button sendStartingThingsBtn;
    @FXML
    public Label mainTitleLabel;
    @FXML
    public Label whatYouDeserveLabel;
    //@FXML
    //+ le label da cambiare

    private boolean faithPoints;
    private int deservedResources;

    public void setMainTitleLabel(String text) {
        this.mainTitleLabel.setText(text);
    }

    @FXML
    public void sendStartingThings(ActionEvent actionEvent) {
        ArrayList<LightLeaderCard> chosenLeaderCards = new ArrayList<>();
        ArrayList<LightResource> chosenResources = new ArrayList<>();
        for (Node n:selectionCardPane.getChildren()) {
            if(n.isVisible()){
                int id = selectionCardPane.getChildren().indexOf(n);
                int cardId = Integer.parseInt(cardsPane.getChildren().get(id).getId());
                JsonParser jsonParser = new JsonParser("");
                chosenLeaderCards.add(jsonParser.getLightLeaderCardFromId(cardId));
            }
        }

        for (Node n:selectionCardPane2.getChildren()) {
            if(n.isVisible()){
                int id = selectionCardPane2.getChildren().indexOf(n);
                LightResource resource = LightResource.valueOf(resPane1.getChildren().get(id).getId());
                chosenResources.add(resource);
            }
        }

        for (Node n:selectionCardPane3.getChildren()) {
            if(n.isVisible()){
                int id = selectionCardPane3.getChildren().indexOf(n);
                LightResource resource = LightResource.valueOf(resPane2.getChildren().get(id).getId());
                chosenResources.add(resource);
            }
        }
        //CONTROLLI
        if(chosenLeaderCards.size()==2 && (chosenResources.size() == deservedResources))
            GUI.getInstance().getController().sendStartItems(chosenLeaderCards, chosenResources, faithPoints);
        else if(chosenLeaderCards.size() != 2)
            GUI.getInstance().getController().showError("You can only choose 2 leader cards");
        else
            GUI.getInstance().getController().showError("You have to choose the resources you deserve");
    }

    public void loadLeaderCards(ArrayList<LightLeaderCard> leaderCards){
        String basePath = "/images/LEADERS/Leader";
        ArrayList<ImageView> images = new ArrayList<>();
        int x = 0;

        for(int i = 0; i < leaderCards.size(); i++){
            ImageView im = new ImageView(basePath+leaderCards.get(i).getId()+".png");
            //ImageView im = new ImageView(basePath+"11"+".png");
            im.fitHeightProperty().bind(cardsPane.heightProperty());
            im.setId(""+leaderCards.get(i).getId());
            im.setOnMouseClicked((mouseEvent)->{
                leaderImageSelection((ImageView) mouseEvent.getTarget());
            });
            im.setPreserveRatio(true);
            im.relocate(x, 0);
            x+=170;
            images.add(im);
        }

        cardsPane.getChildren().addAll(images);
    }


    public void loadResources(int numResources){
        String basePath = "/images/RESOURCES/";
        ArrayList<String> resNames = new ArrayList<>();
        resNames.add("COIN");
        resNames.add("SERVANT");
        resNames.add("SHIELD");
        resNames.add("STONE");

        whatYouDeserveLabel.setText(whatYouDeserveLabel.getText()+"\n"+"Tot obtainable resources you deserve: "+numResources);
        deservedResources = numResources;

        for(int k = 0; k<2; k++) {
            ArrayList<ImageView> images = new ArrayList<>();
            int x = 0;
            for (int i = 0; i < resNames.size(); i++) {
                ImageView im = new ImageView(basePath + resNames.get(i).toLowerCase() +".png");
                disableImage(im);
                im.fitHeightProperty().bind(resPane2.heightProperty());
                im.setId(resNames.get(i));
                im.setPreserveRatio(true);
                im.relocate(x, 0);
                x += 92;
                setCorrectResources(im, numResources, k);
                images.add(im);
            }
            if(k == 0)
                resPane1.getChildren().addAll(images);
            else
                resPane2.getChildren().addAll(images);
        }

    }

    public void showBonusFaithPoints(boolean faithPoints){
        this.faithPoints = faithPoints;
        //label
        whatYouDeserveLabel.setText("Tot bonus points you deserve: "+(faithPoints?"1":"0"));
    }

    private void leaderImageSelection(ImageView imageView){
        int cardIndex = cardsPane.getChildren().indexOf(imageView);
        selectionCardPane.getChildren().get(cardIndex).setVisible(!selectionCardPane.getChildren().get(cardIndex).visibleProperty().get());
    }

    private void resourceImageSelection(ImageView imageView, int numRes){
        if(numRes == 0){
            changeCorrectlyVisibility(imageView, resPane1, selectionCardPane2);
        }else if(numRes == 1){
            changeCorrectlyVisibility(imageView, resPane2, selectionCardPane3);

        }
    }

    private void changeCorrectlyVisibility(ImageView imageView, Pane resPane, Pane selectionCardPane) {
        int resIndex = resPane.getChildren().indexOf(imageView);
        if(!selectionCardPane.getChildren().get(resIndex).visibleProperty().get()){
            selectionCardPane.getChildren().forEach((c)->{
                c.setVisible(false);
            });
        }
        selectionCardPane.getChildren().get(resIndex).setVisible(!selectionCardPane.getChildren().get(resIndex).visibleProperty().get());
    }

    private void disableImage(ImageView imageView){
        ColorAdjust monochrome = new ColorAdjust();
        monochrome.setSaturation(-1);
        imageView.setEffect(monochrome);
    }

    private void enableImage(ImageView imageView){
        imageView.setEffect(null);
    }

    private void setCorrectResources(ImageView imageView, int numResources, int k){
        if(numResources == 2 || (numResources == 1 && k == 0)){
            enableImage(imageView);
            imageView.setOnMouseClicked((mouseEvent)->{
                resourceImageSelection((ImageView) mouseEvent.getTarget(), k);
            });
        }
    }



}
