package GUI;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class GuiController implements Initializable {
    Gui gui;

    public GuiController(Gui gui){
        this.gui = gui;
    }

    public void marketAction(double x, double y, GraphicsContext gc) {
        if (x >= 141.0 && x <= 173.0 && y >= 360.0 && y <= 437.0)
            gc.clearRect(0, 0, 190, 350);//column1
        if (x >= 196.0 && x <= 228.0 && y >= 360.0 && y <= 437.0)
            gc.clearRect(180, 0, 60, 350);//column2
        if (x >= 254.0 && x <= 285.0 && y >= 360.0 && y <= 437.0)
            gc.clearRect(240, 0, 60, 350);//column3
        if (x >= 310.0 && x <= 341.0 && y >= 360.0 && y <= 437.0)
            gc.clearRect(300, 0, 60, 350);//column4
        if (x >= 419.0 && x <= 466.0 && y >= 138.0 && y <= 169.0)
            gc.clearRect(0, 0, 600, 180);//line1
        if (x >= 419.0 && x <= 466.0 && y >= 194.0 && y <= 225.0)
            gc.clearRect(0, 176, 600, 60);//line2
        if (x >= 419.0 && x <= 466.0 && y >= 249.0 && y <= 281.0)
            gc.clearRect(0, 231, 600, 60);//line3
    }

    public void devEvent(double x, double y, GraphicsContext gc) {
        if (x >= 74.0 && x <= 168.0 && y >= 4.0 && y <= 145.0)//green1
            gc.clearRect(0, 0, 170, 150);
        if (x >= 74.0 && x <= 168.0 && y >= 151.0 && y <= 291.0)//green2
            gc.clearRect(0, 150, 170, 150);
        if (x >= 74.0 && x <= 168.0 && y >= 300.0 && y <= 437.0)//green3
            gc.clearRect(0, 300, 170, 150);
        if (x >= 205.0 && x <= 300.0 && y >= 4.0 && y <= 145.0)//blue1
            gc.clearRect(200, 0, 110, 150);
        if (x >= 205.0 && x <= 300.0 && y >= 151.0 && y <= 291.0)//blue2
            gc.clearRect(200, 150, 110, 150);
        if (x >= 205.0 && x <= 300.0 && y >= 300.0 && y <= 437.0)//blue3
            gc.clearRect(200, 300, 110, 150);
        if (x >= 335.0 && x <= 428.0 && y >= 4.0 && y <= 145.0)//yellow1
            gc.clearRect(330, 0, 110, 150);
        if (x >= 335.0 && x <= 428.0 && y >= 300.0 && y <= 291.0)//yellow2
            gc.clearRect(330, 150, 110, 150);
        if (x >= 335.0 && x <= 428.0 && y >= 300.0 && y <= 437.0)//yellow3
            gc.clearRect(330, 300, 110, 150);
        if (x >= 465.0 && x <= 560.0 && y >= 4.0 && y <= 145.0)//purple1
            gc.clearRect(460, 0, 110, 150);
        if (x >= 465.0 && x <= 560.0 && y >= 151.0 && y <= 291.0)//purple2
            gc.clearRect(460, 150, 110, 150);
        if (x >= 465.0 && x <= 560.0 && y >= 300.0 && y <= 437.0)//purple3
            gc.clearRect(460, 300, 110, 150);
    }

    public void leaderEvent(double x, double y, GraphicsContext gc) {
        if(x>=55.0&&x<=245.0&&y>=73.0&&y<=360.0)//leader1
                gc.clearRect(50,70,200,300);
        if(x>=320.0&&x<=510.0&&y>=73.0&&y<=360.0)//leader2
                gc.clearRect(315,70,200,300);
    }

    public void playerEvent(double x, double y, GraphicsContext gc){
        if(x>=153.0&&x<=224.0&&y>=302.0&&y<=369.0)//default
            System.out.println("default");
        if(x>=245.0&&x<=344.0&&y>=220.0&&y<=369.0)//prod1
            System.out.println("prod1");
        if(x>=365.0&&x<=464.0&&y>=220.0&&y<=369.0)//prod2
            System.out.println("prod2");
        if(x>=485.0&&x<=584.0&&y>=220.0&&y<=369.0)//prod3
            System.out.println("prod3");
    }

    public void buttonEvent(String buttonName){
        switch(buttonName){
            case "single" ->{
                gui.mainPane.getChildren().remove(gui.gameSelBox);
                gui.mainPane.getChildren().add(gui.bPane);
            }
            case "join"->{
                gui.mainPane.getChildren().remove(gui.gameSelBox);
                gui.mainPane.getChildren().add(gui.lobbyAPane);
            }
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}