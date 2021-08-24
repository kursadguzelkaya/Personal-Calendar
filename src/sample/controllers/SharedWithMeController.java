/**
 * Shared with me controller
 * @author Kürşad Güzelkaya,
 * @version 1.0
 */
package sample.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sample.models.Calendar;
import sample.models.Event;
import sample.models.User;
import sample.views.AnchorPaneNode;
import sample.views.MonthlyCalendarView;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class SharedWithMeController {
    //properties
    @FXML
    private JFXButton mainButton;

    @FXML
    private JFXButton logoutButton;

    @FXML
    private JFXButton myCalendarsButton;

    @FXML
    private Pane calendarPane;

    @FXML
    private VBox calendarsVBox;

    @FXML
    private VBox eventsVBox;

    User user = MainScreenController.getUser();

    MonthlyCalendarView monthlyCalendar;

    VBox monthlyCalendarView;

    private Stage calendarStage;

    public void setCalendarStage(Stage calendarStage) {
        this.calendarStage = calendarStage;
    }

    /**
     * load user data
     * @param user
     */
    public void loadUserData(User user) {
        monthlyCalendar = new MonthlyCalendarView(YearMonth.now(), calendarStage);
        monthlyCalendarView = monthlyCalendar.getView();
        loadCalendars(user);
        loadEvents(user);
    }

    /**
     * display calendar
     */
    public void displayCalendar() {
        calendarPane.getChildren().clear();
        calendarPane.getChildren().add(monthlyCalendarView);

        //calendarsVBox.getChildren()
    }

    /**
     * load calendars of a user
     * @param user
     */
    public void loadCalendars(User user) {
        calendarsVBox.getChildren().clear();

        // create a toggle group
        ToggleGroup tg = new ToggleGroup();

        for (Calendar calendar: user.getMyCalendars()) {
            HBox hBox = new HBox();
            //JFXToggleNode node = new JFXToggleNode();

            JFXButton addFavoriteButton = new JFXButton();
            addFavoriteButton.setText("★fav");
            addFavoriteButton.setStyle("-fx-font-size: 25");
            addFavoriteButton.setStyle("-fx-text-fill: #ffd400");
            addFavoriteButton.setStyle("-fx-background-color: #ffd400");


            JFXRadioButton calendarButton = new JFXRadioButton();
            calendarButton.setStyle("-fx-background-color: #ff0000");
            calendarButton.setStyle("-fx-font-size: 16px");
            calendarButton.setStyle("-fx-text-fill: #fff");
            calendarButton.setText(calendar.getTitle());
            calendarButton.setToggleGroup(tg);

            JFXButton shareButton = new JFXButton();
            addFavoriteButton.setText("🔗");
            addFavoriteButton.setStyle("-fx-text-fill: #605555");

            hBox.getChildren().addAll(shareButton, calendarButton, addFavoriteButton);
            calendarsVBox.getChildren().add(hBox);
        }

        ArrayList<AnchorPaneNode> changedDays = new ArrayList<AnchorPaneNode>();

        // add a change listener to calendar radio buttons
        tg.selectedToggleProperty().addListener(new ChangeListener<Toggle>()
        {
            public void changed(ObservableValue<? extends Toggle> ob,
                                Toggle o, Toggle n)
            {
                for (AnchorPaneNode day: changedDays) {
                    //day.getChildren().clear();
                    if (day.getChildren().size() > 1) {
                        VBox vBox;
                        vBox = (VBox) day.getChildren().get(1);
                        vBox.getChildren().clear();
                    }
                }

                RadioButton calendarRadioButton = (RadioButton)tg.getSelectedToggle();

                if (calendarRadioButton != null) {
                    String calendarName = calendarRadioButton.getText();

                    for (Calendar calendar : user.getMyCalendars()) {
                        if (calendar.getTitle().equals(calendarName)) {
                            monthlyCalendar.populateCalendar(monthlyCalendar.getCurrentYearMonth(), calendar.getEvents());
                        }
                    }
                }
            }
        });
    }

    /**
     * load events of a user
     * @param user
     */
    private void loadEvents(User user) {
        eventsVBox.getChildren().clear();
        for (Event event: user.getMyEvents()) {
            JFXButton eventButton = new JFXButton();
            eventButton.setText(event.getName());
            eventButton.setStyle("-fx-background-color: #ff0000");
            eventButton.setStyle("-fx-font-size: 16px");
            eventButton.setStyle("-fx-text-fill: #fff");
            //eventButton.onActionProperty()
            eventsVBox.getChildren().add(eventButton);
        }
    }

    //Methods

    /**
     * uploads the main screen
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
     * uploads calendars screen
     * @throws IOException
     */
    public void goMyCalendars() throws IOException{
        Stage stage;
        Parent root;

        // Get window
        stage = (Stage) myCalendarsButton.getScene().getWindow();

        // Load shared_with_me.fxml file
        URL url = new File("src/sample/views/calendars.fxml").toURI().toURL();
        FXMLLoader loader =  new FXMLLoader(url);
        root = loader.load();

        // Get SharedWithMeController
        MyCalendarsController controller = loader.getController();

        // Create new scene and show that screen
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    /**
     * uploads login screen
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
}
