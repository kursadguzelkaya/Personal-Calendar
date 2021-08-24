/**
 * Settings page controller
 * @author
 * @version 1.0
 */
package sample.controllers;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import sample.models.User;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class SettingsController {

    //Properties
    @FXML
    private JFXButton changePasswordButton;

    @FXML
    private JFXButton changeAvatarButton;

    @FXML
    private JFXButton mainButton;

    @FXML
    private Label userNameLabel;

    @FXML
    private Pane profilePane;


    User user = MainScreenController.getUser();

    private SettingsController con;

    /**
     * set settings controller
     * @param controller
     */
    public void setSettingsController (SettingsController controller) {
        con = controller;
    }

    /**
     * load user data from database
     */
    public void loadUserData() throws IOException {
        userNameLabel.setText(user.getMyProfile().getUsername());
        changeAvatar("C:\\Users\\fener\\IdeaProjects\\Personal Calendar Latest\\src\\sample\\views\\superman.png");
        //user avatar
    }

    /**
     * change avatar pop-up
     * @throws IOException
     */
    public void changeAvatarPopUP() throws IOException {
        Stage stage;
        Parent root;
        Stage popUpStage;

        stage = stage = (Stage) changeAvatarButton.getScene().getWindow();
        popUpStage = new Stage();

        // Load change_avatar.fxml file, not exist yet
        URL url = new File("src/sample/views/change_avatar.fxml").toURI().toURL();
        FXMLLoader loader =  new FXMLLoader(url);
        root = loader.load();

        // Get changeAvatarController
        ChangeAvatarController controller = loader.getController();

        // Create new scene and show that screen
        Scene scene = new Scene(root);
        popUpStage.setScene(scene);
        popUpStage.show();

        //sent settings controller to the controller
        controller.getSettingsController(con);

        //sent current stage to the controller
        controller.setSettingStage(stage);
    }

    /**
     * change avatar of the user
     * @param inputFile
     * @throws IOException
     */
    public void changeAvatar(String inputFile) throws IOException {
        System.out.println("method worked");
        InputStream stream = new FileInputStream(inputFile);
        Image image = new Image(stream);
        System.out.println("file founded");
        ImageView imageView = new ImageView();

        //setImageView
        imageView.setImage(image);
        imageView.setX(480);
        imageView.setY(200);
        imageView.setFitWidth(150);
        imageView.setPreserveRatio(true);
        profilePane.getChildren().add(imageView);
    }

    /**
     * Uploads change password screen
     * @throws IOException
     */
    public void goChangePassword() throws IOException {
        Stage stage;
        Parent root;

        // Get window
        stage = (Stage) changePasswordButton.getScene().getWindow();

        // Load change_password.fxml file
        URL url = new File("src/sample/views/change_password.fxml").toURI().toURL();
        FXMLLoader loader =  new FXMLLoader(url);
        root = loader.load();

        // Get ChangePasswordController
        ChangePasswordController controller = loader.getController();

        // Create new scene and show that screen
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Uploads main page screen
     * @throws IOException
     */
    public void goMain() throws IOException{
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
    }
}
