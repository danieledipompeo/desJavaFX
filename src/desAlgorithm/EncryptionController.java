package desAlgorithm;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ResourceBundle;


public class EncryptionController implements Initializable {

    @FXML
    private Main application;

    @FXML
    private Button encryption;

    @FXML
    private TextArea encryptedTextArea;
    @FXML
    private TextArea plainedTextArea;
    @FXML
    private Label testLabel;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    /**
     * It is used in order to encrypt the text inserted
     * @param event
     */
    @FXML
    protected void encrypt(ActionEvent event){
        String plainedText = this.plainedTextArea.getText();

        //String result = Integer.toBinaryString(DesAlgorithm.encrypt(plainedText));
        //String result = Integer.toBinaryString(DesAlgorithm.encrypt("101001100111"));
        String result = DesAlgorithm.encrypt("101001100111");

        /**
         * Save the result of the encryption algorithm in the main application
         * to send the encrypted message to the recipient
         */
        this.application.setEncryptedWord(result);

        // *****************************************************************************

        DesAlgorithm.expansionFunction("011001");

        // *****************************************************************************

        /**
         * It shows the result of the encryption algorithm like a string
         */
        this.encryptedTextArea.setText(new String(result.toString()));
        this.encryptedTextArea.setVisible(true);
    }

    /**
     * onAction from the main.fxml file after action on sendMessage button
     * change the scene on the application
     */
    @FXML
    protected void sendMessage(){
        this.application.setEncryptedTextArea(this.encryptedTextArea);
        this.application.gotoDecrypt();
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

    /**
     * It shall be used to set the text on EncryptTextArea in main.fxml
     * @param text
     */
    public void setEncryptedTextArea(TextArea text){
        this.encryptedTextArea.setText(text.getText());
    }

    public TextArea getEncryptedTextArea(){
        return this.encryptedTextArea;
    }
}
