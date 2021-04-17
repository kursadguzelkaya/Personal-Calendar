import java.util.ArrayList;

/**
 * @author Mustafa Kaan Koç
 * @version 17.04.2021
 */
public class DailyCalendar extends Calendar {
    // properties
    private String today;

    // constructors

    public DailyCalendar(String title, String today) {
        super(title);
        this.today = today;
    }

    public DailyCalendar(String title, ArrayList<Event> events, ArrayList<String> comments, String today) {
        super(title, events, comments);
        this.today = today;
    }

    /**
     * this method returns today
     * 
     * @return today
     */
    public String getToday() {
        return today;
    }

    /**
     * this method sets the today to given today
     * 
     * @param today
     */
    public void setToday(String today) {
        this.today = today;
    }

    public void display() {

    }

}
