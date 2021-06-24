package GUI.scenes;

import GUI.GUI;
import clientView.View;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class ServerSetupScene {

    @FXML
    Button serverBTN;
    @FXML
    TextField serverTXT;
    @FXML
    TextField portTXT;

    String server;
    int port;
    GUI gui = new GUI();

    @FXML
    private void sendServer(ActionEvent event){
        String portS;
        if(serverTXT.getText().isBlank())
            server = "127.0.0.0";
        else
            server = serverTXT.getText();
        if(portTXT.getText().isBlank())
            portS = "1234";
        else
            portS = portTXT.getText();
        port = Integer.parseInt(portS);
        gui.askServerInfo();
    }

    public String getServer(){
        return server;
    }
    public int getPort(){
        return port;
    }
}
