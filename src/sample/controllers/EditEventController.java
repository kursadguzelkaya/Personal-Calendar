/**
 * Edit event pop-up controller
 * @author Kürşad Güzelkaya,
 * @version 1.0
 */
package sample.controllers;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.models.Calendar;
import sample.models.DataSource;
import sample.models.Event;
import sample.models.User;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;

public class EditEventController {

    //Properties
    @FXML
    private DatePicker dueDatePicker;

    @FXML
    private DatePicker assignDatePicker;

    @FXML
    private JFXButton saveButton;

    @FXML
    private TextField eventNameField;

    @FXML
    private TextField EventTypeField;

    @FXML
    private JFXButton starButton1;

    User user = MainScreenController.getUser();
    Event event;
    Calendar calendar;

    private Stage currentStage;
    private Stage popUpStage;

    /**
     * gets current stage and stores it
     * @param stage
     */
    public void setStage(Stage stage) {
        currentStage = stage;
    }
    /**
     * gets current popup stage and stores it
     * @param stage
     */
    public void setPopupStage(Stage stage) {
        popUpStage = stage;
    }

    /**
     * get data of the event and the calendar
     * @param theEvent
     * @param theCalendar
     */
    public void getData(Event theEvent, Calendar theCalendar) {
        event = theEvent;
        calendar = theCalendar;
    }

    /**
     * Save changes made in event
     * @throws IOException
     */
    public void saveEvent() throws IOException {
        String eventName = eventNameField.getText();
        String eventType = EventTypeField.getText();
        LocalDate dueDate = dueDatePicker.getValue();
        LocalDate assignDate = assignDatePicker.getValue();

        System.out.println(calendar);

        if (calendar == null) {
            return;
        }

        if (event != null) {
            // Edit event
            DataSource.getInstance().updateEvent(event.getId(), eventName, eventType, dueDate, assignDate, event.getCalendarID());

        } else {
            int event_id = 0;
            // Create new event
            Event event = new Event(eventName, eventType, dueDate, assignDate, 12, calendar.getId() );
            DataSource.getInstance().postEvent(eventName, eventType, dueDate, assignDate, calendar.getId());
            for (Event eachEvent: DataSource.getInstance().getAllEvents()) {
                if (eachEvent.getName().equals(eventName) && eachEvent.getType().equals(eventType) && eachEvent.getAssignDate().equals(assignDate)) {
                    event_id = eachEvent.getId();
                }
            }
            DataSource.getInstance().addMyEventToDataBase(event_id, user.getMyProfile().getId());
            for (Calendar userCalendar: user.getMyCalendars()) {
                if (calendar.getId() == userCalendar.getId()) {
                    userCalendar.addEvent(event);
                    user.addMyEvent(event);
                }
            }
            MainScreenController.setUser(user);
        }
        refreshStage();
        popUpStage.close();
;    }

    /**
     * refreshes events stage
     * @throws IOException
     */
    public void refreshStage() throws IOException {
        Parent root;

        // Load main_screen.fxml file
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

    public void addToFav() throws IOException{
        starButton1.setStyle("-fx-text-fill: #ffd400");
    }
}
