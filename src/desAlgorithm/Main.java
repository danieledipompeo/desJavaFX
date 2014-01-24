/*
 * Copyright (c) 2012, Oracle and/or its affiliates. All rights reserved.
 */
package desAlgorithm;

import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Main Application. This class handles navigation and user session.
 */
public class Main extends Application {

    private Stage stage;
    private final double MINIMUM_WINDOW_WIDTH = 800.0;
    private final double MINIMUM_WINDOW_HEIGHT = 461.0;

    private TextArea encryptTextArea;
    private TextArea decryptTextArea;
    private String encryptText;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Application.launch(Main.class, (java.lang.String[])null);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            stage = primaryStage;
            stage.setTitle("simplified version of DES by Daniele Di Pompeo");
            stage.setMinWidth(MINIMUM_WINDOW_WIDTH);
            stage.setMinHeight(MINIMUM_WINDOW_HEIGHT);
            gotoEncrypt();
            primaryStage.show();

            Stage consoleStage = new Stage();
            Parent consoleParent = FXMLLoader.load(getClass().getResource("consoleScene.fxml"));
            Scene consoleScene = new Scene(consoleParent);
            consoleStage.setScene(consoleScene);
            consoleStage.setTitle("console of S-DES");

            consoleStage.show();

        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     */
    private void gotoEncrypt() {
        try {
            EncryptionController encryption = (EncryptionController) replaceSceneContent("alice&bob.fxml");
            encryption.setApp(this);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param textArea
     */
    public void setEncryptedTextArea(TextArea textArea){
        this.encryptTextArea = textArea;
    }

    /**
     *
     * @param fxml
     * @return
     * @throws Exception
     */
    private Initializable replaceSceneContent(String fxml) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        InputStream in = Main.class.getResourceAsStream(fxml);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(Main.class.getResource(fxml));
        AnchorPane page;
        try {
            page = (AnchorPane) loader.load(in);
        } finally {
            in.close();
        }
        Scene scene = new Scene(page, 800, 461);
        stage.setScene(scene);
        stage.sizeToScene();
        return (Initializable) loader.getController();
    }

    public void setEncryptedWord(String result) {
        this.encryptText = result;
    }
    public String getEncryptedWord() {
        return this.encryptText;
    }
}
