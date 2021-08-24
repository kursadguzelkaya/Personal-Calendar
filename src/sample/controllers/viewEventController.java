/**
 * Event details pop-up controller
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
import javafx.stage.Stage;
import sample.models.Calendar;
import sample.models.DataSource;
import sample.models.Event;
import sample.models.User;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class viewEventController {

    //Properties
    @FXML
    private JFXButton closeButton;

    @FXML
    private JFXButton deleteButton;

    @FXML
    private JFXButton starButton;

    @FXML
    private Label eventName;

    @FXML
    private Label eventType;

    @FXML
    private Label dueDate;

    @FXML
    private Label assignedDate;

    private Stage popUpStage;

    private Event currentEvent;

    private Stage currentStage;


    User user = MainScreenController.getUser();

    //Methods

    /**
     * gets current stage
     * @param stage
     */
    public void getCurrentStage(Stage stage) {
        currentStage = stage;
    }

    /**
     * loads events
     * @param event
     */
    public void loadEvent(Event event) {
        eventName.setText(event.getName());
        eventType.setText(event.getType());
        dueDate.setText(event.getDueDate() +"");
        assignedDate.setText(event.getAssignDate() +"");

        currentEvent = event;

        if (user.getFavoEvents().contains(currentEvent)) {
            starButton.setStyle("-fx-text-fill: #ffd400");
        } else {
            starButton.setStyle("-fx-text-fill: #605555");
        }
    }

    public void setStage(Stage stage){
        popUpStage = stage;
    }

    public void close() {
       popUpStage.close();
    }

    /**
     * Delete selected event from both database and user's calendar
     * @throws IOException
     */
    public void deleteEvent() throws IOException{
        //  Delete from DB
        DataSource ds = DataSource.getInstance();
        ds.deleteEvent(currentEvent.getId());
        ds.deleteMyEvent(currentEvent.getId(), user.getMyProfile().getId());
        ds.deleteFavEvent(currentEvent.getId(), user.getMyProfile().getId());

        // Delete from user
        for (Calendar calendar: user.getMyCalendars()) {
            if (calendar.getId() == currentEvent.getCalendarID()) {
                calendar.removeEvent(currentEvent);
            }
        }
        user.removeMyEvent(currentEvent);
        user.removeFavEvent(currentEvent);

        refreshStage();
        close();
    }
    /**
     * refreshes calendar stage
     * @throws IOException
     */
    public void refreshStage() throws IOException {
        Parent root;

        // Load calendars.fxml file
        URL url = new File("src/sample/views/calendars.fxml").toURI().toURL();
        FXMLLoader loader =  new FXMLLoader(url);
        root = loader.load();

        // Get mainScreenController
        MyCalendarsController controller = loader.getController();
        controller.loadUserData(user);

        // Create new scene and show that screen
        Scene scene = new Scene(root);
        currentStage.setScene(scene);
        currentStage.show();
    }

    /**
     * Adds event to favorites when star button  on the screen is clicked
     */
    public void addToFav(){
        if (user.getFavoEvents().contains(currentEvent)) {
            //remove from favorites
            starButton.setStyle("-fx-text-fill: #605555");
            DataSource.getInstance().deleteFavEvent(currentEvent.getId(), user.getMyProfile().getId());
            user.removeFavEvent(currentEvent);
        } else {
            //add to favorites
            starButton.setStyle("-fx-text-fill: #ffd400");
            DataSource.getInstance().addFavEventToDataBase(currentEvent.getId(), user.getMyProfile().getId());
            user.addFavEvent(currentEvent);
        }
    }
}
