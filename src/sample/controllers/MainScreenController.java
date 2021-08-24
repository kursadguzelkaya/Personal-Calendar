/**
 * Main screen controller
 * @author Kürşad Güzelkaya,
 * @version 1.0
 */
package sample.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.sun.mail.smtp.SMTPOutputStream;
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
import sample.models.Profile;
import sample.models.User;
import sample.views.AnchorPaneNode;
import sample.views.MonthlyCalendarView;
import sample.views.WeeklyCalendarView;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


public class MainScreenController {

    //Properties
    @FXML
    private JFXButton openCalendarsButton;

    @FXML
    private JFXButton openEventsButton;

    @FXML
    private JFXButton openSettingsButton;

    @FXML
    private JFXButton logoutButton;

    @FXML
    private Pane calendarPane;

    @FXML
    private JFXButton displayWeeklyButton;

    @FXML
    private JFXButton displayMonthlyButton;

    @FXML
    private VBox calendarsVBox;

    @FXML
    private VBox friendsVBox;

    @FXML
    private JFXButton eventButton; //add id to the fxml file

    private static User user;

    private Calendar selectedCalendar;

    private MonthlyCalendarView monthlyCalendar;

    private WeeklyCalendarView weeklyCalendar = new WeeklyCalendarView(YearMonth.now(), LocalDate.now());

    private VBox monthlyCalendarView;

    private VBox weeklyCalendarView = weeklyCalendar.getView();

    private Stage currentStage;

    public static User getUser() {
        return user;
    }

    public static void setUser(User theUser) {
        user = theUser;
    }

    /**
     * gets current stage from previous call
     * @param stage
     */
    public void getCurrentStage(Stage stage){
        currentStage = stage;
    }

    /**
     * loads user data
     * @param user
     */
    public void loadUserData(User user) {

        monthlyCalendar = new MonthlyCalendarView(YearMonth.now(), currentStage);
        monthlyCalendarView = monthlyCalendar.getView();
        loadCalendars(user);
        loadFriends(user);
        displayWeekly();
    }

