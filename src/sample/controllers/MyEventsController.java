/**
 * My events page controller
 * @author Kürşad Güzelkaya, Hazal Buluş
 * @version 1.0
 */
package sample.controllers;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sample.models.Event;
import sample.models.User;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class MyEventsController {
    //Properties
    @FXML
    private JFXButton mainButton;

    @FXML
    private JFXButton logoutButton;

    @FXML
    private VBox eventVBox;

    User user = MainScreenController.getUser();

    /**
     * load events of a user
     * @param user
     */
    public void loadEvents(User user) {
        eventVBox.getChildren().clear();
        eventVBox.setAlignment(Pos.CENTER);
        eventVBox.setSpacing(5);

        for (Event event: user.getMyEvents()) {
            JFXButton eventButton = new JFXButton();
            eventButton.setText(event.getName());
            eventButton.setStyle("-fx-background-color: #c0c0c0;" + "-fx-font-size: 18px;" + "-fx-text-fill: #ff0000;");
            eventButton.setPrefSize(200,30);
            eventButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    try {
                        viewEventDetails(event);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            eventVBox.getChildren().add(eventButton);
        }
    }

    /**
     * go to the main screen
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
    }

    /**
     * logout and go to login screen
     * @throws IOException
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public void logout() throws IOException, ExecutionException, InterruptedException {
        Stage stage;
        Parent root;

        // Get window
        stage = (Stage) logoutButton.getScene().getWindow();

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

    /**
     * view details of an event
     * @param event
     * @throws IOException
     */
    public void viewEventDetails(Event event) throws IOException {
        Stage stage;
        Parent root;

        //Get window
        stage = new Stage();

        //load view_event_details.fxml
        URL url = new File("src/sample/views/view_event_details.fxml").toURI().toURL();
        FXMLLoader loader =  new FXMLLoader(url);
        root = loader.load();

        //Get view event controller
        viewEventController controller = loader.getController();
        controller.loadEvent(event);

        // Create new scene and show that screen
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        //sent current stage to the controller
        controller.setStage(stage);
    }
}
