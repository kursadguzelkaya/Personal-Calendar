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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.models.Calendar;
import sample.models.DataSource;
import sample.models.User;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class EditCalendarController {

    //Properties
     @FXML
    private TextField calendarNameField;

    @FXML
    private JFXButton saveButton;

    private Calendar currentCalendar;

    private Stage currentCalendarStage;

    private Stage popUpStage;

    User user = MainScreenController.getUser();

    /**
     * gets current popup stage and stores it
     * @param stage
     */
    public void setPopUpStage(Stage stage){
        popUpStage = stage;
    }

    /**
     * gets current calendar stage and stores it
     * @param stage
     */
    public void setStage(Stage stage) {

        currentCalendarStage = stage;
    }

    /**
     * gets current calendar and stores it
     * @param cal
     */
    public void loadCalendar(Calendar cal) {
        currentCalendar = cal;
        calendarNameField.setText(cal.getTitle());
    }

    /**
     * updates the calendar
     * @throws IOException
     */
    public void saveCalendar() throws IOException{
        DataSource ds = DataSource.getInstance();
        currentCalendar.setTitle(calendarNameField.getText());
        ds.updateCalendar(currentCalendar.getId(), currentCalendar.getTitle());

        refreshStage();
        popUpStage.close();
    }

    /**
     * deletes current calendar
     * @throws IOException
     */
    public void deleteCalendar() throws IOException{
        DataSource.getInstance().deleteCalendar(currentCalendar.getId());
        DataSource.getInstance().deleteMyCalendar(currentCalendar.getId(), user.getMyProfile().getId());
        DataSource.getInstance().deleteFavCalendar(currentCalendar.getId(), user.getMyProfile().getId());
        user.removeFavCal(currentCalendar);
        user.removeMyCal(currentCalendar);

        //DataSource.getInstance().deleteMyCalendar(currentCalendar);
        refreshStage();
        popUpStage.close();
    }

    /**
     * refreshes calendar stage
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
        currentCalendarStage.setScene(scene);
        currentCalendarStage.show();
    }
}
