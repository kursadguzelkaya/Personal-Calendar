import java.util.ArrayList;

/**
 * Personal Calendar WeeklyCalendar Class
 * 
 * @author Ege Ayan
 * @version 17.04.2021
 */
public class WeeklyCalendar extends Calendar {

    // Properties
    private ArrayList<String> days;

    // Constructors
    public WeeklyCalendar(String title) {
        super(title);
        days = new ArrayList<String>();
    }

    public WeeklyCalendar(String title, ArrayList<Event> events, ArrayList<String> comments) {
        super(title, events, comments);
        days = new ArrayList<String>();
    }

    // Methods
    /**
     * This method gives the day at index i
     * 
     * @param i is the index
     * @return is the day at the index of arraylist
     */
    public String getDay(int i) {
        return days.get(i);
    }

    /**
     * This method adds day to the arraylist
     * 
     * @param day is the day that will be added
     */
    public void addDay(String day) {
        days.add(day);
    }

    /**
     * This method removes day from the index at the arraylist
     * 
     * @param i is the index of the arraylist
     */
    public void removeDay(int i) {
        days.remove(i);
    }

    /**
     * This method displays properties of the calendar
     */
    public String toString() {
        return "" + super.toString() + days;
    }

    public void display() {
    }

}
