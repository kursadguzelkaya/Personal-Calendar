import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

/**
 * This class represents the event in the calendar
 * 
 * @author Mustafa Kaan Koç
 * @version 17.02.2021
 */
public class Event {

    // properties
    private String name;
    private String type;
    private LocalDate dueDate;
    private ArrayList<LocalDate> assignDate;

    // constructors
    public Event() {
    }

    public Event(String name, String type, LocalDate dueDate) {
        this.name = name;
        this.type = type;
        this.dueDate = dueDate;
        assignDate = new ArrayList<LocalDate>();
    }

    public Event(String name, String type, LocalDate dueDate, ArrayList<LocalDate> assignDate) {
        this.name = name;
        this.type = type;
        this.dueDate = dueDate;
        this.assignDate = assignDate;
    }

    /**
     * This method returns the name
     * 
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * This method returns the type
     * 
     * @return type
     */
    public String getType() {
        return type;
    }

    /**
     * This method returns the due date
     * 
     * @return dueDate
     */
    public LocalDate getDueDate() {
        return dueDate;
    }

    /**
     * This method sets the the name to given name
     * 
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * This method sets the type to given type
     * 
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * This method sets the dueDate to given dueDate
     * 
     * @param duedate
     */
    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    @Override
    public String toString() {
        String result;
        result = "Event: " + name + ", type: " + type + ", duedate: " + dueDate + "\n";
        for (int i = 0; i < assignDate.size(); i++) {
            result += assignDate.get(i) + "\n";
        }
        return result;
    }

}