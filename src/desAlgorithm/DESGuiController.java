
package desAlgorithm;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;


public class DESGuiController implements Initializable {

    @FXML
    private Main application;
    @FXML
    private Button encryption, resetBtn;
    @FXML
    private ToggleButton ecbModeBtn, ctrModeBtn;
    @FXML
    private TextArea senderTextArea, recipientTextArea;
    @FXML
    private ImageView questionMark, okMark;

    private static String activatedMode = "ctr";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    /**
     *
     * @param event
     */
    @FXML
    protected void resetScene(ActionEvent event){
        ecbModeBtn.setSelected(false);
        ecbModeBtn.setDisable(false);

        ctrModeBtn.setSelected(false);
        ctrModeBtn.setDisable(false);

        senderTextArea.clear();
        recipientTextArea.clear();

        questionMark.setVisible(false);
        okMark.setVisible(false);

        activatedMode="ctr";

        ConsoleController.clear();
    }

    /**
     *
     */
    @FXML
    protected void setECBModes(){
        ctrModeBtn.setSelected(false);
        setMode("ecb");
        ConsoleController.toConsole("ECB mode selected");
    }

    /**
     *
     */
    @FXML
    protected void setCTRModes(){
        ecbModeBtn.setSelected(false);
        setMode("ctr");
        ConsoleController.toConsole("CTR mode selected");
    }

    /**
     *
      * @param event
     */
    @FXML
    protected void encrypt(ActionEvent event){

        ecbModeBtn.setDisable(true);
        ctrModeBtn.setDisable(true);

        if(activatedMode == "ecb"){
            ECB_Encrypt(event);
        }
        else if(activatedMode == "ctr"){
            CTR_Encrypt(event);
        }
    }

    /**
     *
     * @param event
     */
    @FXML
    protected void decrypt(ActionEvent event){
        ConsoleController.clear();
        if(activatedMode == "ecb"){
            ECB_Decrypt(event);
        }
        else if(activatedMode == "ctr"){
            CTR_Decrypt(event);
        }
    }

    /**
     *
     * @param event
     */
    @FXML
    protected void ECB_Encrypt(ActionEvent event){
        this.recipientTextArea.setText(DesAlgorithm.encrypt(toBinaryString(this.senderTextArea.getText())));
        this.questionMark.setVisible(true);
    }

    /**
     *
      * @param event
     */
    @FXML
    protected void ECB_Decrypt(ActionEvent event){
        this.okMark.setVisible(true);
        this.questionMark.setVisible(false);
        this.recipientTextArea.setText(DesAlgorithm.decrypt(this.recipientTextArea.getText()));
    }


    /**
     *
     * @param event
     */
    @FXML
    protected void CTR_Encrypt(ActionEvent event){
        String plainedText = this.senderTextArea.getText();

        this.recipientTextArea.setText(DesAlgorithm.CTREncrypt(toBinaryString(plainedText)));
        this.questionMark.setVisible(true);
    }

    /**
     *
     * @param event
     */
    @FXML
    protected void CTR_Decrypt(ActionEvent event){
        this.okMark.setVisible(true);
        this.questionMark.setVisible(false);
        this.recipientTextArea.setText(DesAlgorithm.CTRDecrypt(this.recipientTextArea.getText()));
    }

    /**
     * Converts a binary string to Ascii string
     * @param str
     * @return
     */
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

    private void setMode(String mode){
        activatedMode = mode;
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
