/**
 * Welcome and Login Screen controller
 * @author Kürşad Güzelkaya
 * @version 1.0
 */
package sample.controllers;

import com.jfoenix.controls.JFXButton;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.models.DataSource;
import sample.models.User;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class WelcomeController {

    //Properties
    @FXML
    private JFXButton loginButton;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;

    @FXML
    private Label errorMessage;

    @FXML
    private JFXButton registerButton;

    @FXML
    private JFXButton forgetPasswordButton;

    ArrayList<User> users;
    public void getUsers() throws ExecutionException, InterruptedException {
        Task<ArrayList<User>> task = new Task<ArrayList<User>>() {
            @Override
            protected ArrayList<User> call() throws Exception {
                users = DataSource.getInstance().getUsers();
                return users;
            }
        };
        new Thread(task).start();
    }

    /**
     * If username and password match load main screen
     * otherwise show error message
     * @throws IOException
     */
    public void login() throws IOException {
        Stage stage;
        Parent root;
        int userMatch = 0;
        for (User user : users) {
            if (user.getMyProfile().getUsername().equals(usernameField.getText())) {
                if (user.getMyProfile().getPassword().equals(passwordField.getText())) {
                    // Get window
                    stage = (Stage) loginButton.getScene().getWindow();
                    System.out.println("Stage: " + loginButton.getScene().getWindow());

                    // Load main_screen.fxml file
                    URL url = new File("src/sample/views/main_screen.fxml").toURI().toURL();
                    FXMLLoader loader =  new FXMLLoader(url);
                    root = loader.load();

                    // Get mainScreenController
                    MainScreenController controller = loader.getController();

                    // Create new scene and show that screen
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();

                    controller.getCurrentStage(stage);
                    controller.loadUserData(user);
                    controller.setUser(user);
                }
                //Show error message if password and user name do not match
                else {
                    System.out.println("Password incorrect!!!");
                    errorMessage.setText("Password incorrect!!!");
                    passwordField.setText("");
                }
                userMatch++;
            }
        }
        //Print error message and clean textfields if there is no such user in database
        if (userMatch == 0) {
            System.out.println("User not found!!");
            errorMessage.setText("User not found!!");
            usernameField.setText("");
            passwordField.setText("");
        }
    }

    /**
     * uploads register screen stage
     * @throws IOException
     */
    public void openRegisterPage() throws IOException {
        Stage stage;
        Parent root;

        // Get window
        stage = (Stage) registerButton.getScene().getWindow();

        // Load register_screen.fxml file
        URL url = new File("src/sample/views/register.fxml").toURI().toURL();
        FXMLLoader loader =  new FXMLLoader(url);
        root = loader.load();

        // Get registerScreenController
        RegisterController controller = loader.getController();
        controller.getAllUsers(users);

        // Create new scene and show that screen
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * uploads forget password screen stage
     * @throws IOException
     */
    public void openForgetPasswordPage() throws IOException, ExecutionException, InterruptedException {
        Stage stage;
        Parent root;

        //Get window
        stage = (Stage) forgetPasswordButton.getScene().getWindow();

        //Load forgetPassword FXML - not exist yet
        URL url = new File("src/sample/views/forget_password.fxml").toURI().toURL();
        FXMLLoader loader =  new FXMLLoader(url);
        root = loader.load();

        //Get ForgetPasswordController
        ForgetPasswordController controller = loader.getController();
        controller.getUsers();

        //create new scene and show that screen
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}//End of the controller