    /**
     * loads the calendars got from user
     * @param user
     */
    public void loadCalendars(User user) {
        calendarsVBox.getChildren().clear();

        // create a toggle group
        ToggleGroup tg = new ToggleGroup();
        int count = 0;
        for (Calendar calendar: user.getMyCalendars()) {
            HBox hBox = new HBox();
            //JFXToggleNode node = new JFXToggleNode();

            //Create and style new calendar's button
            JFXRadioButton calendarButton = new JFXRadioButton();
            calendarButton.setStyle("-fx-background-color: #ff0000");
            calendarButton.setStyle("-fx-font-size: 16px");
            calendarButton.setStyle("-fx-text-fill: #fff");
            calendarButton.setText(calendar.getTitle());
            calendarButton.setToggleGroup(tg);
            if (count == 0) {
                calendarButton.setSelected(true);
                loadCalendarEventsToPane(tg, user, calendarButton);
            }
            count++;

            //Add new calendar's button to HBox
            hBox.getChildren().addAll(calendarButton);
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

                RadioButton calendarRadioButton = (RadioButton) tg.getSelectedToggle();
                loadCalendarEventsToPane(tg, user, calendarRadioButton);
                /**if (calendarRadioButton != null) {
                    String calendarName = calendarRadioButton.getText();

                    for (Calendar calendar : user.getMyCalendars()) {
                        if (calendar.getTitle().equals(calendarName)) {
                            monthlyCalendar.populateCalendar(monthlyCalendar.getCurrentYearMonth(), calendar.getEvents());
                            weeklyCalendar.populateCalendar(weeklyCalendar.getCurrentYearMonth(), weeklyCalendar.getCurrentWeekStartDate(), calendar.getEvents());
                        }
                    }
                }*/
            }
        });
    }

    /**
     * loads the selected calendar's events to calendar pane
     * @param tg
     * @param user
     * @param calendarRadioButton
     */
    private void loadCalendarEventsToPane(ToggleGroup tg, User user, RadioButton calendarRadioButton) {

        if (calendarRadioButton != null) {
            String calendarName = calendarRadioButton.getText();

            for (Calendar calendar : user.getMyCalendars()) {
                if (calendar.getTitle().equals(calendarName)) {
                    selectedCalendar = calendar;
                    monthlyCalendar.populateCalendar(monthlyCalendar.getCurrentYearMonth(), calendar.getEvents());
                    weeklyCalendar.populateCalendar(weeklyCalendar.getCurrentYearMonth(), weeklyCalendar.getCurrentWeekStartDate(), calendar.getEvents());
                }
            }
        }
    }

    /**
     * loads user's friends to friends VBox
     * @param user
     */
    private void loadFriends(User user) {
        System.out.println(user);
        friendsVBox.getChildren().clear();
        for (Profile friend: user.getFriends()) {
            JFXButton friendButton = new JFXButton();
            friendButton.setText(friend.getUsername());
            friendButton.setPrefSize(100, 31);
            friendButton.setStyle("-fx-text-fill: #fff;" + "-fx-background-color: #ff0000;");
            friendsVBox.getChildren().add(friendButton);
        }
    }

    /**
     * displays the selected calendar in monthly view
     */
    public void displayMonthly() {
        calendarPane.getChildren().clear();
        calendarPane.getChildren().add(monthlyCalendarView);
        displayMonthlyButton.setStyle("-fx-background-color: #ff0000");
        displayWeeklyButton.setStyle("-fx-background-color: #c0c0c0");
    }
    /**
     * displays the selected calendar in weekly view
     */
    public void displayWeekly() {
        calendarPane.getChildren().clear();
        calendarPane.getChildren().add(weeklyCalendarView);
        displayWeeklyButton.setStyle("-fx-background-color: #ff0000");
        displayMonthlyButton.setStyle("-fx-background-color: #c0c0c0");
    }

    /**
     * goes to calendars page
     * @throws IOException
     */
    public void openCalendarsPage() throws IOException {
        Stage stage;
        Parent root;

        // Get window
        stage = (Stage) openCalendarsButton.getScene().getWindow();

        // Load main_screen.fxml file
        URL url = new File("src/sample/views/calendars.fxml").toURI().toURL();
        FXMLLoader loader =  new FXMLLoader(url);
        root = loader.load();

        // Get mainScreenController
        MyCalendarsController controller = loader.getController();


        // Create new scene and show that screen
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        
        //sent current stage
        controller.getCurrentStage(stage);
        controller.loadUserData(user);
    }
    /**
     * goes to events page
     * @throws IOException
     */
    public void openEventsPage() throws IOException {
        Stage stage;
        Parent root;

        // Get window
        stage = (Stage) openEventsButton.getScene().getWindow();

        // Load main_screen.fxml file
        URL url = new File("src/sample/views/events.fxml").toURI().toURL();
        FXMLLoader loader =  new FXMLLoader(url);
        root = loader.load();

        // Get mainScreenController
        MyEventsController controller = loader.getController();
        controller.loadEvents(user);

        // Create new scene and show that screen
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    /**
     * goes to settings page
     * @throws IOException
     */
    public void openSettingsPage() throws IOException {
        Stage stage;
        Parent root;

        // Get window
        stage = (Stage) openSettingsButton.getScene().getWindow();

        // Load settings.fxml file
        URL url = new File("src/sample/views/settings.fxml").toURI().toURL();
        FXMLLoader loader =  new FXMLLoader(url);
        root = loader.load();

        // Get mainScreenController
        SettingsController controller = loader.getController();
        controller.loadUserData();

        // Create new scene and show that screen
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        //sent settings controller
        controller.setSettingsController(controller);
    }

    /**
     * logout and goes to login page
     * @throws IOException
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public void logout() throws IOException, ExecutionException, InterruptedException {
        Stage stage;
        Parent root;

        // Get Window
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


