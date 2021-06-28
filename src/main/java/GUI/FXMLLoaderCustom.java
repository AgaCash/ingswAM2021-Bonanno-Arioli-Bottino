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
