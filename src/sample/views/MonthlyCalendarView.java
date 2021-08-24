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


public class MonthlyCalendarView {

    private ArrayList<AnchorPaneNode> allCalendarDays = new ArrayList<>(35);
    private VBox view;
    private Text calendarTitle;
    private YearMonth currentYearMonth;
    private ArrayList<Event> events;
    private Stage currentStage;

    /**
     * Create a calendar view
     * @param yearMonth year month to create the calendar of
     */
    public MonthlyCalendarView(YearMonth yearMonth, Stage stage) {
        System.out.println("Aldığımız stage: " + stage);
        System.out.println("Stage1: " + currentStage);

        currentYearMonth = yearMonth;
        currentStage = stage;
        // Create the calendar grid pane
        GridPane calendar = new GridPane();
        calendar.setPrefSize(744, 454);
        calendar.setGridLinesVisible(true);
        // Create rows and columns with anchor panes for the calendar
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 7; j++) {
                AnchorPaneNode ap = new AnchorPaneNode();
                ap.setPrefSize(200,200);
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
        previousMonth.setOnAction(e -> previousMonth());
        Button nextMonth = new Button(">>");
        nextMonth.setOnAction(e -> nextMonth());
        HBox titleBar = new HBox(previousMonth, calendarTitle, nextMonth);
        titleBar.setAlignment(Pos.BASELINE_CENTER);
        // Populate calendar with the appropriate day numbers
        populateCalendar(yearMonth, null);
        // Create the calendar view
        view = new VBox(titleBar, dayLabels, calendar);
        System.out.println("Stage2: " + currentStage);
    }

    public YearMonth getCurrentYearMonth() {
        return currentYearMonth;
    }

    /**
     * Set the days of the calendar to correspond to the appropriate date
     * @param yearMonth year and month of month to render
     */
    public void populateCalendar(YearMonth yearMonth, ArrayList<Event> theEvents) {
        events = theEvents;
        // Get the date we want to start with on the calendar
        LocalDate calendarDate = LocalDate.of(yearMonth.getYear(), yearMonth.getMonthValue(), 1);
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
            Text txt = new Text(String.valueOf(calendarDate.getDayOfMonth()));
            ap.setDate(calendarDate);
            VBox vBox = new VBox();
            //ap.setTopAnchor(txt, 5.0);
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
                } else if (ap.getDate().getMonthValue() - LocalDate.now().getMonthValue() > 0) {
                    eventButton.setStyle("-fx-text-fill: #fff;" + "-fx-background-color: #feff01;");
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
            ap.getChildren().add(vBox);
            calendarDate = calendarDate.plusDays(1);
        }
        // Change the title of the calendar
        calendarTitle.setText(yearMonth.getMonth().toString() + " " + String.valueOf(yearMonth.getYear()));
    }

    /**
     * Move the month back by one. Repopulate the calendar with the correct dates.
     */
    private void previousMonth() {
        currentYearMonth = currentYearMonth.minusMonths(1);
        populateCalendar(currentYearMonth, events);

    }

    /**
     * Move the month forward by one. Repopulate the calendar with the correct dates.
     */
    private void nextMonth() {
        currentYearMonth = currentYearMonth.plusMonths(1);
        populateCalendar(currentYearMonth, events);
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
        if (currentStage != null) {
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
            System.out.println("Stage monthly viewww: " + currentStage);
            controller.getCurrentStage(currentStage);
        } else  {
            //load view_event_details.fxml
            URL url = new File("src/sample/views/view_event_details_no_delete.fxml").toURI().toURL();
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
            System.out.println("Stage monthly viewww: " + currentStage);
            controller.getCurrentStage(currentStage);
        }




    }
}
