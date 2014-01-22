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


        String result = DesAlgorithm.encrypt(toBinaryString(plainedText));

        /**
         * Save the result of the encryption algorithm in the main application
         * to send the encrypted message to the recipient
         */
        this.application.setEncryptedWord(result);

        /**
         * It shows the result of the encryption algorithm like a string
         */
        this.encryptedTextArea.setText(new String(result.toString()));
        this.encryptedTextArea.setVisible(true);
    }

    private String toBinaryString(String str){
        byte[] bytes = str.getBytes();
        StringBuilder binary = new StringBuilder();
        for (byte b : bytes)
        {
            int val = b;
            for (int i = 0; i < 8; i++)
            {
                binary.append((val & 128) == 0 ? 0 : 1);
                val <<= 1;
            }
        }
        String back = new String(binary);
        System.out.println("****** to binary string *******");
        System.out.println(back);
        System.out.println("****** to binary string *******");
        return back;
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
