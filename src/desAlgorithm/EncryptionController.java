package desAlgorithm;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;


public class EncryptionController implements Initializable {

    @FXML
    private Main application;
    @FXML
    private Button encryption;
    @FXML
    private ToggleButton generateKeyBtn;
    @FXML
    private TextArea encryptedTextArea;
    @FXML
    private TextArea senderTextArea, recipientTextArea;
    @FXML
    private TextArea plainedTextArea;
    @FXML
    private Label testLabel;
    @FXML
    private ImageView questionMark;
    @FXML
    private ImageView okMark;
    @FXML
    private static TextArea console;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    /**
     * It is used in order to encrypt the text in senderTextArea
     * @param event
     */
    @FXML
    protected void encrypt(ActionEvent event){
        String plainedText = this.senderTextArea.getText();

        String result = DesAlgorithm.encrypt(toBinaryString(plainedText));

        /**
         * Save the result of the encryption algorithm in the main application
         * to send the encrypted message to the recipient
         */
        this.application.setEncryptedWord(result);

        /**
         *
         */
        this.recipientTextArea.setText(result);
        this.questionMark.setVisible(true);
    }

    /**
     *
      * @param event
     */
    @FXML
    protected void dencrypt(ActionEvent event){
        String result = DesAlgorithm.decrypt(this.recipientTextArea.getText());
        this.okMark.setVisible(true);
        this.questionMark.setVisible(false);
        this.recipientTextArea.setText(result);
    }

    @FXML
    protected void generateKey(){
        ConsoleController.toConsole("######### chiave generata ###############");
        if( this.generateKeyBtn.isSelected()){
            //this.generateKeyBtn.setText("the key is generated");
            this.generateKeyBtn.getStyleClass().remove("padlock-open");
            this.generateKeyBtn.getStyleClass().add("padlock-closed");
            this.questionMark.setVisible(false);
            this.generateKeyBtn.setDisable(true);
        }

        DesAlgorithm.generateKey();
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

        return back;
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
