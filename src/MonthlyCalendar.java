
/**
 * @author Hazal Buluş
 * @version 1.0
 */
import java.util.ArrayList;

public class MonthlyCalendar extends Calendar {
    // Properties
    private ArrayList<String> days;
    String month;

    // Constructor
    public MonthlyCalendar(String title, String month) {
        super(title);
        this.month = month;
        days = new ArrayList<>();
    }

    public MonthlyCalendar(String title, ArrayList<Event> events, ArrayList<String> comments, String month) {
        super(title, events, comments);
        days = new ArrayList<>();
        this.month = month;
    }

    /**
     * gets day from arraylist
     * 
     * @param int n
     * @return day
     */
    public String getDay(int n) {
        return days.get(n);
    }

    /**
     * adds day to arraylist
     * 
     * @param String n
     */
    public void addDay(String n) {
        days.add(n);
    }

    /**
     * adds day to arraylist
     * 
     * @param String n
     */
    public void removeDay(String n) {
        for (int i = 0; i < days.size(); i++) {
            if (days.get(i).equals(n)) {
                days.remove(i);
            }
        }
    }

    @Override
    public void display() {
    }

    @Override
    public String toString() {
        String result = super.toString();
        result += "\nDays: ";
        for (int i = 0; i < days.size(); i++) {
            result += days.get(i) + "\n";
        }
        return result;
    }

}
