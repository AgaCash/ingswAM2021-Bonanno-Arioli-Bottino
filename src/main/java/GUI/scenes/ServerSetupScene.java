package GUI.scenes;

import GUI.GUI;
import clientView.View;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;

public class ServerSetupScene {

    @FXML
    Button serverBTN;
    @FXML
    TextField serverTXT;
    @FXML
    TextField portTXT;
    @FXML
    ProgressIndicator progressIndicator;

    String server;
    int port;

    @FXML
    private void sendServer(ActionEvent event){
        String portS;
        if(serverTXT.getText().isBlank())
            server = "127.0.0.1";
        else
            server = serverTXT.getText();
        if(portTXT.getText().isBlank())
            portS = "1234";
        else
            portS = portTXT.getText();
        try {
            port = Integer.parseInt(portS);
            serverBTN.setDisable(true);
            progressIndicator.setVisible(true);
            GUI.getInstance().getController().connectToServer(server, port);
        }catch(NumberFormatException n){
            GUI.getInstance().getController().showError(n.getMessage());
        }
    }

    public String getServer(){
        return server;
    }
    public int getPort(){
        return port;
    }
}
