package sample.views;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sample.controllers.viewEventController;
import sample.models.Event;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;


public class WeeklyCalendarView {

    private ArrayList<AnchorPaneNode> allCalendarDays = new ArrayList<>(7);
    private VBox view;
    private Text calendarTitle;
    private YearMonth currentYearMonth;
    private LocalDate currentWeekStartDate;
    private int currentWeek;
    private ArrayList<Event> events;

    /**
     * Create a calendar view
     * @param yearMonth year month to create the calendar of
     */
    public WeeklyCalendarView(YearMonth yearMonth, LocalDate date) {
        currentYearMonth = yearMonth;
        currentWeekStartDate = date;
        if (currentWeekStartDate.getDayOfMonth() <= 7) {
            currentWeek = 1;
        } else if( currentWeekStartDate.getDayOfMonth() <= 14) {
            currentWeek = 2;
        } else if( currentWeekStartDate.getDayOfMonth() <= 21) {
            currentWeek = 3;
        }else if( currentWeekStartDate.getDayOfMonth() <= 28) {
            currentWeek = 4;
        }else {
            currentWeek = 5;
        }

        // Create the calendar grid pane
        GridPane calendar = new GridPane();
        calendar.setPrefSize(746, 501);
        calendar.setGridLinesVisible(true);
        // Create rows and columns with anchor panes for the calendar
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 7; j++) {
                AnchorPaneNode ap = new AnchorPaneNode();
                ap.setPrefSize(200,501);
                calendar.add(ap,j,i);
                allCalendarDays.add(ap);
            }
        }


        // Days of the week labels
        Text[] dayNames = new Text[]{ new Text("Sunday"), new Text("Monday"), new Text("Tuesday"),
                new Text("Wednesday"), new Text("Thursday"), new Text("Friday"),
                new Text("Saturday") };
        GridPane dayLabels = new GridPane();
        dayLabels.setPrefWidth(600);
        Integer col = 0;
        for (Text txt : dayNames) {
            AnchorPane ap = new AnchorPane();
            ap.setPrefSize(200, 10);
            ap.setBottomAnchor(txt, 5.0);
            ap.getChildren().add(txt);
            dayLabels.add(ap, col++, 0);
        }
        // Create calendarTitle and buttons to change current month
        calendarTitle = new Text();
        Button previousMonth = new Button("<<");
        previousMonth.setOnAction(e -> previousWeek());
        Button nextMonth = new Button(">>");
        nextMonth.setOnAction(e -> nextWeek());
        HBox titleBar = new HBox(previousMonth, calendarTitle, nextMonth);
        titleBar.setAlignment(Pos.BASELINE_CENTER);
        // Populate calendar with the appropriate day numbers
        populateCalendar(yearMonth, date,null);
        // Create the calendar view
        view = new VBox(titleBar, dayLabels, calendar);
    }

    public YearMonth getCurrentYearMonth() {
        return currentYearMonth;
    }

    public LocalDate getCurrentWeekStartDate() {
        return currentWeekStartDate;
    }

    /**
     * Set the days of the calendar to correspond to the appropriate date
     * @param yearMonth year and month of month to render
     * @param startDate
     */
    public void populateCalendar(YearMonth yearMonth, LocalDate startDate, ArrayList<Event> theEvents) {
        events = theEvents;
        // Get the date we want to start with on the calendar
        LocalDate calendarDate = startDate;
        System.out.println(calendarDate);
        // Dial back the day until it is SUNDAY (unless the month starts on a sunday)
        while (!calendarDate.getDayOfWeek().toString().equals("SUNDAY") ) {
            calendarDate = calendarDate.minusDays(1);
        }
        // Populate the calendar with day numbers
        for (AnchorPaneNode ap : allCalendarDays) {
            if (ap.getChildren().size() != 0) {
                //ap.getChildren().remove(0);
                ap.getChildren().clear();

            }
            Text txt = new Text(String.valueOf(calendarDate.getDayOfWeek().getValue()));
            ap.setDate(calendarDate);
            VBox vBox = new VBox();
            //vBox.setTopAnchor(txt, 5.0);
            //ap.setLeftAnchor(txt, 5.0);
            //ap.getChildren().add(txt);
            vBox.getChildren().add(txt);

            ap.clearEvents();
            if (events != null) {
                for (Event event : events) {
                    if (event != null) {
                        if(event.getAssignDate().equals(ap.getDate())) {
                            ap.addEvent(event);
                        }
                    }
                }
            }

            if (ap.getDate().equals(LocalDate.now())) {
                ap.setStyle("-fx-background-color: #8de3d8;" + "-fx-border-color: #000");
            } else {
                ap.setStyle("-fx-background-color: #fff;" + "-fx-border-color: #000");
            }


            for (Event event : ap.getEvents()) {
                JFXButton eventButton = new JFXButton();
                eventButton.setText(event.getName());
                int difference = ap.getDate().getDayOfMonth() - LocalDate.now().getDayOfMonth();
                if (ap.getDate().getMonthValue() - LocalDate.now().getMonthValue() < 0) {
                    eventButton.setStyle("-fx-text-fill: #fff;" + "-fx-background-color: #252424;");
                } else if (difference > 7) {
                    eventButton.setStyle("-fx-background-color: #feff01"); // yellow
                } else if (difference > 2) {
                    eventButton.setStyle("-fx-background-color: #fe8000"); // orange
                } else  if (difference > 0) {
                    eventButton.setStyle("-fx-background-color: #fe2b00"); // almost red
                } else {
                    eventButton.setStyle("-fx-text-fill: #fff;" + "-fx-background-color: #8d8d8f;");; // darkgray
                }
                //eventButton.setStyle("-fx-font-size: 16px");
                //eventButton.setStyle("-fx-text-fill: #000");
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
                vBox.getChildren().add(eventButton);
                System.out.println("Event added: " + event);
            }
            ap.setTopAnchor(vBox, 15.0);
            ap.setLeftAnchor(vBox, 5.0);
            ap.getChildren().add(vBox);
            calendarDate = calendarDate.plusDays(1);
        }
        // Change the title of the calendar
        calendarTitle.setText(yearMonth.getMonth().toString() + " Week: " + currentWeek + " " + String.valueOf(yearMonth.getYear()));
    }

    /**
     * Move the month back by one. Repopulate the calendar with the correct dates.
     */
    private void previousWeek() {

        if (currentWeek == 1) {
            currentYearMonth = currentYearMonth.minusMonths(1);
            currentWeek = 5;
        } else {
            currentWeek--;
        }
        System.out.println("Before Start date is: " + currentWeekStartDate);
        currentWeekStartDate = currentWeekStartDate.minusDays(7);
        System.out.println("Start date is: " + currentWeekStartDate);
        populateCalendar(currentYearMonth, currentWeekStartDate, events);

    }

    /**
     * Move the month forward by one. Repopulate the calendar with the correct dates.
     */
    private void nextWeek() {
        if (currentWeek == 5) {
            currentYearMonth = currentYearMonth.plusMonths(1);
            currentWeek = 1;
        } else {
            currentWeek++;
        }
        System.out.println("Before Start date is: " + currentWeekStartDate);
        currentWeekStartDate = currentWeekStartDate.plusDays(7);
        System.out.println("Start date is: " + currentWeekStartDate);
        populateCalendar(currentYearMonth, currentWeekStartDate,events);
    }

    public VBox getView() {
        return view;
    }

    public ArrayList<AnchorPaneNode> getAllCalendarDays() {
        return allCalendarDays;
    }

    public void setAllCalendarDays(ArrayList<AnchorPaneNode> allCalendarDays) {
        this.allCalendarDays = allCalendarDays;
    }

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

