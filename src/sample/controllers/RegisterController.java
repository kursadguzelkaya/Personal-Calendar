/**
 * Register page controller
 * @author ,
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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.models.DataSource;
import sample.models.User;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class RegisterController {
    //Properties

    @FXML
    private JFXButton signUpButton;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField retypePasswordField;

    @FXML
    private JFXButton backToLoginButton;

    @FXML
    private Label errorLabel;


    ArrayList<User> users;

    /**
     * loads all users from the database
     * @param allUsers
     */
    public void getAllUsers(ArrayList<User> allUsers) {
        users = allUsers;
    }

    /**
     * register the user to the database
     * @return if operation is succesful
     * @throws IOException
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public boolean signUp() throws IOException, ExecutionException, InterruptedException{
        Stage stage;
        DataSource ds = DataSource.getInstance();

        //Get window
        stage = (Stage) signUpButton.getScene().getWindow();
        System.out.println(users);
        //checks if the username used before
        for(int i = 0; i < users.size(); i++)
        {
            if(users.get(i).getMyProfile().getUsername().equals(usernameField.getText()))
            {
                errorLabel.setText("There is already an user named: " + usernameField.getText());
                usernameField.setText("");
                passwordField.setText("");
                retypePasswordField.setText("");
                emailField.setText("");
                errorLabel.setVisible(true);
                return false;
            }
        }

        //check passwords and creates user on database if the passwords are same
        if (passwordField.getText().equals(retypePasswordField.getText())){
            ds.postProfile(usernameField.getText(),passwordField.getText(),emailField.getText());
            this.backToLoginScreen();
            return true;
        }
        else {
            usernameField.setText("");
            passwordField.setText("");
            retypePasswordField.setText("");
            emailField.setText("");
            errorLabel.setText("Passwords do not match. Retry!");
            errorLabel.setVisible(true);
            return false;
        }
    }

    /**
     * go back to login screen
     * @throws IOException
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public void backToLoginScreen() throws IOException, ExecutionException, InterruptedException{
        Stage stage;
        Parent root;

        // Get window
        stage = (Stage) backToLoginButton.getScene().getWindow();

        // Load login_screen.fxml file
        URL url = new File("src/sample/views/login_screen.fxml").toURI().toURL();
        FXMLLoader loader =  new FXMLLoader(url);
        root = loader.load();

        // Get WelcomeController
        WelcomeController controller = loader.getController();
        controller.getUsers();

        // Create new scene and show that screen
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
