package desAlgorithm;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ResourceBundle;

public class DecryptionController implements Initializable {

    @FXML
    private Main application;

    @FXML
    private Button decryptionMessageBtn;
    @FXML
    private TextArea encryptionTextArea;
    @FXML
    private TextArea plainedTextArea;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    protected void dencrypt(ActionEvent event){
        String encryptedText = this.encryptionTextArea.getText();

        this.plainedTextArea.setText(encryptedText);
        this.plainedTextArea.setVisible(true);
    }

    @FXML
    protected void sendMessage(){
         //TODO
    }

    public void setApp(Main application){
        this.application = application;
    }

    public Main getApp(){
        return this.application;
    }

    public void setEncryptedTextArea(TextArea text){
        this.encryptionTextArea.setText(text.getText());
    }

    public TextArea getEncryptedTextArea(){
        return this.encryptionTextArea;
    }

}
