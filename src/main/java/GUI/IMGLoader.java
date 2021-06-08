package GUI;

import javafx.scene.image.Image;

public class IMGLoader {
    public IMGLoader(){
        Image image1 = new Image("background.jpg");
        Image image2 = new Image("market.png");
        Image image3 = new Image("playerboard.jpg");
        Image image4 = new Image("marketIcon.jpg");
        Image greyMarble = new Image("MARBLES/greyMarble.png");
        Image blueMarble= new Image("MARBLES/blueMarble.png");
        Image redMarble = new Image("MARBLES/redMarble.png");
        Image yellowMarble = new Image("MARBLES/yellowMarble.png");
        Image whiteMarble = new Image("MARBLES/whiteMarble.png");
    }
    public static Image getImage(String s){
        return(new Image(s));
    }
}
