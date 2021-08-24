/**
 * Edit calendar pop-up controller
 * @author Kürşad Güzelkaya,
 * @version 1.0
 */
package sample.controllers;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import sample.models.DataSource;
import sample.models.User;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class ChangePasswordController {

    //Properties
    @FXML
    private PasswordField currentPasswordField;

    @FXML
    private PasswordField changePasswordField; //add id to the fxml file

    @FXML
    private PasswordField reTypePasswordField;

    @FXML
    private JFXButton submitButton;

    @FXML
    private JFXButton mainButton;

    @FXML
    private JFXButton editProfileButton;

    @FXML
    private Label errorLabel;

    User user = MainScreenController.getUser();

    /**
     * changes the user's password
     */
    public void changePassword() {
        DataSource ds = DataSource.getInstance();
        errorLabel.setVisible(false);

        //Change the user's password in database
        if ( !currentPasswordField.getText().equals( changePasswordField.getText()) && changePasswordField.getText().equals(reTypePasswordField.getText()))
        {
           ds.updateProfile( user.getMyProfile().getId(), user.getMyProfile().getUsername(), changePasswordField.getText(), user.getMyProfile().geteMail());
           user.getMyProfile().setPassword( changePasswordField.getText());
           errorLabel.setText("Successful");
           errorLabel.setVisible(true);
           currentPasswordField.setText("");
           changePasswordField.setText("");
           reTypePasswordField.setText("");
        }
        //If retyped password does not match the new password field show error message
        else if(!changePasswordField.getText().equals(reTypePasswordField.getText())){
            currentPasswordField.setText("");
            changePasswordField.setText("");
            reTypePasswordField.setText("");
            errorLabel.setText("You retyped your password wrong");
            errorLabel.setVisible(true);
        }
        //If user types previous password in new password field show error message
        else{
            errorLabel.setText("your password cannot be the same as your previous one");
            currentPasswordField.setText("");
            changePasswordField.setText("");
            reTypePasswordField.setText("");
            errorLabel.setVisible(true);
        }
    }
    /**
     * uploads edit profile stage
     * @throws IOException
     */
    public void goEditProfile() throws IOException{
        Stage stage;
        Parent root;

        // Get window
        stage = (Stage) editProfileButton.getScene().getWindow();

        // Load  file
        URL url = new File("src/sample/views/settings.fxml").toURI().toURL();
        FXMLLoader loader =  new FXMLLoader(url);
        root = loader.load();

        // Get SettingsController
        SettingsController controller = loader.getController();
        controller.loadUserData();

        // Create new scene and show that screen
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * uploads main screen stage
     * @throws IOException
     */
    public void goMain() throws IOException {
        Stage stage;
        Parent root;

        // Get window
        stage = (Stage) mainButton.getScene().getWindow();

        // Load main_screen.fxml file
        URL url = new File("src/sample/views/main_screen.fxml").toURI().toURL();
        FXMLLoader loader =  new FXMLLoader(url);
        root = loader.load();

        // Get MainScreenController
        MainScreenController controller = loader.getController();
        controller.loadUserData(user);

        // Create new scene and show that screen
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        //sent current stage
        controller.getCurrentStage(stage);
    }
}
