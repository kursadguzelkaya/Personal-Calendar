/**
 * Change user's profile avatar controller
 * @author
 * @version 1.0
 */
package sample.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class ChangeAvatarController {

    //Properties
    @FXML
    private  TextField fileText;

    private SettingsController settingsController;

    private Stage settingStage;

    /**
     * gets settings stage from previous stage and stores it
     * @param stage
     */
    public void setSettingStage(Stage stage) {
        settingStage = stage;
    }

    /**
     * gets settings controller from pervious stage and stores it
     * @param con
     */
    public void getSettingsController(SettingsController con) {
        settingsController = con;
    }

    /**
     * executes the changeAvatar method from settings stage, and refresh settings calendar stage
     * @throws IOException
     */
    public void changeAction( ) throws IOException{
        settingsController.changeAvatar(fileText.getText());
        refresh();
    }

    /**
     * refreshs settings stage
     * @throws IOException
     */
    public void refresh() throws IOException{
        Parent root;

        // Load change_avatar.fxml file, not exist yet
        URL url = new File("src/sample/views/settings.fxml").toURI().toURL();
        FXMLLoader loader =  new FXMLLoader(url);
        root = loader.load();

        // Get changeAvatarController
        ChangeAvatarController controller = loader.getController();

        // Create new scene and show that screen
        Scene scene = new Scene(root);
        settingStage.setScene(scene);
        settingStage.show();
    }
}
