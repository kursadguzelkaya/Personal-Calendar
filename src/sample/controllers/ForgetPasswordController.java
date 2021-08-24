/**
 * Forget password screen controller
 * @author Ege Ayan,
 * @version 1.0
 */
package sample.controllers;

import com.jfoenix.controls.JFXButton;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.javaMailAPI.Mail;
import sample.models.DataSource;
import sample.models.User;

import javax.mail.MessagingException;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class ForgetPasswordController {

    //Properties
    @FXML
    private TextField forgetPasswordEmailField;

    @FXML
    private TextField forgetPasswordUserNameField;

    @FXML
    private JFXButton backtoLoginScreenButton;

    /**
     * get all users from database
     * @throws ExecutionException
     * @throws InterruptedException
     */
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
     * sends user's password to the user's email if username and e-mail exist
     * @throws IOException
     * @throws ExecutionException
     * @throws InterruptedException
     * @throws MessagingException
     */
    public void forgetPassword() throws IOException, ExecutionException, InterruptedException, MessagingException {
        for (User user : users) {
           if (user.getMyProfile().getUsername().equals(forgetPasswordUserNameField.getText())) {
                if (user.getMyProfile().geteMail().equals(forgetPasswordEmailField.getText())) {
                    Mail mail = new Mail();
                    mail.send("personalcalendarfp@gmail.com", "PersonalCalendar2021", forgetPasswordEmailField.getText(),
                            "Forget Password?", "Your Password is " + user.getMyProfile().getPassword());
                }
                //Show error message if typed e-mail address is wrong
                else {
                    System.out.println("E-Mail and user do not match");
                }
           }
           //Show error message if there is no such user having the username
           else {
               System.out.println("There is no such user");
           }
        }
    }
    /**
     * go back to the login screen
     * @throws IOException
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public void backtoLoginScreen() throws IOException, ExecutionException, InterruptedException{
        Stage stage;
        Parent root;

        // Get window
        stage = (Stage) backtoLoginScreenButton.getScene().getWindow();

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
