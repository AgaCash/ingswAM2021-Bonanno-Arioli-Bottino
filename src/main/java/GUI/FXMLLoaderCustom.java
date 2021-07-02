package GUI;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

/**
 * Class to load the different graphic scene (FXML) to set the central in-game view
 */
public class FXMLLoaderCustom {
    private Pane view;

    /** method to load the correct fxml to set the central in-game view
     * @param fileName the fxml file name to load
     * @return the correct pane to show
     */
    public Pane getPage(String fileName){
        try {
            view = FXMLLoader.load(getClass().getResource("/FXMLFiles/" + fileName + ".fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return view;
    }

}
