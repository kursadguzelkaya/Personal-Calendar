/**
 * Calendars page controller
 * @author Kürşad Güzelkaya,
 * @version 1.0
 */
package sample.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sample.models.Calendar;
import sample.models.DataSource;
import sample.models.Event;
import sample.models.User;
import sample.views.MonthlyCalendarView;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MyCalendarsController {

    //Properties
    @FXML
    private JFXButton mainButton;

    @FXML
    private JFXButton logoutButton;

    @FXML
    private JFXButton sharedWithMe;

    @FXML
    private JFXButton newCalendarButton;

    @FXML
    private Pane calendarPane;

    @FXML
    private JFXButton calendar1;

    @FXML
    private VBox calendarsVBox;

    @FXML
    private VBox eventsVBox;

    @FXML
    private Label calendarNameLabel;

    private Stage currentStage;

    User user = MainScreenController.getUser();

    MonthlyCalendarView monthlyCalendar;

    VBox monthlyCalendarView;

    Calendar selectedCalendar;

    //methods
    ArrayList<User> users;

    /**
     * gets current stage
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
        System.out.println("Gönderdiğimiz satge: " + currentStage);
        monthlyCalendar = new MonthlyCalendarView(YearMonth.now(), currentStage);
        monthlyCalendarView = monthlyCalendar.getView();
        loadCalendars(user);
        loadEvents(user);
        monthlyCalendar.populateCalendar(monthlyCalendar.getCurrentYearMonth(), user.getMyCalendars().get(0).getEvents());
        displayCalendar();
    }

    /**
     * displays calendar
     */
    public void displayCalendar() {
        calendarPane.getChildren().clear();
        calendarPane.getChildren().add(monthlyCalendarView);

        //calendarsVBox.getChildren()
    }

    /**
     * gets all calendars from the user, creating calendar button
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

            //Create and style favorites button
            JFXButton addFavoriteButton = new JFXButton();
            addFavoriteButton.setText("★");
            addFavoriteButton.setStyle("-fx-font-size: 25");
            if (user.getFavoCalendars().contains(calendar)) {
                addFavoriteButton.setStyle("-fx-text-fill: #ffd400");
            } else {
                addFavoriteButton.setStyle("-fx-text-fill: #605555");
            }
            addFavoriteButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    // add favorite if not
                    // remove favorite if favorite
                    if (user.getFavoCalendars().contains(calendar)) {
                        addFavoriteButton.setStyle("-fx-text-fill: #605555");
                        user.removeFavCal(calendar);
                        DataSource.getInstance().deleteFavCalendar(calendar.getId(), user.getMyProfile().getId());
                    } else {
                        addFavoriteButton.setStyle("-fx-text-fill: #ffd400");
                        user.addFavCal(calendar);
                        DataSource.getInstance().addFavCalendarToDataBase(calendar.getId(), user.getMyProfile().getId());
                    }
                }
            });

            //addFavoriteButton.setStyle("-fx-background-color: #ffd400");

            //Create and style new created calendar's buttons
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

            JFXButton infoButton = new JFXButton();
            infoButton.setText("ℹ");
            infoButton.setStyle("-fx-text-fill: #605555");
            infoButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    try {
                        openEditCalendarPopUp(calendar);
                        System.out.println(user);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            //Add buttons to children of HBox
            hBox.getChildren().addAll(addFavoriteButton, calendarButton, infoButton);
            hBox.setSpacing(5);
            calendarsVBox.getChildren().add(hBox);
        }

        // add a change listener to calendar radio buttons
        tg.selectedToggleProperty().addListener(new ChangeListener<Toggle>()
        {
            public void changed(ObservableValue<? extends Toggle> ob,
                                Toggle o, Toggle n)
            {

                RadioButton calendarRadioButton = (RadioButton)tg.getSelectedToggle();

                loadCalendarEventsToPane(tg, user,calendarRadioButton);
            }
        });
    }

    /**
     * loads calendar events to pane
     * @param tg
     * @param user
     * @param calendarRadioButton
     */
    private void loadCalendarEventsToPane(ToggleGroup tg, User user, RadioButton calendarRadioButton) {

        if (calendarRadioButton != null) {
            String calendarName = calendarRadioButton.getText();
            calendarNameLabel.setText(calendarName);

            for (Calendar calendar : user.getMyCalendars()) {
                if (calendar.getTitle().equals(calendarName)) {
                    selectedCalendar = calendar;
                    monthlyCalendar.populateCalendar(monthlyCalendar.getCurrentYearMonth(), calendar.getEvents());
                }
            }
        }
    }

    /**
     * load events of a user
     * @param user
     */
    void loadEvents(User user) {
        eventsVBox.getChildren().clear();
        for (Event event: user.getMyEvents()) {
            JFXButton eventButton = new JFXButton();
            eventButton.setText(event.getName());
            eventButton.setStyle("-fx-background-color: #ff0000");
            eventButton.setStyle("-fx-font-size: 16px");
            eventButton.setStyle("-fx-text-fill: #fff");
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
            eventsVBox.getChildren().add(eventButton);
        }
    }

    //add event buttons --> action="#viewEventDetalis"

    /**
     * displays event details 
     * @param event
     * @throws IOException
     */
    public void viewEventDetails(Event event) throws IOException {
        Stage stage;
        Parent root;
        Stage currentStage;

        //Get window
        currentStage = (Stage) eventsVBox.getScene().getWindow();


        //create new popupStage
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
        controller.getCurrentStage(currentStage);
    }

    /**
     * opens new event pop-up
     * @throws IOException
     */
    public void openNewEventPopup() throws IOException {
        Stage stage;
        Parent root;
        Stage calendarStage;

        //get calendar stage
        calendarStage= (Stage) newCalendarButton.getScene().getWindow();

        //Get window
        stage = new Stage();

        //load view_event_details.fxml
        URL url = new File("src/sample/views/edit_event.fxml").toURI().toURL();
        FXMLLoader loader =  new FXMLLoader(url);
        root = loader.load();

        //Get view event controller
        EditEventController controller = loader.getController();
        controller.getData(null, selectedCalendar);

        // Create new scene and show that screen
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        //sent current stage to the controller
        controller.setStage(calendarStage);
        //sent pop up stage to the controller
        controller.setPopupStage(stage);
    }

    /**
     * Creates new calendar
     */
    public void createNewCalendar() {
        int calendarNumber = user.getMyCalendars().size();
        String calendarName = "Default" + calendarNumber + 1;
        Calendar newCalendar = new Calendar(calendarName, 15);
        //post calendar to DB
        DataSource.getInstance().postCalendar(newCalendar.getTitle());

        for (Calendar calendar: DataSource.getInstance().getAllCalendars(DataSource.getInstance().getAllEvents())) {
            if (calendarName.equals(calendar.getTitle())){
                DataSource.getInstance().addMyCalendarToDataBase(calendar.getId(), user.getMyProfile().getId());
                user.addMyCal(calendar);
            }
        }

        loadCalendars(user);
    }

    /**
     * opens edit calendar pop-up
     * @param cal
     * @throws IOException
     */
    public void openEditCalendarPopUp(Calendar cal) throws IOException {
        Stage stage;
        Parent root;
        Stage calendarStage;

        calendarStage= (Stage) newCalendarButton.getScene().getWindow();

        //Get window
        stage = new Stage();

        //load view_event_details.fxml
        URL url = new File("src/sample/views/edit_calendar.fxml").toURI().toURL();
        FXMLLoader loader =  new FXMLLoader(url);
        root = loader.load();

        //Get view event controller
        EditCalendarController controller = loader.getController();
        controller.loadCalendar(cal);

        //sent stages
        controller.setStage(calendarStage);
        controller.setPopUpStage(stage);

        // Create new scene and show that screen
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * go to shared with me page
     * @throws IOException
     */
    public void goSharedWithMe() throws IOException {
        Stage stage;
        Parent root;

        // Get window
        stage = (Stage) sharedWithMe.getScene().getWindow();

        // Load shared_with_me.fxml file
        URL url = new File("src/sample/views/shared_with_me.fxml").toURI().toURL();
        FXMLLoader loader =  new FXMLLoader(url);
        root = loader.load();

        // Get SharedWithMeController
        SharedWithMeController controller = loader.getController();

        // Create new scene and show that screen
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        //sent current calendar stage to the controller
        controller.setCalendarStage(stage);
        controller.loadUserData(user);
        controller.displayCalendar();
    }

    /**
     * go to main screen
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

        //sent current stage
        controller.getCurrentStage(stage);
    }

    /**
     * logout and go to the login screen
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
     * open share calendar pop-up
     * @param calendar
     * @throws IOException
     */
    public void shareCalendarPopUp(Calendar calendar) throws IOException{
        Stage popUpStage;
        Parent root;

        //Get window
        popUpStage = new Stage();

        //load share_calender_popup
        URL url = new File("src/sample/views/share_calendar_popup.fxml").toURI().toURL();
        FXMLLoader loader =  new FXMLLoader(url);
        root = loader.load();

        // Get WelcomeController
        ShareCalendarController controller = loader.getController();
        controller.getCalendar(calendar);

        // Create new scene and show that screen
        Scene scene = new Scene(root);
        popUpStage.setScene(scene);
        popUpStage.show();
    }
}


