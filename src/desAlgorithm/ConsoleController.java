package desAlgorithm;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ResourceBundle;


public class ConsoleController implements Initializable {

    @FXML
    private Main application;
    @FXML
    private static TextArea console;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public static void toConsole(String text){
        console.appendText(text);
        console.appendText("\n");
    }

    /**
     * It shall be used to set the current main scene
     * @param application
     */
    public void setApp(Main application){
        this.application = application;
    }

    public Main getApp(){
        return this.application;
    }
}
